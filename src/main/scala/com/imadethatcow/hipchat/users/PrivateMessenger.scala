package com.imadethatcow.hipchat.users

import com.imadethatcow.hipchat.common.Common
import Common._
import com.imadethatcow.hipchat.common.caseclass.PrivateMessage
import org.slf4j.LoggerFactory

class PrivateMessenger(private[this] val apiToken: String) {
  val log = LoggerFactory.getLogger(getClass)
  def call(idOrEmail: String, message: String): Boolean = {
    val req = addToken(PrivateMessenger.url(idOrEmail), apiToken)
      .setBody(mapper.writeValueAsString(PrivateMessage(message)))
      .setHeader("Content-Type", "application/json")

    resolveRequest(req, 204) match {
      case Some(r) => true
      case None => false
    }
  }
}

object PrivateMessenger {
  def url(idOrEmail: String) = (apiUrlSecure / "user" / idOrEmail / "message").POST
}


