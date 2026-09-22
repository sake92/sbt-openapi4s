package demo.controllers
import java.time.*
import java.util.UUID
import sttp.model.StatusCode
import ba.sake.querson.QueryStringRW
import ba.sake.validson.Validator
import ba.sake.sharaf.*, routing.*
import demo.models.*
class PetsController {
  def routes = Routes {
    case GET -> Path("pets") =>
      case class QP(limit: Option[Int]) derives QueryStringRW
      val qp = Request.current.queryParamsValidated[QP]
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Seq[Pet]")
    case POST -> Path("pets") =>
      val reqBody = Request.current.bodyJsonValidated[Pet]
      Response.withStatus(StatusCode.NotImplemented)
    case GET -> Path("pets", petId) =>
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Pet")
  }
}