package com.imadethatcow.hipchat.rooms

import com.imadethatcow.hipchat.common.{Logging, Common}
import Common._
import com.imadethatcow.hipchat.common.caseclass.RoomNotification
import com.imadethatcow.hipchat.common.enums.{MessageFormat, Color}

class RoomNotifier(private[this] val apiToken: String) extends Logging {
  import MessageFormat._
  import com.imadethatcow.hipchat.common.enums.Color._
  def sendNotification(roomIdOrName: AnyRef,
           message: String,
           color: Color = Color.yellow,
           notify: Boolean = false,
           messageFormat: MessageFormat = MessageFormat.html): Boolean = {
    val notification = RoomNotification(color.toString, message, notify, messageFormat.toString)
    // TODO: the following is an ugly hack. "notify" as a reserved name, so we can't use it in the case class
    val body = mapper.writeValueAsString(notification).replaceFirst(""""_notify""", """"notify""")
    val req = addToken(RoomNotifier.url(roomIdOrName), apiToken)
      .setBody(body)
      .setHeader("Content-Type", "application/json")

    resolveRequest(req, 204) match {
      case Some(r) => true
      case None => false
    }
  }
}

object RoomNotifier {
  def url(roomIdOrName: Any) = {
    roomIdOrName match {
      case _: String | _: Long =>
        (apiUrl/ "room" / roomIdOrName.toString / "notification").POST
    }
  }
}


