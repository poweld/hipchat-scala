package com.imadethatcow.hipchat.common.caseclass

import com.imadethatcow.hipchat.common.HipchatValueObject

case class MentionItem(id: Long, links: MentionLinks, mention_name: String, name: String) extends HipchatValueObject
case class MentionLinks(self: String) extends HipchatValueObject
