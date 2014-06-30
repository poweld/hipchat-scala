package com.imadethatcow.hipchat.common.caseclass

case class Room(id: Long, name: String)
case class RoomsItem(id: Long, links: RoomsItemLinks, name: String)
case class RoomsItemLinks(self: String, webhooks: String, members: Option[String])
case class RoomsLinks(self: String, prev: Option[String], next: Option[String])
case class RoomsResponse(items: Seq[RoomsItem], startIndex: Long, maxResults: Long, links: RoomsLinks)
