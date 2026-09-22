ThisBuild / organization := "ba.sake"

name := "sbt-openapi4s"

enablePlugins(SbtPlugin)

description := "sbt 2 plugin for OpenAPI4s"
pluginCrossBuild / sbtVersion := "2.0.0"
scriptedSbt := "2.0.6"
scriptedLaunchOpts := Seq(
  "-Xmx1G",
  s"-Dplugin.version=${version.value}"
)

homepage := Some(url("https://github.com/sake92/sbt-openapi4s"))
licenses := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))
scmInfo := Some(
  ScmInfo(
    url("https://github.com/sake92/sbt-openapi4s"),
    "scm:git:git@github.com:sake92/sbt-openapi4s.git"
  )
)
developers := List(
  Developer("sake92", "Sakib Hadziavdic", "", url("https://github.com/sake92"))
)
versionScheme := Some("semver-spec")
