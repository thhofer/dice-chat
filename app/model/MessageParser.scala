package model

import scala.util.parsing.combinator.RegexParsers

/**
  * Missing javadoc!
  */
class MessageParser(nick: String) extends RegexParsers {
  def message: Parser[Message] = command | plainText

  def command: Parser[Command] = roll | action

  def action: Parser[ActionMessage] = literal("/me ") ~> """.*""".r ^^ { action =>
    ActionMessage(nick, action)
  }

  def roll: Parser[RollMessage] = literal("/roll ") ~> """\d*""".r ~ diceSides ^^ {
    case n ~ sides => RollMessage(nick, n.toInt, sides)
  }

  def diceSides: Parser[Int] = literal("d") ~> """\d+""".r ^^ {
    _.toInt
  }

  def plainText: Parser[SimpleMessage] =
    """.*""".r ^^ { message =>
      SimpleMessage(nick, message)
    }
}
