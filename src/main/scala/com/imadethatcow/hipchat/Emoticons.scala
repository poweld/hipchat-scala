package com.imadethatcow.hipchat

import com.imadethatcow.hipchat.common.Common._
import com.imadethatcow.hipchat.common.Logging
import com.imadethatcow.hipchat.common.caseclass.{EmoticonDetails, Emoticon, EmoticonsResponse}
import org.slf4j.LoggerFactory

import scala.util.{Failure, Success, Try}

class Emoticons(private[this] val apiToken: String) extends Logging {
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
        val emoticonsResponse = Try(mapper.readValue(json, classOf[EmoticonsResponse]))
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

  def get(emoticonIdOrKey: Any): Option[EmoticonDetails] = {
    val req = addToken(Emoticons.url(emoticonIdOrKey), apiToken)
    val jsonOpt = resolveRequest(req)
    jsonOpt match {
      case Some(json) =>
        val emoticonsTry = Try(mapper.readValue(json, classOf[EmoticonDetails]))
        emoticonsTry match {
          case Success(v) => Some(v)
          case Failure(e) =>
            log.error("Failed to parse JSON response", e)
            None
        }
      case None => None
    }
  }
}

object Emoticons {
  val url = (apiUrl / "emoticon").GET
  def url(emoticonIdOrName: Any) = emoticonIdOrName match {
    case _: Long | _: String =>
      (apiUrl / "emoticon" / emoticonIdOrName.toString).GET
  }
}







