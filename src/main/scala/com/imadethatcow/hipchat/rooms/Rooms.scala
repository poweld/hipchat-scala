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
import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global

class Rooms(private[this] val apiToken: String) extends Logging {
  def getAll(startIndex: Option[Long] = None,
             maxResults: Option[Long] = None,
             includeArchived: Option[Boolean] = None): Future[Seq[Room]] = {
    var req = addToken(Rooms.url.GET, apiToken)
    for (si <- startIndex) req = req.addQueryParameter("start-index", si.toString)
    for (mr <- maxResults) req = req.addQueryParameter("max-results", mr.toString)
    for (ia <- includeArchived) req = req.addQueryParameter("include-archived", ia.toString)

    resolveAndDeserializeFut[RoomsResponse](req) map {
      response => response.items.map {
        item => Room(item.id, item.name)
      }
    }
  }

  def get(roomIdOrName: Any): Future[RoomDetails] = {
    val req = addToken(Rooms.urlGet(roomIdOrName), apiToken)
    resolveAndDeserializeFut[RoomDetails](req)
  }

  def update(roomIdOrName: Any,
             newRoomName: String,
             newTopic: String,
             newPrivacy: Privacy = Privacy.public,
             newIsArchived: Boolean = false,
             newIsGuestAccessible: Boolean = false,
             newOwnerIdOrEmail: Option[Any] = None): Future[Boolean] = {
    if (newOwnerIdOrEmail.isEmpty) log.warn("Hipchat doesn't currently support setting the newOwnerIdOrEmail to None")
    val name = newRoomName.substring(0, Math.min(newRoomName.length, 50)) // Name may only be 50 characters long
    val roomUpdate = RoomUpdate(name, newPrivacy.toString, newIsArchived, newIsGuestAccessible, newTopic, Owner(newOwnerIdOrEmail))
    val json = writeMapper.writeValueAsString(roomUpdate)
    val req = addToken(Rooms.urlPut(roomIdOrName).PUT, apiToken)
      .setBody(json)
      .setHeader("Content-Type", "application/json")
    resolveRequestFut(req, 204) map { _ => true} recover { case _: Exception => false}
  }

  def setTopic(roomIdOrName: Any,
               topic: String): Future[Boolean] = {
    val topicUrl = (Rooms.url(roomIdOrName) / "topic").PUT
    val req = addToken(topicUrl, apiToken)
      .setBody(writeMapper.writeValueAsString(TopicRequest(topic)))
      .setHeader("Content-Type", "application/json")
    resolveRequestFut(req, 204) map { _ => true} recover { case _: Exception => false}
  }
}

private object Rooms {
  val url = apiUrl / "room"
  private def url(roomIdOrName: Any) = roomIdOrName match {
    case _: Long | _: String =>
      apiUrl / "room" / roomIdOrName.toString
  }
  def urlPut(roomIdOrName: Any) = url(roomIdOrName).PUT
  def urlGet(roomIdOrName: Any) = url(roomIdOrName).GET
}







