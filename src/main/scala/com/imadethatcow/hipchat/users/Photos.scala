package com.imadethatcow.hipchat.users

import com.imadethatcow.hipchat.common.Common
import Common._
import com.imadethatcow.hipchat.common.caseclass.Photo
import scala.concurrent.{ExecutionContext, Future}

class Photos(private[this] val apiToken: String, private[this] val baseUrlOpt: Option[String] = None)(implicit executor: ExecutionContext) {

  private def url(idOrEmail: String) = reqFromBaseUrl(baseUrlOpt) / "user" / idOrEmail / "photo"
  private def urlPut(idOrEmail: String) = url(idOrEmail).PUT
  private def urlDelete(idOrEmail: String) = url(idOrEmail).DELETE

  def update(idOrEmail: String, encodedPhoto: String): Future[Boolean] = {
    val req = addToken(urlPut(idOrEmail), apiToken)
      .setBody(readMapper.writeValueAsString(Photo(encodedPhoto)))
      .setHeader("Content-Type", "application/json")

    resolveBoolRequest(req, 204)
  }
  def delete(idOrEmail: String): Future[Boolean] = {
    val req = addToken(urlDelete(idOrEmail), apiToken)

    resolveBoolRequest(req, 204)
  }
}