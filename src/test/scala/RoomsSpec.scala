import com.imadethatcow.hipchat._
import com.imadethatcow.hipchat.common.caseclass.User
import com.imadethatcow.hipchat.common.enums.Privacy
import com.imadethatcow.hipchat.rooms.Rooms
import com.typesafe.config.ConfigFactory
import org.scalatest._
import scala.concurrent.Await
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class RoomsSpec extends FlatSpec with Matchers {
  val config = ConfigFactory.load
  val API_TOKEN_KEY = "com.imadethatcow.hipchat.auth_token"
  val TEST_ROOM_KEY = "com.imadethatcow.hipchat.test_room"
  val apiTokenTry = Try(config.getString(API_TOKEN_KEY))
  val testRoomTry = Try(config.getString(TEST_ROOM_KEY))
  if (apiTokenTry.isFailure) fail("Could not find api_token in config")
  if (testRoomTry.isFailure) fail("Could not find test_room in config")

  for (apiToken <- apiTokenTry; room <- testRoomTry) {
    val rooms = new Rooms(apiToken)

    "Room create/delete request" should "return a valid JSON response" in {
      val name = "TestRoom" + System.currentTimeMillis

      val createFut = rooms.create(name)
      val createdRoom = Await.result(createFut, Duration.Inf)
      println(s"Created test room: $name, id: ${createdRoom.id}")

      val deleteRoomFut = rooms.delete(createdRoom.id)
      println(s"Deleting the test room")
      Await.result(deleteRoomFut, Duration.Inf) shouldEqual true
    }

    "Rooms request" should "return a valid JSON response" in {
      for (seq <- rooms.getAll(); room <- seq) {
        println(room)
      }
    }

    it should "return a valid JSON response when specifying start-index" in {
      rooms.getAll(startIndex = Some(1L))
    }

    it should "return a valid JSON response when specifying max-results" in {
      rooms.getAll(maxResults = Some(1L))
    }

    it should "return a valid JSON response when specifying include-archived" in {
      rooms.getAll(includeArchived = Some(true))
    }

    "Room details request" should "Return a valid JSON response" in {
      for (roomDetails <- rooms.get(room)) println(roomDetails)
    }

    "Set topic request" should "Return true" in {
      Await.result(rooms.setTopic(room, "Hello world!"), Duration.Inf) shouldEqual true
      val fut = rooms.setTopic(room, "Hello world!")
      val success = Await.result(fut, Duration.Inf)
      success shouldEqual true
    }
  }
}
