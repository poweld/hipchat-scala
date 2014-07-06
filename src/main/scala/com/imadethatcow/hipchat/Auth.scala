package com.imadethatcow.hipchat

import com.imadethatcow.hipchat.common.Common._
import com.imadethatcow.hipchat.common.Logging
import org.slf4j.LoggerFactory

import scala.util.Try
import com.imadethatcow.hipchat.common.caseclass.AuthResponse
import scala.util.Success
import scala.util.Failure
import scala.Some
import com.imadethatcow.hipchat.common.enums.Scope.Scope
import com.imadethatcow.hipchat.common.enums.AuthGrantType

class Auth(private[this] val apiToken: String) extends Logging {

  private def createReqWithHeaderAndParams(urlEncodedVals: Seq[(String,String)]) = {
    val reqWithHeader = addToken(Auth.urlPost, apiToken)
      .setHeader("Content-Type", "application/x-www-form-urlencoded")

    addFormUrlEncodedVals(reqWithHeader, urlEncodedVals)
  }

  // TODO: this is good, should find a way to make a generic for use throughout the code if possible
  private def mapJsonStringToResponse(jsonOpt: Option[String]) = {
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

  def genPersonalToken() = {
    val urlEncodedVals = Seq(("grant_type", AuthGrantType.personal.toString))

    val req = createReqWithHeaderAndParams(urlEncodedVals)

    val jsonOpt = resolveRequest(req)

    mapJsonStringToResponse(jsonOpt)
  }

  def genPasswordToken(username: String,
                       password: String,
                       scopes: Seq[Scope]) = {
    val urlEncodedVals = Seq("grant_type" -> AuthGrantType.password.toString,
      "username" -> username, "password" -> password, "scope" -> scopes.mkString(" "))

    val req = createReqWithHeaderAndParams(urlEncodedVals)

    val jsonOpt = resolveRequest(req)

    mapJsonStringToResponse(jsonOpt)
  }
}

private object Auth {
  val urlBase = apiUrl / "oauth" / "token"
  val urlPost = urlBase.POST
}





































