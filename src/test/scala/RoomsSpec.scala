import com.imadethatcow.hipchat.rooms.Rooms
import org.scalatest._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class RoomsSpec extends FlatSpec with Matchers with WithConfig {
  val apiToken = config("auth_token")
  val room = config("test_room")

  val rooms = new Rooms(apiToken)

  "Room create/delete request" should "return a valid JSON response" in {
    val name = "TestRoom" + System.currentTimeMillis

    val createFut = rooms.create(name)
    val createdRoom = Await.result(createFut, Duration.Inf)
    println(s"Created test room: $name, id: ${createdRoom.id}")

    val deleteRoomFut = rooms.delete(createdRoom.id.toString)
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
