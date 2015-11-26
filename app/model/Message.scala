package model

import java.security.SecureRandom

/**
  * Missing javadoc!
  */
sealed abstract class Message(nick: String)

case class JoinMessage(nick: String, userCount: Int) extends Message(nick)

case class LeaveMessage(nick: String, userCount: Int) extends Message(nick)

case class SimpleMessage(nick: String, message: String) extends Message(nick)

sealed abstract class Command(nick: String) extends Message(nick)

case class RollMessage(nick: String, count: Int, sides: Int) extends Command(nick) {
  val result = RollMessage.roll(count, sides)
}

object RollMessage {
  val random = SecureRandom.getInstance("SHA1PRNG")

  def roll(count: Int, sides: Int): Seq[Int] = {
    for (
      n <- 1 to count
    ) yield random.nextInt(sides-1) + 1
  }
}

case class ActionMessage(nick: String, action: String) extends Command(nick)
