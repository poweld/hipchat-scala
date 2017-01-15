package com.imadethatcow.hipchat.users

import com.imadethatcow.hipchat.common.Common
import Common._
import com.imadethatcow.hipchat.common.caseclass.Photo
import scala.concurrent.{ExecutionContext, Future}

class Photos(private[this] val apiToken: String)(implicit executor: ExecutionContext) {
  def update(idOrEmail: String, encodedPhoto: String): Future[Boolean] = {
    val req = addToken(Photos.urlPut(idOrEmail), apiToken)
      .setBody(readMapper.writeValueAsString(Photo(encodedPhoto)))
      .setHeader("Content-Type", "application/json")

    resolveBoolRequest(req, 204)
  }
  def delete(idOrEmail: String): Future[Boolean] = {
    val req = addToken(Photos.urlDelete(idOrEmail), apiToken)

    resolveBoolRequest(req, 204)
  }
}

object Photos {
  private def url(idOrEmail: String) = apiUrl / "user" / idOrEmail / "photo"

  def urlPut(idOrEmail: String) = url(idOrEmail).PUT
  def urlDelete(idOrEmail: String) = url(idOrEmail).DELETE
}
