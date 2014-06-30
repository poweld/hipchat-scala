package com.imadethatcow.hipchat.common.caseclass

case class MentionItem(id: Long, links: MentionLinks, mention_name: String, name: String)
case class MentionLinks(self: String)
