package com.imadethatcow.hipchat.common.caseclass

import com.imadethatcow.hipchat.common.HipchatValueObject

case class Room(id: Long, name: String) extends HipchatValueObject
case class RoomsItem(id: Long, links: RoomsItemLinks, name: String) extends HipchatValueObject
case class RoomsItemLinks(self: String, webhooks: String, members: Option[String]) extends HipchatValueObject
case class RoomsLinks(self: String, prev: Option[String], next: Option[String]) extends HipchatValueObject
case class RoomsResponse(items: Seq[RoomsItem], startIndex: Long, maxResults: Long, links: RoomsLinks) extends HipchatValueObject
case class RoomsCreateResponse(id: Long, links: RoomDetailsStatsLinks) extends HipchatValueObject
case class RoomsCreateRequest(guest_access: Boolean, name: String, owner_user_id: Option[String], privacy: String) extends HipchatValueObject
case class RoomDetailsStatsLinks(self: String) extends HipchatValueObject
case class RoomDetailsStats(links: RoomDetailsStatsLinks) extends HipchatValueObject
case class RoomDetailsLinks(self: String, webhooks: String, members: String) extends HipchatValueObject
case class RoomDetailsParticipantsLinks(self: String) extends HipchatValueObject
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
) extends HipchatValueObject

case class Owner(id: Option[Any]) extends HipchatValueObject
case class RoomUpdate(
  name:                String,
  privacy:             String,
  is_archived:         Boolean,
  is_guest_accessible: Boolean,
  topic:               String,
  owner:               Owner
) extends HipchatValueObject

case class TopicRequest(topic: String) extends HipchatValueObject
