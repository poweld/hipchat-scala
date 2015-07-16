package com.imadethatcow.hipchat.common.caseclass

import com.imadethatcow.hipchat.common.HipchatValueObject

case class User(mention_name: String, id: Long, name: String) extends HipchatValueObject
case class UsersItem(mention_name: String, id: Long, links: UsersItemLinks, name: String) extends HipchatValueObject
case class UsersItemLinks(self: String) extends HipchatValueObject
case class UsersLinks(self: String, prev: String = "", next: String = "") extends HipchatValueObject
case class UsersResponse(items: Seq[UsersItem], startIndex: Long, maxResults: Long, links: UsersLinks) extends HipchatValueObject
