package com.imadethatcow.hipchat.common

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala._
import com.typesafe.config.ConfigFactory
import dispatch._
import org.asynchttpclient.Response
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.ExecutionContext
import scala.reflect.ClassTag

object Common extends Logging with Config {
  // Mapper will ignore pairs with null/None values
  val readMapper: ObjectMapper = new ObjectMapper()
    .setSerializationInclusion(Include.NON_NULL)
    .registerModule(DefaultScalaModule)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  val writeMapper: ObjectMapper = new ObjectMapper().registerModule(DefaultScalaModule)
  val http: Http = Http.withConfiguration(_.setFollowRedirect(true))
  val apiUrl: Req = url(config.getString("api-url"))
  val defaultResponseCode: Int = 200

  sys.ShutdownHookThread {
    http.shutdown()
    log.info("HTTP client shutdown")
  }

  def addFormUrlEncodedVals(req: Req, vals: Seq[(String, String)]): Req =
    vals.foldLeft(req) {
      case (request, (name, value)) => request.addParameter(name, value)
    }

  def addToken(req: Req, token: String): Req = req.addQueryParameter("auth_token", token.toString)

  def resolveRequest(req: Req, expectedResponseCode: Int = defaultResponseCode)(implicit executor: ExecutionContext): Future[Response] = {
    http(req) map {
      response =>
        if (response.getStatusCode != expectedResponseCode)
          throw new Exception(s"Received unexpected status code: ${response.getStatusCode}, message: ${response.getResponseBody}")
        else
          response
    }
  }

  def reqFromBaseUrl(baseUrlOpt: Option[String]): Req = {
    baseUrlOpt.map(dispatch.url).getOrElse(apiUrl)
  }

  @inline def resolveBoolRequest(req: Req, expectedResponseCode: Int = defaultResponseCode)(implicit executor: ExecutionContext): Future[Boolean] =
    resolveRequest(req, expectedResponseCode) map { _ => true } recover { case _: Exception => false }

  def resolveAndDeserialize[T](req: Req, expectedResponseCode: Int = defaultResponseCode)(implicit executor: ExecutionContext, classTag: ClassTag[T]): Future[T] = {
    resolveRequest(req, expectedResponseCode) map {
      response =>
        val tClass = implicitly[ClassTag[T]].runtimeClass
        val mappedObject = readMapper.readValue(response.getResponseBody, tClass)
        mappedObject.asInstanceOf[T]
    }
  }
}

trait Logging {
  val log: Logger = LoggerFactory.getLogger(getClass)
}

trait Config {
  private val _configKey = "com.imadethatcow.hipchat"
  val config: com.typesafe.config.Config = ConfigFactory.load.getConfig(_configKey)
}

trait HipchatValueObject extends Serializable
