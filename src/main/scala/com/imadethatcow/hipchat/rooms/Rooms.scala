package com.imadethatcow.hipchat.rooms

import com.imadethatcow.hipchat.common.{Logging, Common}
import Common._
import com.imadethatcow.hipchat.common.caseclass._
import com.imadethatcow.hipchat.common.enums.Privacy.Privacy
import com.imadethatcow.hipchat.common.caseclass.RoomsResponse
import com.imadethatcow.hipchat.common.caseclass.RoomDetails
import com.imadethatcow.hipchat.common.caseclass.RoomUpdate
import com.imadethatcow.hipchat.common.caseclass.Room
import scala.Some
import com.imadethatcow.hipchat.common.enums.Privacy
import com.imadethatcow.hipchat.common.enums.Privacy.Privacy

class Rooms(private[this] val apiToken: String) extends Logging {
  def getAll(startIndex: Option[Long] = None,
           maxResults: Option[Long] = None,
           includeArchived: Option[Boolean] = None): Option[Seq[Room]] = {
    var req = addToken(Rooms.url, apiToken)
    for (si <- startIndex) req = req.addQueryParameter("start-index", si.toString)
    for (mr <- maxResults) req = req.addQueryParameter("max-results", mr.toString)
    for (ia <- includeArchived) req = req.addQueryParameter("include-archived", ia.toString)

    resolveAndDeserialize[RoomsResponse](req) match {
      case Some(roomsResponse) =>
        Some(roomsResponse.items.map(r => Room(r.id, r.name)).toSeq)
      case None => None
    }
  }

  def get(roomIdOrName: Any): Option[RoomDetails] = {
    val req = addToken(Rooms.url(roomIdOrName), apiToken)
    resolveAndDeserialize[RoomDetails](req)
  }

  def update(roomIdOrName: Any,
             newRoomName: String,
             newTopic: String,
             newPrivacy: Privacy = Privacy.public,
             newIsArchived: Boolean = false,
             newIsGuestAccessible: Boolean = false,
             newOwnerIdOrEmail: Option[Any] = None): Boolean = {
    if (newOwnerIdOrEmail.isEmpty) log.warn("Hipchat doesn't currently support setting the newOwnerIdOrEmail to None")
    val name = newRoomName.substring(0, Math.min(newRoomName.length, 50)) // Name may only be 50 characters long
    val roomUpdate = RoomUpdate(name, newPrivacy.toString, newIsArchived, newIsGuestAccessible, newTopic, Owner(newOwnerIdOrEmail))
    val json = writeMapper.writeValueAsString(roomUpdate)
    val req = addToken(Rooms.url(roomIdOrName).PUT, apiToken)
      .setBody(json)
      .setHeader("Content-Type", "application/json")
    resolveRequest(req, 204)
    true
  }
}

object Rooms {
  val url = (apiUrl / "room").GET
  def url(roomIdOrName: Any) = roomIdOrName match {
    case _: Long | _: String =>
      (apiUrl / "room" / roomIdOrName.toString).GET
  }
}







