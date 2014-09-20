import com.typesafe.config.ConfigFactory
import org.scalatest.Assertions

import scala.util.Try

trait WithConfig { this: Assertions =>
  private val _config = ConfigFactory.load.atPath("com.imadethatcow.hipchat")
  def config(key: String) = Try(_config.getString(key)) getOrElse fail(s"Could not find $key in config")
}
