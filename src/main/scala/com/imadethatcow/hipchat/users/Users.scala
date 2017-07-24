package com.imadethatcow.hipchat.users

import com.imadethatcow.hipchat.common.Common._
import com.imadethatcow.hipchat.common.Logging
import com.imadethatcow.hipchat.common.caseclass.{User, UsersResponse}
import scala.concurrent.{ExecutionContext, Future}

class Users(private[this] val apiToken: String, private[this] val baseUrlOpt: Option[String] = None)(implicit executor: ExecutionContext) extends Logging {

  private val url = (reqFromBaseUrl(baseUrlOpt) / "user").GET

  def getAll(
    startIndex:     Option[Long]    = None,
    maxResults:     Option[Long]    = None,
    includeGuests:  Option[Boolean] = None,
    includeDeleted: Option[Boolean] = None
  ): Future[Seq[User]] = {
    var req = addToken(url, apiToken)
    for (si <- startIndex) req = req.addQueryParameter("start-index", si.toString)
    for (mr <- maxResults) req = req.addQueryParameter("max-results", mr.toString)
    for (ig <- includeGuests) req = req.addQueryParameter("include-guests", ig.toString)
    for (mr <- includeDeleted) req = req.addQueryParameter("include-deleted", mr.toString)

    resolveAndDeserialize[UsersResponse](req) map {
      response =>
        response.items map {
          item => User(item.mention_name, item.id, item.name)
        }
    }
  }
}