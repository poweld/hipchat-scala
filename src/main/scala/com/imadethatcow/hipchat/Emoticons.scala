package com.imadethatcow.hipchat

import com.imadethatcow.hipchat.common.Common._
import com.imadethatcow.hipchat.common.Logging
import com.imadethatcow.hipchat.common.caseclass.{EmoticonDetails, Emoticon, EmoticonsResponse}
import scala.concurrent.{ExecutionContext, Future}

class Emoticons(private[this] val apiToken: String)(implicit executor: ExecutionContext) extends Logging {
  def getAll(startIndex: Option[Long] = None,
             maxResults: Option[Long] = None,
             `type`: Option[Boolean] = None): Future[Seq[Emoticon]] = {
    var req = addToken(Emoticons.url, apiToken)
    for (si <- startIndex) req = req.addQueryParameter("start-index", si.toString)
    for (mr <- maxResults) req = req.addQueryParameter("max-results", mr.toString)
    for (t <- `type`) req = req.addQueryParameter("type", t.toString)

    resolveAndDeserialize[EmoticonsResponse](req) map {
      response => response.items.map {
        item => Emoticon(item.url, item.id, item.shortcut)
      }.toSeq
    }
  }

  def get(emoticonIdOrKey: Any): Future[EmoticonDetails] = {
    val req = addToken(Emoticons.url(emoticonIdOrKey), apiToken)
    resolveAndDeserialize[EmoticonDetails](req)
  }
}

object Emoticons {
  val url = (apiUrl / "emoticon").GET
  def url(emoticonIdOrName: Any) = emoticonIdOrName match {
    case _: Long | _: String =>
      (apiUrl / "emoticon" / emoticonIdOrName.toString).GET
  }
}







