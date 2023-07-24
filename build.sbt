name := "scala-course"
version := "1.0"
scalaVersion := "2.12.16"


libraryDependencies ++= Seq(
  "junit" % "junit" % "4.13.2" % Test,
  "org.scalatest" % "scalatest_2.12" % "3.0.5" % Test,
  "org.scalamock" % "scalamock_2.12" % "4.4.0" % Test,
  "org.junit.vintage" % "junit-vintage-engine" % "5.8.2" % Test
)
