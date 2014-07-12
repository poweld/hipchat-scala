package com.imadethatcow.hipchat.users

import com.imadethatcow.hipchat.common.Common
import Common._
import com.imadethatcow.hipchat.common.caseclass.Photo

class Photos(private[this] val apiToken: String) {
  def update(idOrEmail: String, encodedPhoto: String) = {
    val req = addToken(Photos.urlPut(idOrEmail), apiToken)
      .setBody(mapper.writeValueAsString(Photo(encodedPhoto)))
      .setHeader("Content-Type", "application/json")

    resolveRequest(req, 204) match {
      case Some(r) => true
      case None => false
    }
  }
  def delete(idOrEmail: String) = {
    val req = addToken(Photos.urlDelete(idOrEmail), apiToken)

    resolveRequest(req, 204) match {
      case Some(r) => true
      case None => false
    }
  }
}

object Photos {
  private def url(idOrEmail: String) = apiUrl / "user" / idOrEmail / "photo"

  def urlPut(idOrEmail: String) = url(idOrEmail).PUT
  def urlDelete(idOrEmail: String) = url(idOrEmail).DELETE
}
