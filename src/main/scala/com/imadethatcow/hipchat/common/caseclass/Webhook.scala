package com.imadethatcow.hipchat.common.caseclass

case class Webhook(room: Option[RoomsItem], url: String, pattern: Option[String], event: String, name: String, id: Long, creator: Option[UsersItem]) extends Serializable
case class WebhookSimple(url: String, pattern: Option[String], event: String, name: String, id: Long) extends Serializable

case class WebhookCreateRequest(url: String, event: String, pattern: Option[String] = None, name: Option[String] = None) extends Serializable
case class WebhookItemLinks(self: String) extends Serializable
case class WebhookCreateResponse(id: Long, links: WebhookItemLinks) extends Serializable

case class WebhookGetRequest(room_id_or_name: String, webhook_id: Long) extends Serializable
case class WebhookGetItem(room: Option[RoomsItem], links: WebhookItemLinks, creator: Option[UsersItem], url: String, pattern: Option[String], created: String, event: String, name: String, id: Long) extends Serializable

case class WebhookGetSimpleItem(links: WebhookItemLinks, url: String, pattern: Option[String], event: String, name: String, id: Long) extends Serializable
case class WebhookItemsLinks(self: String, prev: String, next: String) extends Serializable
case class WebhookGetItems(items: Seq[WebhookGetSimpleItem], startIndex: Long, maxResults: Long, links: WebhookItemsLinks) extends Serializable

// The following are all for webhook callbacks, starting with the common classes
case class WebhookRoomLinks(members: Option[String], self: String, webhooks: String) extends Serializable
case class WebhookRoom(id: Long, links: WebhookRoomLinks, name: String) extends Serializable
case class WebhookSenderLinks(self: String) extends Serializable
case class WebhookSender(id: Long, links: WebhookSenderLinks, mention_name: String, name: String) extends Serializable

case class WebhookRoomEnterItem(room: WebhookRoom, sender: WebhookSender) extends Serializable
case class WebhookRoomEnter(event: String, item: WebhookRoomEnterItem, oauth_client_id: Option[String], webhook_id: Long) extends Serializable

case class WebhookRoomExitItem(room: WebhookRoom, sender: WebhookSender) extends Serializable
case class WebhookRoomExit(event: String, item: WebhookRoomExitItem, oauth_client_id: Option[String], webhook_id: Long) extends Serializable

case class WebhookMessage(date: String, file: Option[HCFile], from: From, id: String, mentions: Option[Seq[MentionItem]], message: String) extends Serializable
case class WebhookRoomMessageItem(message: WebhookMessage, room: Option[RoomsItem]) extends Serializable
case class WebhookRoomMessage(event: String, item: WebhookRoomMessageItem, oauth_client_id: Option[String], webhook_id: Long) extends Serializable

case class WebhookRoomNotificationMessage(color: Option[String], date: String, from: Option[String], id: Long, mentions: Seq[Mention], message: String, message_format: String) extends Serializable
case class WebhookRoomNotificationItem(message: WebhookRoomNotificationMessage, room: WebhookRoom) extends Serializable
case class WebhookRoomNotification(event: String, item: WebhookRoomNotificationItem, oauth_client_id: Option[String], webhook_id: String) extends Serializable

case class WebhookRoomTopicChangeItem(room: WebhookRoom, sender: WebhookSender, topic: String) extends Serializable
case class WebhookRoomTopicChange(event: String, item: WebhookRoomTopicChangeItem, oauth_client_id: Option[String], webhook_id: String) extends Serializable
