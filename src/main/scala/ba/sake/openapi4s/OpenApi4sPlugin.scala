package ba.sake.openapi4s

import sbt.*
import sbt.Keys.*

object OpenApi4sPlugin extends AutoPlugin:

  object autoImport:
    val openApi4sModels = settingKey[String]("Model backend: tupson or circe.")
    val openApi4sFramework = settingKey[Option[String]](
      "Optional server framework backend: sharaf or http4s."
    )
    val openApi4sClient = settingKey[Option[String]](
      "Optional client backend: sttp."
    )
    val openApi4sValidation = settingKey[String](
      "Validation backend: none, iron, or validson."
    )
    val openApi4sTags = settingKey[Seq[String]](
      "Tags to include in client generation; an empty sequence includes every tag."
    )
    val openApi4sPackage = settingKey[String]("Base package for generated sources.")
    val openApi4sFile = settingKey[File](
      "OpenAPI document, JSON Schema file, or JSON Schema directory."
    )
    val openApi4sTargetDir = settingKey[File]("Base directory for generated sources.")
    val openApi4sVersion = settingKey[String]("OpenAPI4s CLI version.")
    val openApi4sGenerate = taskKey[Unit]("Generate Scala sources with OpenAPI4s.")

  import autoImport.*

  private val OpenApi4s = config("openapi4s").hide
  private val MainClass = "ba.sake.openapi4s.cli.OpenApi4sMain"

  override def requires = plugins.JvmPlugin
  override def trigger = noTrigger

  override lazy val projectSettings: Seq[Setting[?]] =
    inConfig(OpenApi4s)(Defaults.configSettings) ++ Seq(
      ivyConfigurations += OpenApi4s,
      openApi4sModels := "tupson",
      openApi4sFramework := Some("sharaf"),
      openApi4sClient := None,
      openApi4sValidation := "none",
      openApi4sTags := Seq.empty,
      openApi4sPackage := "",
      openApi4sFile := (Compile / resourceDirectory).value / "openapi.json",
      openApi4sTargetDir := (Compile / scalaSource).value,
      openApi4sVersion := "0.9.0",
      libraryDependencies +=
        "ba.sake" % "openapi4s-cli_2.13" % openApi4sVersion.value % OpenApi4s.name,
      openApi4sGenerate := generate.value
    )

  private lazy val generate = Def.task {
    val log = streams.value.log
    val converter = fileConverter.value
    val input = openApi4sFile.value
    val target = openApi4sTargetDir.value
    val basePackage = openApi4sPackage.value.trim

    require(basePackage.nonEmpty, "openApi4sPackage must not be empty")
    require(input.exists(), s"OpenAPI4s input does not exist: ${input.getAbsolutePath}")
    IO.createDirectory(target)

    val args =
      Seq(
        "--models",
        openApi4sModels.value,
        "--url",
        input.toURI.toString,
        "--baseFolder",
        target.getAbsolutePath,
        "--basePackage",
        basePackage,
        "--validation",
        openApi4sValidation.value
      ) ++ optionArgs("--framework", openApi4sFramework.value) ++
        optionArgs("--client", openApi4sClient.value) ++
        tagsArgs(openApi4sTags.value)

    log.info(s"Generating OpenAPI4s sources in ${target.getAbsolutePath}")
    val result = (Compile / runner).value.run(
      MainClass,
      (OpenApi4s / fullClasspath).value.map(entry => converter.toPath(entry.data)),
      args,
      log
    )
    result.get
    log.info("OpenAPI4s source generation completed")
  }

  private def optionArgs(name: String, value: Option[String]): Seq[String] =
    value.filter(_.nonEmpty).toSeq.flatMap(v => Seq(name, v))

  private def tagsArgs(tags: Seq[String]): Seq[String] =
    if tags.isEmpty then Seq.empty else Seq("--tags", tags.mkString(","))
