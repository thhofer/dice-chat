package model

import scala.util.parsing.combinator.RegexParsers

/**
  * Missing javadoc!
  */
object MessageParser extends RegexParsers {
  def message: Parser[Message] = command | plainText

  def command: Parser[Command] = roll | action

  def action: Parser[Action] = literal("/me ") ~> """.*""".r ^^ {
    Action
  }

  def roll: Parser[Roll] = literal("/roll ") ~> """\d*""".r ~ diceSides ^^ {
    case n ~ sides => Roll(n.toInt, sides)
  }

  def diceSides: Parser[Int] = literal("d") ~> """\d+""".r ^^ {
    _.toInt
  }

  def plainText: Parser[SimpleMessage] =
    """.*""".r ^^ {
      SimpleMessage
    }
}
