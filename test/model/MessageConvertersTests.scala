package model

import org.specs2.mutable.Specification

/**
  * Missing javadoc!
  */
class MessageConvertersTests extends Specification {
  val testUser = "TestUser"
  val messageParser = new MessageParser(testUser)

  "The 'Hello world' string" should {
    val parseResult = messageParser.parse(messageParser.message, "Hello world")
    "be parsed successfully" in {
      parseResult.isEmpty mustEqual false
    }
    "as a SimpleMessage" in {
      parseResult.get must haveClass[SimpleMessage]
    }
    "with the adequate message" in {
      parseResult.get mustEqual SimpleMessage("TestUser", "Hello world")
    }
  }

  "The '/me sighs' string" should {
    val parseResult = messageParser.parse(messageParser.action, "/me sighs")
    "be parsed successfully [with action]" in {
      parseResult.isEmpty mustEqual false
    }
    "as an Action command" in {
      parseResult.get must haveClass[ActionMessage]
    }
    "with the adequate values" in {
      parseResult.get mustEqual ActionMessage("TestUser", "sighs")
    }
  }

  "The '/roll 3d6' string" should {
    val parseResult = messageParser.parse(messageParser.message, "/roll 3d6")
    "be parsed successfully" in {
      parseResult.isEmpty mustEqual false
    }
    "as a Roll command" in {
      parseResult.get must haveClass[RollMessage]
    }
    "with the adequate values" in {
      parseResult.get mustEqual RollMessage("TestUser", 3, 6)
    }
  }

  "The '/roll 3d6' string" should {
    val parseResult = messageParser.parse(messageParser.roll, "/roll 3d6")
    "be parsed successfully [with roll]" in {
      parseResult.isEmpty mustEqual false
    }
    "as a RollMessage" in {
      parseResult.get must haveClass[RollMessage]
    }
    val rollMessage = parseResult.get
    "from user 'TestUser'" in {
      rollMessage.nick mustEqual testUser
    }
    "with 3 dice" in {
      rollMessage.count mustEqual 3
    }
    "of 6 sides" in {
      rollMessage.sides mustEqual 6
    }
  }

  "The 'd6' string" should {
    val parseResult = messageParser.parse(messageParser.diceSides, "d6")
    "be parsed successfully [with diceSides]" in {
      parseResult.isEmpty mustEqual false
    }
    "with the proper result (6)" in {
      parseResult.get mustEqual 6
    }
  }
}
