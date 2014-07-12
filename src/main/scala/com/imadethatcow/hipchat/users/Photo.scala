package com.imadethatcow.hipchat.users

import com.imadethatcow.hipchat.common.Common
import Common._

class Photo(private[this] val apiToken: String) {
  def update(idOrEmail: String, encodedPhoto: String) = {
    val req = addToken(Photo.urlPut(idOrEmail), apiToken)
      .setBody(encodedPhoto)
      .setHeader("Content-Type", "application/json")

    resolveRequest(req, 204) match {
      case Some(r) => true
      case None => false
    }
  }
}

object Photo {
  private def url(idOrEmail: String) = apiUrl / "user" / idOrEmail / "photo"

  def urlPut(idOrEmail: String) = url(idOrEmail).PUT
  def urlDelete(idOrEmail: String) = url(idOrEmail).DELETE
}

/*
class PrivateMessenger(private[this] val apiToken: String) extends Logging {
  def sendMessage(idOrEmail: String, message: String): Boolean = {
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
  def url(idOrEmail: String) = (apiUrl / "user" / idOrEmail / "message").POST
}
*/
