package controllers

import javax.inject.Inject

import actors.{ChatRoom, Join}
import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import play.api.Play.current
import play.api.data.Form
import play.api.data.Forms.{nonEmptyText, single}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.concurrent.Akka
import play.api.libs.iteratee.{Enumerator, Iteratee}
import play.api.libs.json.JsValue
import play.api.mvc.{Action, Controller, WebSocket}

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

/**
  * Missing javadoc!
  */
object Chat {
  val userForm = Form(
    single(
      "nick" -> nonEmptyText(minLength = 5, maxLength = 20)
    )
  )

  val room = Akka.system.actorOf(Props[ChatRoom])
}

class Chat @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {
  implicit val executionContext = ExecutionContext.global

  implicit val timeout = Timeout(1 seconds)

  def showRoom = Action { implicit request =>
    Chat.userForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.index(formWithErrors)), { case (nick) => Ok(views.html.chat.showRoom(nick)) }
    )
  }

  def chatSocket(nick: String) = WebSocket.tryAccept[JsValue] { request =>
    try {
      val channelsFuture = Chat.room ? Join(nick)
      channelsFuture.mapTo[(Iteratee[JsValue, _], Enumerator[JsValue])].map(Right(_))
    } catch {
      case e: Exception =>
        Future(Left(InternalServerError))
    }
  }
}