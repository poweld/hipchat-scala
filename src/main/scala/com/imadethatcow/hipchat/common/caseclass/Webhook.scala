package com.imadethatcow.hipchat.common.caseclass

case class Webhook(room: Option[RoomsItem], url: String, pattern: Option[String], event: String, name: String, id: Long, creator: Option[UsersItem])
case class WebhookSimple(url: String, pattern: Option[String], event: String, name: String, id: Long)

case class WebhookCreateRequest(url: String, event: String, pattern: Option[String] = None, name: Option[String] = None)
case class WebhookItemLinks(self: String)
case class WebhookCreateResponse(id: Long, links: WebhookItemLinks)

case class WebhookGetRequest(room_id_or_name: String, webhook_id: Long)
case class WebhookGetItem(room: Option[RoomsItem], links: WebhookItemLinks, creator: Option[UsersItem], url: String, pattern: Option[String], created: String, event: String, name: String, id: Long)

case class WebhookGetSimpleItem(links: WebhookItemLinks, url: String, pattern: Option[String], event: String, name: String, id: Long)
case class WebhookItemsLinks(self: String, prev: String, next: String)
case class WebhookGetItems(items: Seq[WebhookGetSimpleItem], startIndex: Long, maxResults: Long, links: WebhookItemsLinks)

// The following are all for webhook callbacks, starting with the common classes
case class WebhookRoomLinks(members: Option[String], self: String, webhooks: String)
case class WebhookRoom(id: Long, links: WebhookRoomLinks, name: String)
case class WebhookSenderLinks(self: String)
case class WebhookSender(id: Long, links: WebhookSenderLinks, mention_name: String, name: String)

case class WebhookRoomEnterItem(room: WebhookRoom, sender: WebhookSender)
case class WebhookRoomEnter(event: String, item: WebhookRoomEnterItem, oauth_client_id: Option[String], webhook_id: Long)

case class WebhookRoomExitItem(room: WebhookRoom, sender: WebhookSender)
case class WebhookRoomExit(event: String, item: WebhookRoomExitItem, oauth_client_id: Option[String], webhook_id: Long)

case class WebhookMessage(date: String, file: Option[HCFile], from: From, id: String, mentions: Option[Seq[MentionItem]], message: String)
case class WebhookRoomMessageItem(message: WebhookMessage, room: Option[RoomsItem])
case class WebhookRoomMessage(event: String, item: WebhookRoomMessageItem, oauth_client_id: Option[String], webhook_id: Long)

case class WebhookRoomNotificationMessage(color: Option[String], date: String, from: Option[String], id: Long, mentions: Seq[Mention], message: String, message_format: String)
case class WebhookRoomNotificationItem(message: WebhookRoomNotificationMessage, room: WebhookRoom)
case class WebhookRoomNotification(event: String, item: WebhookRoomNotificationItem, oauth_client_id: Option[String], webhook_id: String)

case class WebhookRoomTopicChangeItem(room: WebhookRoom, sender: WebhookSender, topic: String)
case class WebhookRoomTopicChange(event: String, item: WebhookRoomTopicChangeItem, oauth_client_id: Option[String], webhook_id: String)
