package com.imadethatcow.hipchat.users

import com.imadethatcow.hipchat.common.{Logging, Common}
import Common._
import com.imadethatcow.hipchat.common.caseclass.PrivateMessage
import scala.concurrent.{ExecutionContext, Future}

class PrivateMessenger(private[this] val apiToken: String, private[this] val baseUrlOpt: Option[String] = None)(implicit executor: ExecutionContext) extends Logging {

  private def url(idOrEmail: String) = (reqFromBaseUrl(baseUrlOpt) / "user" / idOrEmail / "message").POST

  def sendMessage(idOrEmail: String, message: String): Future[Boolean] = {
    val req = addToken(url(idOrEmail), apiToken)
      .setBody(readMapper.writeValueAsString(PrivateMessage(message)))
      .setHeader("Content-Type", "application/json")

    resolveBoolRequest(req, 204)
  }
}