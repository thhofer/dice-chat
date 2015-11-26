package model

import org.specs2.mutable.Specification
import model.MessageParser._

/**
  * Missing javadoc!
  */
class MessageConvertersTests extends Specification {
  "The 'Hello world' string" should {
    val parseResult = parse(message, "Hello world")
    "be parsed successfully" in {
      parseResult.isEmpty mustEqual false
    }
    "as a SimpleMessage" in {
      parseResult.get must haveClass[SimpleMessage]
    }
    "with the adequate message" in {
      parseResult.get mustEqual SimpleMessage("Hello world")
    }
  }

  "The '/me sighs' string" should {
    val parseResult = parse(MessageParser.action, "/me sighs")
    "be parsed successfully [with action]" in {
      parseResult.isEmpty mustEqual false
    }
    "as an Action command" in {
      parseResult.get must haveClass[ActionMessage]
    }
    "with the adequate values" in {
      parseResult.get mustEqual ActionMessage("sighs")
    }
  }

  "The '/roll 3d6' string" should {
    val parseResult = parse(message, "/roll 3d6")
    "be parsed successfully" in {
      parseResult.isEmpty mustEqual false
    }
    "as a Roll command" in {
      parseResult.get must haveClass[RollMessage]
    }
    "with the adequate values" in {
      parseResult.get mustEqual RollMessage(3, 6)
    }
  }

  "The '/roll 3d6' string" should {
    val parseResult = parse(roll, "/roll 3d6")
    "be parsed successfully [with roll]" in {
      parseResult.isEmpty mustEqual false
    }
    "as a Roll command" in {
      parseResult.get must haveClass[RollMessage]
    }
    "with the adequate values" in {
      parseResult.get mustEqual RollMessage(3, 6)
    }
  }

  "The 'd6' string" should {
    val parseResult = parse(diceSides, "d6")
    "be parsed successfully [with diceSides]" in {
      parseResult.isEmpty mustEqual false
    }
    "with the proper result (6)" in {
      parseResult.get mustEqual 6
    }
  }
}
