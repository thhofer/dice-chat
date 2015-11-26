package model

/**
  * Missing javadoc!
  */
sealed abstract class Message

case class JoinMessage(nick: String, userCount: Int) extends Message

case class LeaveMessage(nick: String, userCount: Int) extends Message

case class SimpleMessage(message: String) extends Message

sealed abstract class Command extends Message

case class Roll(count: Int, sides: Int) extends Command

case class Action(action: String) extends Command
