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

version := "1.2"

scalaVersion := "2.12.3"

crossScalaVersions := Seq(
  "2.12.3",
  "2.11.2"
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8", "-deprecation", "-unchecked", "-feature", //"-optimise",
  "-Xmigration", "-Xfuture", //"–Xverify", "-Xcheck-null", "-Ystatistics",
  "-Ywarn-dead-code"
)

javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.7", "-target", "1.7", "-Xlint:unchecked", "-Xlint:deprecation")

libraryDependencies ++= Seq(
  "ch.qos.logback"                % "logback-classic"       % "1.1.2",
  "com.fasterxml.jackson.core"    % "jackson-core"          % "2.8.4",
  "com.fasterxml.jackson.module"  %% "jackson-module-scala" % "2.8.4",
  "net.databinder.dispatch"       %% "dispatch-core"        % "0.13.1",
  "com.typesafe.akka"             %% "akka-actor"           % "2.5.4",
  "joda-time"                     % "joda-time"             % "2.4",
  "org.joda"                      % "joda-convert"          % "1.7",
  "com.typesafe"                  % "config"                % "1.3.1",
  "org.scalatest"                 %% "scalatest"            % "3.0.4"     % "test"
)

mappings in(Compile, packageBin) ~= {
  _.filterNot {
    case (file, _) => file.getName == "logback.xml"
  }
}
