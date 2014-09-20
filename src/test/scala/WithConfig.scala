import com.typesafe.config.ConfigFactory
import org.scalatest.Assertions

import scala.util.Try

trait WithConfig { this: Assertions =>
  private val _configKey = "com.imadethatcow.hipchat"
  private val _config = Try(ConfigFactory.load.getConfig(_configKey)) getOrElse fail(s"Could not find config")
  def config(key: String) = Try(_config.getString(key)) getOrElse fail(s"Could not find $key in config")
}
