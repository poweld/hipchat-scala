package com.imadethatcow.hipchat.common.caseclass

case class Room(id: Long, name: String)
case class RoomsItem(id: Long, links: RoomsItemLinks, name: String)
case class RoomsItemLinks(self: String, webhooks: String, members: Option[String])
case class RoomsLinks(self: String, prev: Option[String], next: Option[String])
case class RoomsResponse(items: Seq[RoomsItem], startIndex: Long, maxResults: Long, links: RoomsLinks)

case class RoomDetailsStatsLinks(self: String)
case class RoomDetailsStats(links: RoomDetailsStatsLinks)
case class RoomDetailsLinks(self: String, webhooks: String, members: String)
case class RoomDetailsParticipantsLinks(self: String)
case class RoomDetails(xmpp_jid: String,
                       statistics: RoomDetailsStats,
                       name: String,
                       links: RoomDetailsLinks,
                       created: String,
                       is_archived: Boolean,
                       privacy: String,
                       is_guest_accessible: Boolean,
                       topic: String,
                       participants: Seq[UsersItem],
                       owner: UsersItem,
                       id: Long,
                       guest_access_url: String,
                       last_active: String)

case class Owner(id: Option[Any])
case class RoomUpdate(name: String,
                      privacy: String,
                      is_archived: Boolean,
                      is_guest_accessible: Boolean,
                      topic: String,
                      owner: Owner)

case class TopicRequest(topic: String)
