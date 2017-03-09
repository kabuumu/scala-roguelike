package refactor.core.entity

import org.scalatest.{Matchers, WordSpec}
import refactor.core.TestFixture

/**
  * Created by rob on 26/02/17.
  */
class EntitySpec extends WordSpec with Matchers with TestFixture{
  "+" should {
    "add a component to the Entity" in {
      emptyEntity + testComponent should contain(testComponent)
    }
  }

  "get[T]" should {
    "return a component of that type if it exists" in {
      Entity(testComponent).get[TestComponent] should contain(testComponent)
    }

    "return None if the entity does not contain that type of component" in {
      emptyEntity.get[TestComponent] shouldNot contain(testComponent)
    }
  }

  "update[T](f)" should {
    "apply the function to the component if it exists within the entity" in {

      val component = BooleanComponent(false)
      val entity = Entity(component)

      val expectedComponent = BooleanComponent(true)

      entity ~> setToTrue should contain(expectedComponent)
    }

    "return the original entity if the component does not exist" in {
      val invalidOperation = (c: TestComponent) => c

      emptyEntity ~> invalidOperation shouldBe emptyEntity
    }

    "chain commands together and operate them all" in {
      val component = IntComponent(0)
      val entity = Entity(component)

      val expectedComponent = IntComponent(2)
      val res = entity ~> increment ~> increment

      res should contain(expectedComponent)
    }

    "work using the update command" in {
      val component = IntComponent(0)
      val entity = Entity(component)

      val expectedComponent = IntComponent(2)
      val res = entity
        .update(increment)
        .update(increment)

      res should contain(expectedComponent)
    }
  }

  "exists" should {
    "return true when an entity contains a matching component" in {
      Entity(IntComponent(0)) exists intIsZero shouldBe true
    }

    "return false when an entity does not contain a matching component" in {
      Entity(IntComponent(1)) exists intIsZero shouldBe false
    }

    "return false when an entity does not contain the matching component type" in {
      emptyEntity exists intIsZero shouldBe false
    }

    "work with the ?> operator" in {
      Entity(IntComponent(0)) ?> intIsZero shouldBe true
    }

    "work with pattern matching" in {
      val validEntity = Entity(IntComponent(0))
      val collection = Set(validEntity, emptyEntity)

      val res = collection.collectFirst {
        case e if e ?> intIsZero => e
      }

      res should contain(validEntity)
    }
  }
}
