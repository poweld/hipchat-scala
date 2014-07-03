package com.imadethatcow.hipchat

import com.imadethatcow.hipchat.common.Common._
import org.slf4j.LoggerFactory

import scala.util.{Failure, Success, Try}
import com.imadethatcow.hipchat.common.caseclass.{AuthRequest, AuthResponse}
import scala.util.Success
import scala.util.Failure
import scala.Some
import com.imadethatcow.hipchat.common.enums.AuthGrantType
import com.imadethatcow.hipchat.common.enums.Scopes._

class Auth(private[this] val apiToken: String) {

  val log = LoggerFactory.getLogger(getClass)
  def generatePersonalToken() = {

    val req = addToken(Auth.urlPost, apiToken)
      .setHeader("Content-Type", "application/x-www-form-urlencoded")

    val urlEncodedVals = Seq(("grant_type", AuthGrantType.personal.toString))

    val reqWithBody = addFormUrlEncodedVals(req, urlEncodedVals:_*)

    val jsonOpt = resolveRequest(reqWithBody)

    jsonOpt match {
      case Some(json) =>
        val authResponse = Try(mapper.readValue(json, classOf[AuthResponse]))
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





































