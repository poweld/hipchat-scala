package com.imadethatcow.hipchat.common.caseclass

case class User(mention_name: String, id: Long, name: String) extends Serializable
case class UsersItem(mention_name: String, id: Long, links: UsersItemLinks, name: String) extends Serializable
case class UsersItemLinks(self: String) extends Serializable
case class UsersLinks(self: String, prev: String = "", next: String = "") extends Serializable
case class UsersResponse(items: Seq[UsersItem], startIndex: Long, maxResults: Long, links: UsersLinks) extends Serializable
