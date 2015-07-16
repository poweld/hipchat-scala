package com.imadethatcow.hipchat.common.caseclass

import com.imadethatcow.hipchat.common.HipchatValueObject

case class From(id: Long, links: FromLinks, mention_name: String, name: String) extends HipchatValueObject
case class FromLinks(self: String) extends HipchatValueObject
