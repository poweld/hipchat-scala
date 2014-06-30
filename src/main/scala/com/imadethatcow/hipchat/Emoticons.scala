package com.imadethatcow.hipchat

import com.imadethatcow.hipchat.common.Common._
import com.imadethatcow.hipchat.common.caseclass.{Emoticon, EmoticonsResponse}
import org.slf4j.LoggerFactory

import scala.util.{Failure, Success, Try}

class Emoticons(private[this] val apiToken: String) {
  val log = LoggerFactory.getLogger(getClass)
  def getAll(startIndex: Option[Long] = None,
           maxResults: Option[Long] = None,
           `type`: Option[Boolean] = None): Option[Seq[Emoticon]] = {
    var req = addToken(Emoticons.url, apiToken)
    for (si <- startIndex) req = req.addQueryParameter("start-index", si.toString)
    for (mr <- maxResults) req = req.addQueryParameter("max-results", mr.toString)
    for (t <- `type`) req = req.addQueryParameter("type", t.toString)

    val jsonOpt = resolveRequest(req)
    jsonOpt match {
      case Some(json) =>
        val emoticonsResponse = Try[EmoticonsResponse](mapper.readValue(json, classOf[EmoticonsResponse]))
        emoticonsResponse match {
          case Success(v) =>
            Some(v.items.map(r => Emoticon(r.url, r.id, r.shortcut)).toSeq)
          case Failure(e) =>
            log.error("Failed to parse JSON response", e)
            None
        }
      case None => None
    }
  }

  def get(emoticonIdOrKey: Any)= ???
}

object Emoticons {
  val url = (apiUrlSecure / "emoticon").GET
}







