package com.imadethatcow.hipchat

import Common._
import com.imadethatcow.hipchat.enums.AuthGrantType
import dispatch._, Defaults._
import org.slf4j.LoggerFactory
import scala.util.{Failure, Success, Try}
import com.imadethatcow.hipchat.caseclass._
import scala.util.Failure
import scala.Some
import scala.util.Success
import AuthGrantType.AuthGrantType

// TODO: this isn't complete, shouldn't be used
private class Auth(private[this] val apiToken: String) {
  val log = LoggerFactory.getLogger(getClass)
  def call(grantType: AuthGrantType,
           username: Option[String] = None,
           code: Option[String] = None,
           redirectUrl: Option[String] = None,
           scope: Option[String] = None,
           password: Option[String] = None,
           refreshToken: Option[String] = None): Option[AuthResponse] = {
    //val body = mapper.writeValueAsString(authRequest)
    var req = addToken(Auth.urlPost, apiToken)
      .addQueryParameter("grant_type", grantType.toString)
      //.setBody(body)
      //.setHeader("Content-Type", "application/json")
      //.setHeader("Content-Type", "x-www-form-urlencoded")

    for (u <- username) req = req.addQueryParameter("username", u)
    for (c <- code) req = req.addQueryParameter("code", c)
    for (ru <- redirectUrl) req = req.addQueryParameter("redirect_url", ru)
    for (s <- scope) req = req.addQueryParameter("scope", s)
    for (p <- password) req = req.addQueryParameter("password", p)
    for (rt <- refreshToken) req = req.addQueryParameter("refresh_token", rt)
    println(req.toRequest.getUrl)

    val jsonOpt = resolveRequest(req)
    jsonOpt match {
      case Some(json) =>
        val authResponse = Try[AuthResponse](mapper.readValue(json, classOf[AuthResponse]))
        authResponse match {
          case Success(v) =>
            Some(v)
          case Failure(e) =>
            log.error("Failed to parse JSON response", e)
            None
        }
      case None =>
        None
    }
  }
}

private object Auth {
  val log = LoggerFactory.getLogger(getClass)
  val urlBase = apiUrlSecure / "oauth" / "token"
  val urlPost = urlBase.POST
}





































