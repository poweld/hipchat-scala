import com.imadethatcow.hipchat._
import com.imadethatcow.hipchat.common.enums.WebhookEvent
import org.scalatest._
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

class WebhooksSpec extends FlatSpec with WithConfig {
  val apiToken = config("auth_token")
  val room = config("test_room")

  val url = "http://www.imadethatcow.com"
  val webhooks = new Webhooks(apiToken)
  val event = WebhookEvent.room_message

  "Webhook create request" should "return a valid JSON response" in {
    for (hookResponse <- webhooks.create(room, url, event)) {
      val id = hookResponse.id

      val hookFut = webhooks.get(room, id)
      println(Await.result(hookFut, Duration.Inf))

      println(s"Deleting webhook id $id")
      val deleteFut = webhooks.delete(room, id)
      Await.result(deleteFut, Duration.Inf)
    }
  }

  "Webhook get all request" should "return a valid JSON response" in {
    val hooks = webhooks.getAll(room)
    println(Await.result(hooks, Duration.Inf))
  }
}
