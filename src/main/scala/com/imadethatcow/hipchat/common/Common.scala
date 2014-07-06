package com.imadethatcow.hipchat.common

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala._
import com.typesafe.config.ConfigFactory
import dispatch.Defaults._
import dispatch._
import org.slf4j.LoggerFactory
import scala.reflect.ClassTag
import scala.util.{Success, Failure, Try}

object Common extends Logging with Config {
  val secureTry = Try(config.getBoolean("com.imadethatcow.hipchat.secure"))
  val secure = secureTry match {
    case Failure(e) => true
    case Success(v) => v
  }

  // Mapper will ignore pairs with null/None values
  val mapper = new ObjectMapper().setSerializationInclusion(Include.NON_NULL)
  mapper.registerModule(DefaultScalaModule)
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
  def resolveRequest(req: Req, expectedResponseCode: Int = defaultResponseCode): Option[String] = {
    val response = http(req).option.apply()
    response match {
      case Some(r) =>
        if (r.getStatusCode == expectedResponseCode)
          Some(r.getResponseBody)
        else {
          throw new Exception(s"Received unexpected status code: ${r.getStatusCode}, message: ${r.getResponseBody}")
        }
      case None =>
        log.error(s"No response for request: $req")
        None
    }
  }
  def resolveAndDeserialize[T : ClassTag](req: Req, expectedResponseCode: Int = defaultResponseCode): Option[T] = {
    val jsonOpt = resolveRequest(req, expectedResponseCode)
    jsonOpt match {
      case Some(json) =>
        val tClass = implicitly[ClassTag[T]].runtimeClass
        val emoticonsResponse = Try(mapper.readValue(json, tClass))
        emoticonsResponse match {
          case Success(v) =>
            Some(v.asInstanceOf[T])
          case Failure(e) =>
            log.error("Failed to parse JSON response", e)
            None
        }
      case None => None
    }
  }
}

trait Logging {
  val log = LoggerFactory.getLogger(getClass)
}

trait Config {
  val config = ConfigFactory.load
}
