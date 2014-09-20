import com.imadethatcow.hipchat._
import org.scalatest._
import scala.concurrent.ExecutionContext.Implicits.global

class EmoticonsSpec extends FlatSpec with WithConfig {
  val apiToken = config("auth_token")
  val room = config("test_room")

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
