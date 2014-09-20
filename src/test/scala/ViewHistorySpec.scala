import com.imadethatcow.hipchat.rooms.ViewHistory
import org.scalatest._
import scala.concurrent.ExecutionContext.Implicits.global

class ViewHistorySpec extends FlatSpec with WithConfig {
  val apiToken = config("auth_token")
  val room = config("test_room")

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
