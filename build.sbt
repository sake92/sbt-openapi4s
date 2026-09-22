ThisBuild / organization := "ba.sake"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / homepage := Some(url("https://github.com/sake92/sbt-openapi4s"))
ThisBuild / licenses := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))
ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/sake92/sbt-openapi4s"),
    "scm:git:git@github.com:sake92/sbt-openapi4s.git"
  )
)
ThisBuild / developers := List(
  Developer("sake92", "Sakib Hadziavdic", "", url("https://github.com/sake92"))
)
ThisBuild / versionScheme := Some("semver-spec")

lazy val root = project
  .in(file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-openapi4s",
    description := "sbt 2 plugin for OpenAPI4s",
    pluginCrossBuild / sbtVersion := "2.0.0",
    scriptedSbt := "2.0.6",
    scriptedLaunchOpts := Seq(
      "-Xmx1G",
      s"-Dplugin.version=${version.value}"
    )
  )
