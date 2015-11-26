package actors

import akka.actor.Actor
import akka.event.Logging
import model.MessageConverters.messageWrites
import model.{JoinMessage, LeaveMessage, MessageParser}
import play.api.libs.iteratee.{Concurrent, Enumerator, Iteratee}
import play.api.libs.json.{JsDefined, JsValue, Json}

import scala.concurrent.ExecutionContext

/**
  * Missing javadoc!
  */
case class Join(nick: String)

case class Leave(nick: String)

case class Broadcast(nick: String, message: String)

class ChatRoom extends Actor {
  implicit val ec: ExecutionContext = context.dispatcher
  val log = Logging(context.system, this)
  val (enumerator, channel) = Concurrent.broadcast[JsValue]
  var users = Set[String]()

  def receive = {
    case Join(nick) =>
      log.info(s"user $nick joined")
      if (!users.contains(nick)) {
        val iteratee: Iteratee[JsValue, _] = Iteratee.foreach[JsValue] { message =>
          self ! Broadcast(nick, (message \ "text").as[String])
        }.map { _ =>
          self ! Leave(nick)
        }
        users += nick
        log.info(s"pushing $nick join's to channel")
        val json: JsValue = Json.toJson(JoinMessage(nick, users.size))
        log.info(s"message jsonified ${Json.prettyPrint(json)}")
        channel.push(json)
        log.info(s"replying to sender of Join($nick)")
        sender !(iteratee, enumerator)
      } else {
        val enumerator = Enumerator[JsValue](Json.obj(
          "type" -> "error",
          "message" -> s"nick $nick is already in use."
        ))
        val iteratee = Iteratee.ignore[JsValue]
        sender !(iteratee, enumerator)
      }
    case Leave(nick) =>
      log.info(s"user $nick left")
      users -= nick
      channel.push(Json.toJson(LeaveMessage(nick, users.size)))
    case Broadcast(nick: String, msg: String) =>
      log.info(s"user $nick sent $msg")
      val parser: MessageParser = new MessageParser(nick)
      parser.parse(parser.message, msg).map {
        m => channel.push(Json.toJson(m))
      }
  }

}
