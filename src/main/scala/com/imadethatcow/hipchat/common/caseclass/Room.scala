package com.imadethatcow.hipchat.common.caseclass

case class Room(id: Long, name: String) extends Serializable
case class RoomsItem(id: Long, links: RoomsItemLinks, name: String) extends Serializable
case class RoomsItemLinks(self: String, webhooks: String, members: Option[String]) extends Serializable
case class RoomsLinks(self: String, prev: Option[String], next: Option[String]) extends Serializable
case class RoomsResponse(items: Seq[RoomsItem], startIndex: Long, maxResults: Long, links: RoomsLinks) extends Serializable
case class RoomsCreateResponse(id: Long, links: RoomDetailsStatsLinks) extends Serializable
case class RoomsCreateRequest(guest_access: Boolean, name: String, owner_user_id: Option[String], privacy: String) extends Serializable
case class RoomDetailsStatsLinks(self: String) extends Serializable
case class RoomDetailsStats(links: RoomDetailsStatsLinks) extends Serializable
case class RoomDetailsLinks(self: String, webhooks: String, members: String) extends Serializable
case class RoomDetailsParticipantsLinks(self: String) extends Serializable
case class RoomDetails(
  xmpp_jid:            String,
  statistics:          RoomDetailsStats,
  name:                String,
  links:               RoomDetailsLinks,
  created:             String,
  is_archived:         Boolean,
  privacy:             String,
  is_guest_accessible: Boolean,
  topic:               String,
  participants:        Seq[UsersItem],
  owner:               UsersItem,
  id:                  Long,
  guest_access_url:    String,
  last_active:         String
) extends Serializable

case class Owner(id: Option[Any]) extends Serializable
case class RoomUpdate(
  name:                String,
  privacy:             String,
  is_archived:         Boolean,
  is_guest_accessible: Boolean,
  topic:               String,
  owner:               Owner
) extends Serializable

case class TopicRequest(topic: String) extends Serializable
