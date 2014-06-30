package com.imadethatcow.hipchat.common.caseclass

case class From(id: Long, links: FromLinks, mention_name: String, name: String)
case class FromLinks(self: String)
