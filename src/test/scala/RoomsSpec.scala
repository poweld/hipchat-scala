import com.imadethatcow.hipchat._
import com.imadethatcow.hipchat.common.caseclass.User
import com.imadethatcow.hipchat.common.enums.Privacy
import com.imadethatcow.hipchat.rooms.Rooms
import com.typesafe.config.ConfigFactory
import org.scalatest._
import scala.util.Try

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

    val guest_access = true;
    val name = "Hello dude2";

    "Room create request" should "return a valid JSON response" in {
      for (roomResponse <- rooms.create(guest_access,name,privacy= Privacy.`private` )) {

        val id = roomResponse.id

        val roomOpt = rooms.get(id)
        if (roomOpt.isDefined)
          println(roomOpt.get)
        else
          fail("Did not receive a valid room from get request")

        println(s"Deleting room id $id")// test delete
        assert(rooms.delete(id))
      }
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
      println(rooms.get(room))
    }

    "Set topic request" should "Return true" in {
      rooms.setTopic(room, "Hello world!") shouldEqual true
    }
  }
}
