import com.imadethatcow.hipchat._
import com.typesafe.config.ConfigFactory
import org.scalatest._
import scala.util.Try

class RoomsSpec extends FlatSpec {
  val config = ConfigFactory.load
  val API_TOKEN_KEY = "com.imadethatcow.hipchat.auth_token"
  val TEST_ROOM_KEY = "com.imadethatcow.hipchat.test_room"
  val apiTokenTry = Try(config.getString(API_TOKEN_KEY))
  val testRoomTry = Try(config.getString(TEST_ROOM_KEY))
  if (apiTokenTry.isFailure) fail("Could not find api_token in config")
  if (testRoomTry.isFailure) fail("Could not find test_room in config")

  for (apiToken <- apiTokenTry; room <- testRoomTry) {
    val rooms = new Rooms(apiToken)

    "Rooms request" should "return a valid JSON response" in {
      for (seq <- rooms.call(); room <- seq) {
        println(room)
      }
    }

    it should "return a valid JSON response when specifying start-index" in {
      rooms.call(startIndex = Some(1L))
    }

    it should "return a valid JSON response when specifying max-results" in {
      rooms.call(maxResults = Some(1L))
    }

    it should "return a valid JSON response when specifying include-archived" in {
      rooms.call(includeArchived = Some(true))
    }
  }
}
