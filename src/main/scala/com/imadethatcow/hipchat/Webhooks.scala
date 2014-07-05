package com.imadethatcow.hipchat

import com.imadethatcow.hipchat.common.Common
import Common._
import com.imadethatcow.hipchat.common.enums.WebhookEvent
import dispatch._, Defaults._
import org.slf4j.LoggerFactory
import WebhookEvent._
import scala.util.{Failure, Success, Try}
import com.imadethatcow.hipchat.common.caseclass._
import com.imadethatcow.hipchat.common.caseclass.WebhookCreateResponse
import scala.util.Failure
import scala.Some
import com.imadethatcow.hipchat.common.caseclass.WebhookCreateRequest
import com.imadethatcow.hipchat.common.caseclass.WebhookGetItems
import scala.util.Success
import com.imadethatcow.hipchat.common.caseclass.WebhookSimple

class Webhooks(private[this] val apiToken: String) {
  val log = LoggerFactory.getLogger(getClass)
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

    val jsonOpt = resolveRequest(req, 201)
    jsonOpt match {
      case Some(json) =>
        val webhookResponse = Try[WebhookCreateResponse](mapper.readValue(json, classOf[WebhookCreateResponse]))
        webhookResponse match {
          case Success(v) =>
            Some(v)
          case Failure(e) =>
            log.error("Failed to parse JSON response", e)
            None
        }
      case None =>
        None
    }
  }
  def get(roomIdOrName: Any,
          webhookId: Long): Option[Webhook] = {
    val req = addToken(Webhooks.urlGet(roomIdOrName, webhookId), apiToken)

    val jsonOpt = resolveRequest(req)
    jsonOpt match {
      case Some(json) =>
        val webhookItem = Try[WebhookGetItem](mapper.readValue(json, classOf[WebhookGetItem]))
        webhookItem match {
          case Success(v) =>
            val roomOpt = if (v.room == null) None else Some(v.room)
            val patternOpt = if (v.pattern == null) None else Some(v.pattern)
            val creatorOpt = if (v.creator == null) None else Some(v.creator)
            Some(Webhook(roomOpt, v.url, patternOpt, v.event, v.name, v.id, creatorOpt))
          case Failure(e) =>
            log.error("Failed to parse JSON response", e)
            None
        }
      case None =>
        None
    }
  }

  def getAll(roomIdOrName: Any,
             startIndex: Option[Long] = None,
             maxResults: Option[Long] = None): Option[Seq[WebhookSimple]] = {
    var req = addToken(Webhooks.urlGetAll(roomIdOrName), apiToken)
    for (si <- startIndex) req = req.addQueryParameter("start-index", si.toString)
    for (mr <- maxResults) req = req.addQueryParameter("max-results", mr.toString)

    val jsonOpt = resolveRequest(req)
    jsonOpt match {
      case Some(json) =>
        val webhookItems = Try[WebhookGetItems](mapper.readValue(json, classOf[WebhookGetItems]))
        webhookItems match {
          case Success(v) =>
            Some(v.items.map({ h =>
                val patternOpt = if (h.pattern == null) None else Some(h.pattern)
                WebhookSimple(h.url, patternOpt, h.event, h.name, h.id)
            }))
          case Failure(e) =>
            log.error("Failed to parse JSON response", e)
            None
        }
      case None =>
        None
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
  val log = LoggerFactory.getLogger(getClass)
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






































