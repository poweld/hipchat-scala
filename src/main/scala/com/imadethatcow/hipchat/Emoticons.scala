package com.imadethatcow.hipchat

import com.imadethatcow.hipchat.common.Common._
import com.imadethatcow.hipchat.common.Logging
import com.imadethatcow.hipchat.common.caseclass.{EmoticonDetails, Emoticon, EmoticonsResponse}
import scala.concurrent.{ExecutionContext, Future}

class Emoticons(private[this] val apiToken: String, private[this] val baseUrlOpt: Option[String] = None)(implicit executor: ExecutionContext) extends Logging {

  private val baseUrl = reqFromBaseUrl(baseUrlOpt)
  private val url = (baseUrl / "emoticon").GET
  private def url(emoticonIdOrName: String) = (baseUrl / "emoticon" / emoticonIdOrName).GET

  def getAll(
    startIndex: Option[Long]    = None,
    maxResults: Option[Long]    = None,
    `type`:     Option[Boolean] = None
  ): Future[Seq[Emoticon]] = {
    var req = addToken(url, apiToken)
    for (si <- startIndex) req = req.addQueryParameter("start-index", si.toString)
    for (mr <- maxResults) req = req.addQueryParameter("max-results", mr.toString)
    for (t <- `type`) req = req.addQueryParameter("type", t.toString)

    resolveAndDeserialize[EmoticonsResponse](req) map {
      response =>
        response.items.map {
          item => Emoticon(item.url, item.id, item.shortcut)
        }.toSeq
    }
  }

  def get(emoticonIdOrKey: String): Future[EmoticonDetails] = {
    val req = addToken(url(emoticonIdOrKey), apiToken)
    resolveAndDeserialize[EmoticonDetails](req)
  }
}