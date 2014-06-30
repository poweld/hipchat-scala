package com.imadethatcow.hipchat.rooms

import com.imadethatcow.hipchat.common.Common
import Common._
import com.imadethatcow.hipchat.common.caseclass.{Room, RoomsResponse}
import org.slf4j.LoggerFactory

import scala.util.{Failure, Success, Try}

class Rooms(private[this] val apiToken: String) {
  val log = LoggerFactory.getLogger(getClass)
  def call(startIndex: Option[Long] = None,
           maxResults: Option[Long] = None,
           includeArchived: Option[Boolean] = None): Option[Seq[Room]] = {
    var req = addToken(Rooms.url, apiToken)
    for (si <- startIndex) req = req.addQueryParameter("start-index", si.toString)
    for (mr <- maxResults) req = req.addQueryParameter("max-results", mr.toString)
    for (ia <- includeArchived) req = req.addQueryParameter("include-archived", ia.toString)

    val jsonOpt = resolveRequest(req)
    jsonOpt match {
      case Some(json) =>
        val roomsResponse = Try[RoomsResponse](mapper.readValue(json, classOf[RoomsResponse]))
        roomsResponse match {
          case Success(v) =>
            Some(v.items.map(r => Room(r.id, r.name)).toSeq)
          case Failure(e) =>
            log.error("Failed to parse JSON response", e)
            None
        }
      case None => None
    }
  }
}

object Rooms {
  val url = (apiUrlSecure / "room").GET
}







