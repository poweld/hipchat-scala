import com.imadethatcow.hipchat.users.Users
import com.typesafe.config.ConfigFactory
import org.scalatest._
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global

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
      for (seq <- users.getAll(); user <- seq) {
        println(user)
      }
    }

    it should "return a valid JSON response when specifying start-index" in {
      val fut = users.getAll(startIndex = Some(1L))
      Await.ready(fut, Duration.Inf)
    }

    it should "return a valid JSON response when specifying max-results" in {
      val fut = users.getAll(maxResults = Some(1L))
      Await.ready(fut, Duration.Inf)
    }

    it should "return a valid JSON response when specifying include-guests" in {
      val fut = users.getAll(includeGuests = Some(true))
      Await.ready(fut, Duration.Inf)
    }

    // This will respond with a 403 if not using proper credentials
    // it should "return a valid JSON response when specifying include-deleted" in {
    //   users.getAll(includeDeleted = Some(true))
    // }
  }
}
