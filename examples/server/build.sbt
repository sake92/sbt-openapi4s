
import ba.sake.openapi4s.OpenApi4sPlugin
//import ba.sake.openapi4s.OpenApi4sPlugin.autoImport.*

enablePlugins(OpenApi4sPlugin)
openApi4sPackage := "demo"
openApi4sFramework := Some("sharaf")

scalaVersion := "3.8.4"
libraryDependencies ++= Seq(
    "ba.sake" %% "sharaf-undertow" % "0.18.0"
)

scalacOptions ++= Seq("-Yretain-trees")
