import com.imadethatcow.hipchat._
import com.imadethatcow.hipchat.rooms.ViewHistory
import com.typesafe.config.ConfigFactory
import org.scalatest._
import scala.concurrent.ExecutionContext
import scala.util.Try

class ViewHistorySpec extends FlatSpec {

  val config = ConfigFactory.load
  val API_TOKEN_KEY = "com.imadethatcow.hipchat.auth_token"
  val TEST_ROOM_KEY = "com.imadethatcow.hipchat.test_room"
  val apiTokenTry = Try(config.getString(API_TOKEN_KEY))
  val testRoomTry = Try(config.getString(TEST_ROOM_KEY))
  if (apiTokenTry.isFailure) fail("Could not find api_token in config")
  if (testRoomTry.isFailure) fail("Could not find test_room in config")

  implicit def executionContext = ExecutionContext.Implicits.global

  for (apiToken <- apiTokenTry; room <- testRoomTry) {
    val viewHistory = new ViewHistory(apiToken)

    "ViewHistory request" should "return a valid JSON response" in {
      for (seq <- viewHistory.roomHistory(room); history <- seq) {
        println(history)
      }
    }

    // TODO this is only valid with a non-recent request, need to implement date-specific requests and then fix this test
    // it should "return a valid JSON response when specifying start-index" in {
    //   viewHistory.roomHistory(room, startIndex = Some(1L))
    // }

    it should "return a valid JSON response when specifying max-results" in {
      for (seq <- viewHistory.roomHistory(room, maxResults = Some(10L)); history <- seq) {
         println(history)
       }
     }

    it should "return a valid JSON response when specifying include-archived" in {
      viewHistory.roomHistory(room, reverse = Some(true))
    }
  }
}
