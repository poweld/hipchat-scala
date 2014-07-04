package com.imadethatcow.hipchat.common

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala._
import dispatch.Defaults._
import dispatch._
import org.slf4j.LoggerFactory
import annotation.tailrec

object Common {
  val log = LoggerFactory.getLogger(getClass)

  // Mapper will ignore pairs with null/None values
  val mapper = new ObjectMapper().setSerializationInclusion(Include.NON_NULL)
  mapper.registerModule(DefaultScalaModule)
  val http = Http.configure(_ setFollowRedirects true)
  val apiRootSecure = "https://api.hipchat.com"
  val version = "v2"
  val apiUrlSecure = url(apiRootSecure) / version
  def addFormUrlEncodedVals(req: Req, vals: (String,String)*): Req = {
    @tailrec def loop(req: Req, vals: Seq[(String, String)]): Req = vals match {
      case Nil => req
      case head :: tail =>
        val (name, value) = head
        loop(req.addParameter(name, value), tail)
    }
    loop(req, vals)
  }
  def addToken(req: Req, token: String): Req = req.addQueryParameter("auth_token", token.toString)
  def resolveRequest(req: Req, expectedResponseCode: Int = 200): Option[String] = {
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
}
