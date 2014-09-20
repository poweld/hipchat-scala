xerial.sbt.Sonatype.sonatypeSettings

publishMavenStyle := true

publishArtifact in Test := false

pomExtra := <url>https://github.com/poweld/hipchat-scala</url>
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
    </developer>
    <developer>
      <id>giabao</id>
      <name>Gia Bảo</name>
      <email>giabao@sandinh.net</email>
      <organization>Sân Đình</organization>
      <organizationUrl>http://sandinh.com</organizationUrl>
    </developer>
  </developers>