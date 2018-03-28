name := "ExternalCall"
version := "0.1"
scalaVersion := "2.12.4"
organization in ThisBuild := "com.knoldus"
version in ThisBuild := "1.0-SNAPSHOT"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test


lazy val `user-lagom` = (project in file("."))
  .aggregate(`user-lagom-api`, `user-lagom-impl`)

lazy val `user-lagom-api` = (project in file("user-lagom-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `user-lagom-impl` = (project in file("user-lagom-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest,
      "com.knoldus" %% "emp-lagom-api" % "1.0-SNAPSHOT"
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`user-lagom-api`)
lazy val externalCall = lagomExternalScaladslProject("external", "com.knoldus" %% "emp-lagom-impl" % "1.0-SNAPSHOT")

lagomServiceGatewayPort in ThisBuild := 9010
lagomServiceLocatorPort in ThisBuild := 10000
