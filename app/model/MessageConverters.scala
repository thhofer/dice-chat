package model

import play.api.libs.json.{Json, JsValue, Writes}

/**
  * Missing javadoc!
  */
object MessageConverters {
  implicit val messageWrites = new Writes[Message] {
    override def writes(message: Message): JsValue = message match {
      case joinMessage: JoinMessage => writesJoin(joinMessage)
      case leaveMessage: LeaveMessage => writesLeave(leaveMessage)
      case simpleMessage: SimpleMessage => writesSimple(simpleMessage)
      case _ => Json.toJson("an error occured")
    }
  }

  private def writesJoin(joinMessage: JoinMessage): JsValue = Json.obj(
    "type" -> "join",
    "nick" -> joinMessage.nick,
    "userCount" -> joinMessage.userCount
  )

  private def writesLeave(leaveMessage: LeaveMessage): JsValue = Json.obj(
    "type" -> "leave",
    "nick" -> leaveMessage.nick,
    "userCount" -> leaveMessage.userCount
  )

  private def writesSimple(simpleMessage: SimpleMessage): JsValue = Json.obj(
    "type" -> "text",
    "message" -> simpleMessage.message
  )
}
