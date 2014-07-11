package com.imadethatcow.hipchat

import com.imadethatcow.hipchat.common.Common._
import com.imadethatcow.hipchat.common.Logging

import com.imadethatcow.hipchat.common.caseclass.{GetSessionResponse, AuthResponse}
import com.imadethatcow.hipchat.common.enums.Scope.Scope
import com.imadethatcow.hipchat.common.enums.AuthGrantType

class Auth(private[this] val apiToken: String) extends Logging {

  private def createReqWithHeaderAndParams(urlEncodedVals: Seq[(String,String)]) = {
    val reqWithHeader = addToken(Auth.urlPost, apiToken)
      .setHeader("Content-Type", "application/x-www-form-urlencoded")

    addFormUrlEncodedVals(reqWithHeader, urlEncodedVals)
  }

  def genPersonalToken() = {
    val urlEncodedVals = Seq(("grant_type", AuthGrantType.personal.toString))

    val req = createReqWithHeaderAndParams(urlEncodedVals)
    resolveAndDeserialize[AuthResponse](req)
  }

  def genPasswordToken(username: String,
                       password: String,
                       scopes: Seq[Scope]) = {
    val urlEncodedVals = Seq("grant_type" -> AuthGrantType.password.toString,
      "username" -> username, "password" -> password, "scope" -> scopes.mkString(" "))

    val req = createReqWithHeaderAndParams(urlEncodedVals)
    resolveAndDeserialize[AuthResponse](req)
  }

  def getSession(token: String) = {
    val req = addToken(Auth.urlGet(token), apiToken)
      .setHeader("Content-Type", "application/x-www-form-urlencoded")
      .addParameter("session-id", token)

    resolveAndDeserialize[GetSessionResponse](req)
  }
}

private object Auth {
  private val urlBase = apiUrl / "oauth" / "token"
  def urlGet(token: String) = {
    (urlBase / token).GET
  }
  val urlPost = urlBase.POST
}





































