package com.imadethatcow.hipchat

import com.imadethatcow.hipchat.common.Common._
import com.imadethatcow.hipchat.common.Logging
import com.imadethatcow.hipchat.common.caseclass.{EmoticonDetails, Emoticon, EmoticonsResponse}

import scala.util.{Failure, Success, Try}

class Emoticons(private[this] val apiToken: String) extends Logging {
  def getAll(startIndex: Option[Long] = None,
             maxResults: Option[Long] = None,
             `type`: Option[Boolean] = None): Option[Seq[Emoticon]] = {
    var req = addToken(Emoticons.url, apiToken)
    for (si <- startIndex) req = req.addQueryParameter("start-index", si.toString)
    for (mr <- maxResults) req = req.addQueryParameter("max-results", mr.toString)
    for (t <- `type`) req = req.addQueryParameter("type", t.toString)

    resolveAndDeserialize[EmoticonsResponse](req) match {
      case Some(emoticonResponse) =>
        Some(emoticonResponse.items.map(r => Emoticon(r.url, r.id, r.shortcut)).toSeq)
      case None => None
    }
  }

  def get(emoticonIdOrKey: Any): Option[EmoticonDetails] = {
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







