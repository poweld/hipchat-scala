package com.imadethatcow.hipchat

import com.imadethatcow.hipchat.common.Common._
import com.imadethatcow.hipchat.common.Logging
import scala.concurrent.{ExecutionContext, Future}
import com.imadethatcow.hipchat.common.caseclass.Emoticon
import com.imadethatcow.hipchat.common.caseclass.EmoticonDetails
import com.imadethatcow.hipchat.common.caseclass.EmoticonsResponse
import com.imadethatcow.hipchat.common.enums.EmoticonType
import com.imadethatcow.hipchat.common.enums.EmoticonType.EmoticonType

class Emoticons(private[this] val apiToken: String)(implicit executor: ExecutionContext) extends Logging {
  def getAll(startIndex: Option[Int] = None,
             maxResults: Option[Int] = None,
             `type`: Option[EmoticonType] = None): Future[Seq[Emoticon]] = {

    var req = addToken(Emoticons.url, apiToken)

    for (si <- startIndex) {
      if (si < 0) throw new IllegalArgumentException("startIndex must be greater than 0")
      req = req.addQueryParameter("start-index", si.toString)
    }
    for (mr <- maxResults) {
      if (mr < 0 || mr > 100) throw new IllegalArgumentException("maxResults range: 0-100")
      req = req.addQueryParameter("max-results", mr.toString)
    }
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
    case _: Long | _: String | _: Int =>
      (apiUrl / "emoticon" / emoticonIdOrName.toString).GET
  }
}







