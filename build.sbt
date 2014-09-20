name := "hipchat-scala"

organization := "com.imadethatcow"

version := "0.1"

scalaVersion := "2.11.2"

publishMavenStyle := true

publishTo := {
	val nexus = "https://oss.sonatype.org/"
	if (isSnapshot.value)
		Some("snapshots" at nexus + "content/repositories/snapshots")
	else
		Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT"))

homepage := Some(url("https://github.com/poweld"))

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
