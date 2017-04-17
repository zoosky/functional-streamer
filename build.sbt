val ScalaVer = "2.12.1"

val ScalaJSDom = "0.9.1"
val ScalaTags  = "0.6.3"

val ApacheIO    = "2.5"
val ApacheCodec = "1.10"

val jsPath = file("assets") / "js"

scalaVersion in ThisBuild := ScalaVer

lazy val commonSettings = Seq(
  name    := "functionalstreamer"
, version := "0.1.0"
, scalaVersion := ScalaVer
, libraryDependencies ++= Seq(
    "com.lihaoyi" %%% "scalatags" % ScalaTags
  )
, scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-language:experimental.macros",
      "-unchecked",
      "-Xlint",
      "-Ywarn-dead-code",
      "-Xfuture",
      "-Ypartial-unification")
)

lazy val root = project.in(file(".")).
  aggregate(js, jvm)
  .settings(
    publish := {},
    publishLocal := {}
  )

lazy val functionalstreamer = crossProject.in(file("."))
  .settings(commonSettings)

lazy val jvm = functionalstreamer.jvm
  .settings(
    libraryDependencies ++= Seq(
      "commons-io"    % "commons-io"    % ApacheIO
    , "commons-codec" % "commons-codec" % ApacheCodec
    )
  , baseDirectory in reStart := new File(".")
  , reStart <<= reStart.dependsOn(fastOptJS in (js, Compile))
  )

lazy val js  = functionalstreamer.js
  .settings(
    scalaJSUseMainModuleInitializer := true
  , libraryDependencies ++= Seq(
      "org.scala-js"  %%% "scalajs-dom" % ScalaJSDom
    )
  , artifactPath in (Compile, fastOptJS) := jsPath / "application.js"
  )