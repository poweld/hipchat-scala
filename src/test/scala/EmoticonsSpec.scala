import com.imadethatcow.hipchat._
import com.imadethatcow.hipchat.common.caseclass.Emoticon
import com.imadethatcow.hipchat.common.enums.EmoticonType
import com.typesafe.config.ConfigFactory
import org.scalatest._
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try

class EmoticonsSpec extends FlatSpec with Matchers {
  val config = ConfigFactory.load
  val API_TOKEN_KEY = "com.imadethatcow.hipchat.auth_token"
  val apiTokenTry = Try(config.getString(API_TOKEN_KEY))
  if (apiTokenTry.isFailure) fail("Could not find api_token in config")

  for (apiToken <- apiTokenTry) {
    val emoticons = new Emoticons(apiToken)

    "Emoticons request" should "return a valid JSON response" in {
      val emoticonsFut = emoticons.getAll()
      val allEmoticons: Seq[Emoticon] = Await.result(emoticonsFut, Duration.Inf)
      allEmoticons map println
    }

    it should "return a valid JSON response when specifying start-index" in {
      for {
        theEmoticons <- emoticons.getAll(startIndex = 2)
        emoticonTwo <- emoticons.get(2)
      } yield theEmoticons.head shouldEqual emoticonTwo
    }

    it should "return a valid JSON response when specifying max-results" in {
      for {
        theEmoticons <- emoticons.getAll(maxResults = 1)
        emoticonZero <- emoticons.get(0)
      } yield theEmoticons.head shouldEqual emoticonZero
    }

    it should "return a valid JSON response when specifying type" in {
      val emoticonsFut = emoticons.getAll(`type` = EmoticonType.all)
      val allEmoticons = Await.result(emoticonsFut, Duration.Inf)
      allEmoticons.size shouldEqual 100
    }

    it should "return a valid JSON response when specifying a large start index" in {
      val emoticonsFut = emoticons.getAll(startIndex = Int.MaxValue)
      val allEmoticons = Await.result(emoticonsFut, Duration.Inf)
      allEmoticons.size shouldEqual 0
    }

    it should "return a valid JSON response when specifying zero max-results" in {
      val emoticonsFut = emoticons.getAll(maxResults = 0)
      val allEmoticons = Await.result(emoticonsFut, Duration.Inf)
      allEmoticons.size shouldEqual 100
    }

    it should "return an exception when getting max-results > 100" in {
      intercept[IllegalArgumentException] {
        val emoticonsFut = emoticons.getAll(maxResults = 101)
        Await.result(emoticonsFut, Duration.Inf)
      }
    }

    it should "return an exception when getting startIndex < 0" in {
      intercept[IllegalArgumentException] {
        val emoticonsFut = emoticons.getAll(startIndex = -1)
        Await.result(emoticonsFut, Duration.Inf)
      }
    }

    it should "return an exception when getting max-results < 0" in {
      intercept[IllegalArgumentException] {
        val emoticonsFut = emoticons.getAll(maxResults = -1)
        Await.result(emoticonsFut, Duration.Inf)
      }
    }

    it should "return an exception when getting an invalid emoticon" in {
      intercept[Exception] {
        val emoticonFut = emoticons.get(java.util.UUID.randomUUID.toString)
        Await.result(emoticonFut, Duration.Inf)
      }
    }
  }
}
