package com.imadethatcow.hipchat.users

import com.imadethatcow.hipchat.common.Common._
import com.imadethatcow.hipchat.common.caseclass.{User, UsersResponse}
import org.slf4j.LoggerFactory

import scala.util.{Failure, Success, Try}

class Users(private[this] val apiToken: String) {
  val log = LoggerFactory.getLogger(getClass)
  def call(startIndex: Option[Long] = None,
          maxResults: Option[Long] = None,
          includeGuests: Option[Boolean] = None,
          includeDeleted: Option[Boolean] = None): Option[Seq[User]] = {
    var req = addToken(Users.url, apiToken)
    for (si <- startIndex) req = req.addQueryParameter("start-index", si.toString)
    for (mr <- maxResults) req = req.addQueryParameter("max-results", mr.toString)
    for (ig <- includeGuests) req = req.addQueryParameter("include-guests", ig.toString)
    for (mr <- includeDeleted) req = req.addQueryParameter("include-deleted", mr.toString)

    val jsonOpt = resolveRequest(req)
    jsonOpt match {
      case Some(json) =>
        val usersResponse = Try[UsersResponse](mapper.readValue(json, classOf[UsersResponse]))
        usersResponse match {
          case Success(v) =>
            Some(v.items.map(r => User(r.mention_name, r.id, r.name)).toSeq)
          case Failure(e) =>
            log.error("Failed to parse JSON response", e)
            None
        }
      case None => None
    }
  }
}

object Users {
  val url = (apiUrlSecure / "user").GET
}







