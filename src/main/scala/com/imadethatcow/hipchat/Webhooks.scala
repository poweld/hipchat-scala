package com.imadethatcow.hipchat

import com.imadethatcow.hipchat.common.{Logging, Common}
import Common._
import com.imadethatcow.hipchat.common.enums.WebhookEvent
import dispatch._, Defaults._
import WebhookEvent._
import com.imadethatcow.hipchat.common.caseclass._

class Webhooks(private[this] val apiToken: String) extends Logging {
  def create(roomIdOrName: Any,
             url: String,
             event: WebhookEvent,
             pattern: Option[String] = None,
             name: Option[String] = None): Option[WebhookCreateResponse] = {
    val webhook = WebhookCreateRequest(url, event.toString, pattern, name)
    val body = mapper.writeValueAsString(webhook)
    val req = addToken(Webhooks.urlPost(roomIdOrName), apiToken)
      .setBody(body)
      .setHeader("Content-Type", "application/json")

    resolveAndDeserialize[WebhookCreateResponse](req, 201)
  }
  def get(roomIdOrName: Any,
          webhookId: Long): Option[Webhook] = {
    val req = addToken(Webhooks.urlGet(roomIdOrName, webhookId), apiToken)

    resolveAndDeserialize[WebhookGetItem](req) match {
      case Some(webhook) =>
        Some(Webhook(webhook.room, webhook.url, webhook.pattern, webhook.event, webhook.name, webhook.id, webhook.creator))
      case None => None
    }
  }

  def getAll(roomIdOrName: Any,
             startIndex: Option[Long] = None,
             maxResults: Option[Long] = None): Option[Seq[WebhookSimple]] = {
    var req = addToken(Webhooks.urlGetAll(roomIdOrName), apiToken)
    for (si <- startIndex) req = req.addQueryParameter("start-index", si.toString)
    for (mr <- maxResults) req = req.addQueryParameter("max-results", mr.toString)

    resolveAndDeserialize[WebhookGetItems](req) match {
      case Some(webhookGetItems) =>
        Some(webhookGetItems.items.map { h =>
          WebhookSimple(h.url, h.pattern, h.event, h.name, h.id)
        })
      case None => None
    }
  }

  def delete(roomIdOrName: Any, webhookId: Long): Boolean = {
    val req = addToken(Webhooks.urlDelete(roomIdOrName, webhookId), apiToken)

    resolveRequest(req, 204) match {
      case Some(r) => true
      case None => false
    }
  }
}

object Webhooks {
  def urlBase(roomIdOrName: Any) = {
    roomIdOrName match {
      case _: String | _: Long =>
        apiUrl / "room" / roomIdOrName.toString / "webhook"
    }
  }
  def urlPost(roomIdOrName: Any) = urlBase(roomIdOrName).POST
  def urlGet(roomIdOrName: Any, webhookId: Long) = (urlBase(roomIdOrName) / webhookId).GET
  def urlGetAll(roomIdOrName: Any) = urlBase(roomIdOrName).GET
  def urlDelete(roomIdOrName: Any, webhookId: Long) = (urlBase(roomIdOrName) / webhookId).DELETE
}






































