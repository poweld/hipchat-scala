package com.imadethatcow.hipchat.rooms

import com.imadethatcow.hipchat.common.{Logging, Common}
import Common._
import com.imadethatcow.hipchat.common.caseclass.RoomNotification
import com.imadethatcow.hipchat.common.enums.{MessageFormat, Color}
import scala.concurrent.{ExecutionContext, Future}
import dispatch._

class RoomNotifier(private[this] val apiToken: String, private[this] val baseUrlOpt: Option[String] = None)(implicit executor: ExecutionContext) extends Logging {

  import MessageFormat._
  import com.imadethatcow.hipchat.common.enums.Color._

  private def url(roomIdOrName: String) = {
    val baseUrl = reqFromBaseUrl(baseUrlOpt)
    (baseUrl / "room" / roomIdOrName / "notification").POST
  }

  def sendNotification(
    roomIdOrName:  String,
    message:       String,
    color:         Color         = Color.yellow,
    notify:        Boolean       = false,
    messageFormat: MessageFormat = MessageFormat.html
  ): Future[Boolean] = {
    val notification = RoomNotification(color.toString, message, notify, messageFormat.toString)
    // TODO: the following is an ugly hack. "notify" as a reserved name, so we can't use it in the case class
    val body = readMapper.writeValueAsString(notification).replaceFirst(""""_notify""", """"notify""")
    val req = addToken(url(roomIdOrName), apiToken)
      .setBody(body)
      .setHeader("Content-Type", "application/json")

    resolveBoolRequest(req, 204)
  }
}