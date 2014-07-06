package com.imadethatcow.hipchat.users

import com.imadethatcow.hipchat.common.Common._
import com.imadethatcow.hipchat.common.Logging
import com.imadethatcow.hipchat.common.caseclass.{User, UsersResponse}

class Users(private[this] val apiToken: String) extends Logging {
  def getAll(startIndex: Option[Long] = None,
          maxResults: Option[Long] = None,
          includeGuests: Option[Boolean] = None,
          includeDeleted: Option[Boolean] = None): Option[Seq[User]] = {
    var req = addToken(Users.url, apiToken)
    for (si <- startIndex) req = req.addQueryParameter("start-index", si.toString)
    for (mr <- maxResults) req = req.addQueryParameter("max-results", mr.toString)
    for (ig <- includeGuests) req = req.addQueryParameter("include-guests", ig.toString)
    for (mr <- includeDeleted) req = req.addQueryParameter("include-deleted", mr.toString)

    resolveAndDeserialize[UsersResponse](req) match {
      case Some(usersResponse) =>
        Some(usersResponse.items.map(r => User(r.mention_name, r.id, r.name)).toSeq)
      case None => None
    }
  }
}

object Users {
  val url = (apiUrl / "user").GET
}







