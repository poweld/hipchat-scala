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
  def getAll(startIndex: Int = 0,
             maxResults: Int = 100,
             `type`: EmoticonType = EmoticonType.all): Future[Seq[Emoticon]] = {
    if (startIndex < 0) throw new IllegalArgumentException("startIndex must be greater than 0")
    if (maxResults < 0 || maxResults > 100) throw new IllegalArgumentException("maxResults range: 0-100")
    var req = addToken(Emoticons.url, apiToken)
    req = req.addQueryParameter("start-index", startIndex.toString)
    req = req.addQueryParameter("max-results", maxResults.toString)
    req = req.addQueryParameter("type", `type`.toString)

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







