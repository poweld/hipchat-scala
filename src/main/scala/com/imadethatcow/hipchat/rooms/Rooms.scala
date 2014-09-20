package com.imadethatcow.hipchat.rooms

import com.imadethatcow.hipchat.common.{Logging, Common}
import Common._
import com.imadethatcow.hipchat.common.caseclass._
import com.imadethatcow.hipchat.common.caseclass.RoomsResponse
import com.imadethatcow.hipchat.common.caseclass.RoomDetails
import com.imadethatcow.hipchat.common.caseclass.RoomUpdate
import com.imadethatcow.hipchat.common.caseclass.Room
import com.imadethatcow.hipchat.common.enums.Privacy
import com.imadethatcow.hipchat.common.enums.Privacy.Privacy
import scala.concurrent.{ExecutionContext, Future}

class Rooms(private[this] val apiToken: String)(implicit executor: ExecutionContext) extends Logging {
  def create(name: String,
             ownerIdEmailOrMentionName: Option[String] = None,
             guestAccess: Boolean = false,
             privacy : Privacy = Privacy.public): Future[RoomsCreateResponse] = {
    val room = RoomsCreateRequest(guestAccess, name, ownerIdEmailOrMentionName, privacy.toString)
    val body = mapper.writeValueAsString(room)
    val req = addToken(Rooms.url.POST, apiToken)
      .setBody(body)
      .setHeader("Content-Type", "application/json")
    resolveAndDeserialize[RoomsCreateResponse](req, 201)
  }

  def delete(roomIdOrName: String): Future[Boolean] = {
    val req = addToken(Rooms.urlDelete(roomIdOrName), apiToken)
    resolveRequest(req, 204) map { _ => true} recover { case _: Exception => false }
  }

  def getAll(startIndex: Option[Long] = None,
             maxResults: Option[Long] = None,
             includeArchived: Option[Boolean] = None): Future[Seq[Room]] = {
    var req = addToken(Rooms.url.GET, apiToken)
    for (si <- startIndex) req = req.addQueryParameter("start-index", si.toString)
    for (mr <- maxResults) req = req.addQueryParameter("max-results", mr.toString)
    for (ia <- includeArchived) req = req.addQueryParameter("include-archived", ia.toString)

    resolveAndDeserialize[RoomsResponse](req) map {
      response => response.items.map {
        item => Room(item.id, item.name)
      }
    }
  }

  def get(roomIdOrName: String): Future[RoomDetails] = {
    val req = addToken(Rooms.urlGet(roomIdOrName), apiToken)
    resolveAndDeserialize[RoomDetails](req)
  }

  def update(roomIdOrName: String,
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
    resolveRequest(req, 204) map { _ => true} recover { case _: Exception => false}
  }

  def setTopic(roomIdOrName: String,
               topic: String): Future[Boolean] = {
    val topicUrl = (Rooms.url(roomIdOrName) / "topic").PUT
    val req = addToken(topicUrl, apiToken)
      .setBody(writeMapper.writeValueAsString(TopicRequest(topic)))
      .setHeader("Content-Type", "application/json")
    resolveRequest(req, 204) map { _ => true} recover { case _: Exception => false}
  }
}

private object Rooms {
  val url = apiUrl / "room"
  private def url(roomIdOrName: String) = apiUrl / "room" / roomIdOrName
  def urlPut(roomIdOrName: String) = url(roomIdOrName).PUT
  def urlGet(roomIdOrName: String) = url(roomIdOrName).GET
  def urlDelete(roomIdOrName: String) = url(roomIdOrName).DELETE
}
