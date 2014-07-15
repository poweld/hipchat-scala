package com.imadethatcow.hipchat.users

import com.imadethatcow.hipchat.common.{Logging, Common}
import Common._
import com.imadethatcow.hipchat.common.caseclass.PrivateMessage
import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global

class PrivateMessenger(private[this] val apiToken: String) extends Logging {
  def sendMessage(idOrEmail: String, message: String): Future[Boolean] = {
    val req = addToken(PrivateMessenger.url(idOrEmail), apiToken)
      .setBody(mapper.writeValueAsString(PrivateMessage(message)))
      .setHeader("Content-Type", "application/json")

    resolveRequest(req, 204) map { _ => true} recover { case _: Exception => false}
  }
}

object PrivateMessenger {
  def url(idOrEmail: String) = (apiUrl / "user" / idOrEmail / "message").POST
}


