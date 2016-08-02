package core

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by rob on 27/07/16.
  */
class EntitySpec extends FlatSpec with Matchers{
  "Entity" should "have a string value called id" in {
    val testObject: Entity = new Entity{val id = "test"}
    testObject.id shouldBe "test"
  }
}
