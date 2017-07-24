package com.imadethatcow.hipchat

import com.imadethatcow.hipchat.common.Common._
import com.imadethatcow.hipchat.common.Logging

import com.imadethatcow.hipchat.common.caseclass.{GetSessionResponse, AuthResponse}
import com.imadethatcow.hipchat.common.enums.Scope.Scope
import com.imadethatcow.hipchat.common.enums.AuthGrantType
import scala.concurrent.{ExecutionContext, Future}

class Auth(private[this] val apiToken: String, private[this] val baseUrlOpt: Option[String] = None)(implicit executor: ExecutionContext) extends Logging {

  private val urlBase = reqFromBaseUrl(baseUrlOpt) / "oauth" / "token"
  private def urlGet(token: String) = (urlBase / token).GET
  private def urlDelete(token: String) = (urlBase / token).DELETE
  private val urlPost = urlBase.POST

  private def createReqWithHeaderAndParams(urlEncodedVals: Seq[(String, String)]) = {
    val reqWithHeader = addToken(urlPost, apiToken)
      .setHeader("Content-Type", "application/x-www-form-urlencoded")

    addFormUrlEncodedVals(reqWithHeader, urlEncodedVals)
  }

  def genPersonalToken(): Future[AuthResponse] = {
    val urlEncodedVals = Seq(("grant_type", AuthGrantType.personal.toString))

    val req = createReqWithHeaderAndParams(urlEncodedVals)
    resolveAndDeserialize[AuthResponse](req)
  }

  def genPasswordToken(
    username: String,
    password: String,
    scopes:   Seq[Scope]
  ): Future[AuthResponse] = {
    val urlEncodedVals = Seq(
      "grant_type" -> AuthGrantType.password.toString,
      "username" -> username, "password" -> password, "scope" -> scopes.mkString(" ")
    )

    val req = createReqWithHeaderAndParams(urlEncodedVals)
    resolveAndDeserialize[AuthResponse](req)
  }

  def getSession(token: String): Future[GetSessionResponse] = {
    val req = addToken(urlGet(token), apiToken)
      .setHeader("Content-Type", "application/x-www-form-urlencoded")
      .addParameter("session-id", token)

    resolveAndDeserialize[GetSessionResponse](req)
  }

  def deleteSession(token: String): Future[Boolean] = {
    val req = addToken(urlDelete(token), apiToken)
      .setHeader("Content-Type", "application/x-www-form-urlencoded")
      .addParameter("session-id", token)

    resolveBoolRequest(req, 204)
  }
}