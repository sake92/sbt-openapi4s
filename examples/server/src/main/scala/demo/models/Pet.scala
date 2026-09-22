package demo.models
import java.time.*
import java.util.UUID
import org.typelevel.jawn.ast.JValue
import ba.sake.tupson.*
import ba.sake.validson.Validator
case class Pet(id: Long, name: String, tag: Option[String]) derives JsonRW
object Pet { given Validator[Pet] = Validator.derived[Pet].min(_.id, 1L).max(_.id, 1L) }