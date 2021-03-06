package com.imadethatcow.hipchat.rooms

import com.imadethatcow.hipchat.common.{Logging, Common}
import Common._
import com.imadethatcow.hipchat.common.caseclass.{HistoriesResponse, HistoryItem}

import scala.concurrent.{ExecutionContext, Future}

class ViewHistory(private[this] val apiToken: String, private[this] val baseUrlOpt: Option[String] = None)(implicit executor: ExecutionContext) extends Logging {

  private def url(roomIdOrName: String) = (reqFromBaseUrl(baseUrlOpt) / "room" / roomIdOrName / "history").GET

  def roomHistory(
    roomIdOrName: String,
    date:         Option[Any]     = None, // Must be either "recent" or conform to ISO-8601, use joda for the latter
    timezone:     Option[String]  = None,
    startIndex:   Option[Long]    = None,
    maxResults:   Option[Long]    = None,
    reverse:      Option[Boolean] = None
  ): Future[Seq[HistoryItem]] = {
    var req = addToken(url(roomIdOrName), apiToken)
    for (d <- date) req = req.addQueryParameter("date", d.toString)
    for (tz <- timezone) req = req.addQueryParameter("timezone", tz)
    for (si <- startIndex) req = req.addQueryParameter("start-index", si.toString)
    for (mr <- maxResults) req = req.addQueryParameter("max-results", mr.toString)
    for (r <- reverse) req = req.addQueryParameter("reverse", r.toString)

    resolveAndDeserialize[HistoriesResponse](req) map {
      response => response.items
    }
  }
}