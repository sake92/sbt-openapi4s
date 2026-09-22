import ba.sake.openapi4s.OpenApi4sPlugin
import ba.sake.openapi4s.OpenApi4sPlugin.autoImport.*

enablePlugins(OpenApi4sPlugin)

openApi4sPackage := "demo"
openApi4sFramework := None

@transient
lazy val checkInitial = taskKey[Unit]("Check the initially generated model")

@transient
lazy val checkUpdated = taskKey[Unit]("Check the regenerated model")

checkInitial := {
  val generated = (Compile / scalaSource).value / "demo" / "models" / "Address.scala"
  checkAddress(generated, expectedProperty = None)
}

checkUpdated := {
  val generated = (Compile / scalaSource).value / "demo" / "models" / "Address.scala"
  checkAddress(generated, expectedProperty = Some("country: Option[String]"))
}

def checkAddress(generated: File, expectedProperty: Option[String]): Unit = {
  assert(generated.isFile, s"Missing generated model: $generated")
  val source = IO.read(generated)
  expectedProperty.foreach(property => assert(source.contains(property), source))
}
