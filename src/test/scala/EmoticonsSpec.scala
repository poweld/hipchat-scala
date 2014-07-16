import com.imadethatcow.hipchat._
import com.typesafe.config.ConfigFactory
import org.scalatest._
import scala.concurrent.ExecutionContext

class EmoticonsSpec extends FlatSpec {
  val config = ConfigFactory.load
  val API_TOKEN_KEY = "com.imadethatcow.hipchat.auth_token"
  val TEST_ROOM_KEY = "com.imadethatcow.hipchat.test_room"
  val apiTokenTry = Option(config.getString(API_TOKEN_KEY))
  val testRoomTry = Option(config.getString(TEST_ROOM_KEY))
  if (apiTokenTry.isEmpty) fail("Could not find api_token in config")
  if (testRoomTry.isEmpty) fail("Could not find test_room in config")

  implicit def executionContext = ExecutionContext.Implicits.global

  for (apiToken <- apiTokenTry; room <- testRoomTry) {
    val emoticons = new Emoticons(apiToken)

    "Emoticons request" should "return a valid JSON response" in {
      for (seq <- emoticons.getAll(); emoticon <- seq) {
        println(emoticon)
      }
    }

    it should "return a valid JSON response when specifying start-index" in {
      emoticons.getAll(startIndex = Some(1L))
    }

    it should "return a valid JSON response when specifying max-results" in {
      emoticons.getAll(maxResults = Some(1L))
    }

    it should "return a valid JSON response when specifying type" in {
      emoticons.getAll(`type` = Some(true))
    }

    "Emoticon details request" should "return a valid JSON response" in {
      println(emoticons.get("okay"))
    }
  }
}
