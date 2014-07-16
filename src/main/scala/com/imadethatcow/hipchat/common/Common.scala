package com.imadethatcow.hipchat.common

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala._
import com.typesafe.config.ConfigFactory
import dispatch._
import org.slf4j.LoggerFactory
import scala.concurrent.ExecutionContext
import scala.reflect.ClassTag
import scala.util.{Success, Failure, Try}
import com.ning.http.client.Response


object Common extends Logging with Config {
  val secureTry = Try(config.getBoolean("com.imadethatcow.hipchat.secure"))
  val secure = secureTry match {
    case Failure(e) => true
    case Success(v) => v
  }

  // Mapper will ignore pairs with null/None values
  val mapper = new ObjectMapper().setSerializationInclusion(Include.NON_NULL).registerModule(DefaultScalaModule)
  val writeMapper = new ObjectMapper().registerModule(DefaultScalaModule)
  val http = Http.configure(_ setFollowRedirects true)
  val apiRoot = "http://api.hipchat.com"
  val apiRootSecure = "https://api.hipchat.com"
  val version = "v2"
  val apiUrl = if (secure) url(apiRootSecure) / version else url(apiRoot) / version
  val defaultResponseCode: Int = 200

  def addFormUrlEncodedVals(req: Req, vals: Seq[(String,String)]): Req =
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

  def resolveAndDeserialize[T](req: Req, expectedResponseCode: Int = defaultResponseCode)(implicit executor: ExecutionContext, classTag: ClassTag[T]): Future[T] = {
    resolveRequest(req, expectedResponseCode) map {
      response =>
        val tClass = implicitly[ClassTag[T]].runtimeClass
        val mappedObject = mapper.readValue(response.getResponseBody, tClass)
        mappedObject.asInstanceOf[T]
    }
  }
}

trait Logging {
  val log = LoggerFactory.getLogger(getClass)
}

trait Config {
  val config = ConfigFactory.load
}
