import com.imadethatcow.hipchat._
import com.typesafe.config.ConfigFactory
import org.scalatest._
import scala.util.Try

class UsersSpec extends FlatSpec {
  val config = ConfigFactory.load
  val API_TOKEN_KEY = "com.imadethatcow.hipchat.auth_token"
  val TEST_ROOM_KEY = "com.imadethatcow.hipchat.test_room"
  val apiTokenTry = Try(config.getString(API_TOKEN_KEY))
  val testRoomTry = Try(config.getString(TEST_ROOM_KEY))
  if (apiTokenTry.isFailure) fail("Could not find api_token in config")
  if (testRoomTry.isFailure) fail("Could not find test_room in config")

  for (apiToken <- apiTokenTry; room <- testRoomTry) {
    val users = new Users(apiToken)

    "Users request" should "return a valid JSON response" in {
      for (seq <- users.call(); user <- seq) {
        println(user)
      }
    }

    it should "return a valid JSON response when specifying start-index" in {
      users.call(startIndex = Some(1L))
    }

    it should "return a valid JSON response when specifying max-results" in {
      users.call(maxResults = Some(1L))
    }

    it should "return a valid JSON response when specifying include-guests" in {
      users.call(includeGuests = Some(true))
    }

    // This will respond with a 403 if not using proper credentials
    // it should "return a valid JSON response when specifying include-deleted" in {
    //   users.call(includeDeleted = Some(true))
    // }
  }
}
