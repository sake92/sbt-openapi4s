# sbt-openapi4s

sbt 2 plugin for [OpenAPI4s](https://github.com/sake92/openapi4s). It generates editable Scala 3 models, server code, and clients from an OpenAPI document or local JSON Schema input.

The plugin uses OpenAPI4s **0.9.0** by default and follows the configuration style of [mill-openapi4s](https://github.com/sake92/mill-openapi4s).

## Requirements

- sbt 2
- JDK 17 or newer
- Scala 3 for the generated sources

Add the plugin to `project/plugins.sbt`:

```scala
addSbtPlugin("ba.sake" % "sbt-openapi4s" % "<version>")
```

Enable and configure it in `build.sbt`:

```scala
import ba.sake.openapi4s.OpenApi4sPlugin
import ba.sake.openapi4s.OpenApi4sPlugin.autoImport.*

lazy val api = project
  .enablePlugins(OpenApi4sPlugin)
  .settings(
    scalaVersion := "3.7.1",
    libraryDependencies ++= Seq(
      "ba.sake" %% "tupson" % "0.30.0",
      "ba.sake" %% "validson" % "0.19.0",
      "ba.sake" %% "sharaf" % "0.9.3"
    ),
    openApi4sPackage := "com.example.api"
  )
```

Put the specification at `src/main/resources/openapi.json`, then run:

```shell
sbt openApi4sGenerate
```

By default, generated files are written below `src/main/scala/<package path>`. OpenAPI4s generation is additive: generated source files can contain hand-written changes and are not treated as disposable managed sources.

## Configuration

Only `openApi4sPackage` is required. All other settings have defaults.

| Setting | Default | Description |
|---|---|---|
| `openApi4sPackage` | required | Base package for generated sources |
| `openApi4sModels` | `"tupson"` | Model backend: `tupson` or `circe` |
| `openApi4sFramework` | `Some("sharaf")` | Server backend: `Some("sharaf")`, `Some("http4s")`, or `None` |
| `openApi4sClient` | `None` | Client backend: `Some("sttp")` or `None` |
| `openApi4sValidation` | `"none"` | Validation backend: `none`, `iron`, or `validson` |
| `openApi4sTags` | `Seq.empty` | Client tags to generate; empty means all tags |
| `openApi4sFile` | `Compile / resourceDirectory / "openapi.json"` | OpenAPI file, local JSON Schema file, or JSON Schema directory |
| `openApi4sTargetDir` | `Compile / scalaSource` | Base directory for generated sources |
| `openApi4sVersion` | `"0.9.0"` | OpenAPI4s CLI version |

For example, generate Circe models, http4s routes, and an sttp client for selected tags:

```scala
openApi4sPackage := "com.example.petstore"
openApi4sModels := "circe"
openApi4sFramework := Some("http4s")
openApi4sClient := Some("sttp")
openApi4sValidation := "iron"
openApi4sTags := Seq("Pet", "Store")
openApi4sFile := baseDirectory.value / "spec" / "petstore.yaml"
```

OpenAPI4s resolves the generator in an isolated sbt configuration, so it is not added to the application compile classpath. Add the libraries required by the generated code to the consuming project; see the [OpenAPI4s backend requirements](https://github.com/sake92/openapi4s#requirements).

## Development

```shell
sbt -batch compile
sbt -batch scripted
```

The scripted test loads the published plugin in a real sbt 2 build, generates a model with OpenAPI4s 0.9.0, changes the input schema, and verifies regeneration.
