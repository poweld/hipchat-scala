package com.imadethatcow.hipchat

import Common._
import dispatch._, Defaults._
import scala.util.{Failure, Success, Try}
import org.slf4j.LoggerFactory
import com.imadethatcow.hipchat.caseclass.{HistoryItem, HistoriesResponse}

class ViewHistory(private[this] val apiToken: String) {
  val log = LoggerFactory.getLogger(getClass)
  def call(roomIdOrName: Any,
           date: Option[Any] = None, // Must be either "recent" or conform to ISO-8601, use joda for the latter
           timezone: Option[String] = None,
           startIndex: Option[Long] = None,
           maxResults: Option[Long] = None,
           reverse: Option[Boolean] = None): Option[Seq[HistoryItem]] = {
    var req = addToken(ViewHistory.url(roomIdOrName), apiToken)
    for (d <- date) req = req.addQueryParameter("date", d.toString)
    for (tz <- timezone) req = req.addQueryParameter("timezone", tz)
    for (si <- startIndex) req = req.addQueryParameter("start-index", si.toString)
    for (mr <- maxResults) req = req.addQueryParameter("max-results", mr.toString)
    for (r <- reverse) req = req.addQueryParameter("reverse", r.toString)

    val jsonOpt = resolveRequest(req)
    jsonOpt match {
      case Some(json) =>
        val historiesResponse = Try[HistoriesResponse](mapper.readValue(json, classOf[HistoriesResponse]))
        historiesResponse match {
          case Success(v) =>
            Some(v.items)
          case Failure(e) =>
            log.error("Failed to parse JSON response", e)
            None
        }
      case None => None
    }
  }
}

object ViewHistory {
  def url(roomIdOrName: Any) = {
    roomIdOrName match {
      case _: String | _: Long =>
        (apiUrl / "room" / roomIdOrName.toString / "history").GET
    }
  }
}





