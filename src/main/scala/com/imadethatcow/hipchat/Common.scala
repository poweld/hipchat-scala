package com.imadethatcow.hipchat

import dispatch._, Defaults._
import com.fasterxml.jackson.module.scala._
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.annotation.JsonInclude.Include
import org.slf4j.LoggerFactory

object Common {
  val log = LoggerFactory.getLogger(getClass)

  // Mapper will ignore pairs with null/None values
  val mapper = new ObjectMapper().setSerializationInclusion(Include.NON_NULL)
  mapper.registerModule(DefaultScalaModule)
  val http = Http.configure(_ setFollowRedirects true)
  val apiRoot = "http://api.hipchat.com"
  val apiRootSecure = "https://api.hipchat.com"
  val version = "v2"
  val apiUrl = url(apiRoot) / version
  val apiUrlSecure = url(apiRootSecure) / version
  def addToken(req: Req, token: String) = req.addQueryParameter("auth_token", token.toString)
  def resolveRequest(req: Req, expectedResponseCode: Int = 200) = {
    //http(req OK as.String).option.apply()
    val response = http(req).option.apply()
    response match {
      case Some(r) =>
        if (r.getStatusCode == expectedResponseCode)
          Some(r.getResponseBody)
        else {
          throw new Exception(s"Received unexpected status code: ${r.getStatusCode}, message: ${r.getResponseBody}")
          //log.warn(s"Received unexpected status code: ${r.getStatusCode}")
          //None
        }
      case None =>
        log.error(s"No response for request: $req")
        None
    }
  }
}

private[hipchat] case class MaxResults(_maxResults: Long) {
  val value = _maxResults.max(100).min(0)
  implicit def maxResults2Long(m: MaxResults) = m.value
  implicit def long2MaxResults(l: Long) = MaxResults(l)
}