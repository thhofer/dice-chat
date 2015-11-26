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
      case rollMessage: RollMessage => writesRollMessage(rollMessage)
      case actionMessage: ActionMessage => writesActionMessage(actionMessage)
      case _ => Json.toJson("an error occured")
    }
  }

  private def writesJoin(joinMessage: JoinMessage) = Json.obj(
    "type" -> "join",
    "nick" -> joinMessage.nick,
    "userCount" -> joinMessage.userCount
  )

  private def writesLeave(leaveMessage: LeaveMessage) = Json.obj(
    "type" -> "leave",
    "nick" -> leaveMessage.nick,
    "userCount" -> leaveMessage.userCount
  )

  private def writesSimple(simpleMessage: SimpleMessage) = Json.obj(
    "type" -> "text",
    "nick" -> simpleMessage.nick,
    "message" -> simpleMessage.message
  )

  private def writesRollMessage(rollMessage: RollMessage) = Json.obj(
    "type" -> "roll",
    "nick" -> rollMessage.nick,
    "count" -> rollMessage.count,
    "sides" -> rollMessage.sides,
    "result" -> Json.toJson(rollMessage.result)
  )

  private def writesActionMessage(actionMessage: ActionMessage) = Json.obj(
    "type" -> "action",
    "nick" -> actionMessage.nick,
    "action" -> actionMessage.action
  )
}
