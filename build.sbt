import SonatypeKeys._

sonatypeSettings

organization := "com.imadethatcow"

profileName := "poweld"

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := {
  <url>https://github.com/poweld/hipchat-scala</url>
  <licenses>
    <license>
      <name>MIT</name>
      <url>http://opensource.org/licenses/MIT</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:poweld/hipchat-scala.git</url>
    <connection>scm:git:git@github.com:poweld/hipchat-scala.git</connection>
  </scm>
  <developers>
    <developer>
      <id>poweld</id>
      <name>Dave Powell</name>
      <email>poweld@gmail.com</email>
      <url>https://github.com/poweld</url>
    </developer>
    <developer>
      <id>mdotson</id>
      <name>Michael Dotson</name>
    </developer>
    <developer>
      <id>giabao</id>
      <name>Gia Bảo</name>
      <email>giabao@sandinh.net</email>
      <organization>Sân Đình</organization>
      <organizationUrl>http://sandinh.com</organizationUrl>
    </developer>
  </developers>
}

name := "hipchat-scala"

version := "0.1"

scalaVersion := "2.11.2"

crossScalaVersions := Seq(
  "2.11.2",
  "2.10.4"
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8", "-deprecation", "-unchecked", "-feature", //"-optimise",
  "-Xmigration", "-Xfuture", //"–Xverify", "-Xcheck-null", "-Ystatistics",
  "-Yinline-warnings", //"-Yinline",
  "-Ywarn-dead-code", "-Ydead-code"
)

javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.7", "-target", "1.7", "-Xlint:unchecked", "-Xlint:deprecation")

libraryDependencies ++= Seq(
  "ch.qos.logback"                % "logback-classic"       % "1.1.2",
  "com.fasterxml.jackson.core"    % "jackson-core"          % "2.4.2",
  "com.fasterxml.jackson.module"  %% "jackson-module-scala" % "2.4.2",
  "net.databinder.dispatch"       %% "dispatch-core"        % "0.11.2",
  "com.typesafe.akka"             %% "akka-actor"           % "2.3.6",
  "joda-time"                     % "joda-time"             % "2.4",
  "org.joda"                      % "joda-convert"          % "1.7",
  "com.typesafe"                  % "config"                % "1.2.1",
  "org.scalatest"                 %% "scalatest"            % "2.2.2"     % "test"
)

mappings in(Compile, packageBin) ~= {
  _.filterNot {
    case (file, _) => file.getName == "logback.xml"
  }
}
