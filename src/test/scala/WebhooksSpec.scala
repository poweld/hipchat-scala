import com.imadethatcow.hipchat._
import com.typesafe.config.ConfigFactory
import org.scalatest._
import scala.util.Try

class WebhooksSpec extends FlatSpec {
  val config = ConfigFactory.load
  val API_TOKEN_KEY = "com.imadethatcow.hipchat.auth_token"
  val TEST_ROOM_KEY = "com.imadethatcow.hipchat.test_room"
  val apiTokenTry = Try(config.getString(API_TOKEN_KEY))
  val testRoomTry = Try(config.getString(TEST_ROOM_KEY))
  if (apiTokenTry.isFailure) fail("Could not find api_token in config")
  if (testRoomTry.isFailure) fail("Could not find test_room in config")

  for (apiToken <- apiTokenTry; room <- testRoomTry) {
    val url = "http://ec2-54-85-170-167.compute-1.amazonaws.com/webhook"
    val webhooks = new Webhooks(apiToken)
    val event = WebhookEvent.room_message

    "Webhook create request" should "return a valid JSON response" in {
      for (hookResponse <- webhooks.create(room, url, event)) {
        val id = hookResponse.id

        val hookOpt = webhooks.get(room, id)
        if (hookOpt.isDefined)
          println(hookOpt.get)
        else
          fail("Did not receive a valid hook from get request")

        println(s"Deleting webhook id $id")
        assert(webhooks.delete(room, id))
      }
    }

    "Webhook get all request" should "return a valid JSON response" in {
      val hooks = webhooks.getAll(room)
      if (hooks.isDefined)
        for (hook <- hooks)
          println(hook)
      else
        fail("Did not receive a valid hook from get request")
    }
  }
}
