package com.imadethatcow.hipchat.common.caseclass

case class User(mention_name: String, id: Long, name: String)
case class UsersItem(mention_name: String, id: Long, links: UsersItemLinks, name: String)
case class UsersItemLinks(self: String)
case class UsersLinks(self: String, prev: String = "", next: String = "")
case class UsersResponse(items: Seq[UsersItem], startIndex: Long, maxResults: Long, links: UsersLinks)
