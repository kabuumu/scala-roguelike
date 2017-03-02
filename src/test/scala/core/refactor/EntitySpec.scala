package core.refactor

import core.refactor.EntityHelpers._
import org.scalatest.{Matchers, WordSpec}

/**
  * Created by rob on 26/02/17.
  */
class EntitySpec extends WordSpec with Matchers {

  val emptyEntity = Seq.empty[Component]
  val testComponent = new TestComponent {}

  class TestComponent extends Component

  case class BooleanComponent(boolean: Boolean) extends Component

  case class IntComponent(n: Int) extends Component

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
      val setToTrue = (c: BooleanComponent) => c.copy(boolean = true)

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
      val increment = (c: IntComponent) => c.copy(n = c.n + 1)

      val component = IntComponent(0)
      val entity = Entity(component)

      val expectedComponent = IntComponent(2)
      val res = entity ~> increment ~> increment

      res should contain(expectedComponent)
    }

    "work using the update command" in {
      val increment = (c: IntComponent) => c.copy(n = c.n + 1)

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
      val entity = Entity(IntComponent(0))

      val predicate = (c: IntComponent) => c.n == 0

      entity exists predicate shouldBe true
    }

    "return false when an entity does not contain a matching component" in {
      val entity = Entity(IntComponent(1))

      val predicate = (c: IntComponent) => c.n == 0

      entity exists predicate shouldBe false
    }

    "return false when an entity does not contain the matching component type" in {
      val predicate = (c: IntComponent) => c.n == 0

      emptyEntity exists predicate shouldBe false
    }

    "work with the ?> operator" in {
      val entity = Entity(IntComponent(0))

      val predicate = (c: IntComponent) => c.n == 0

      entity ?> predicate shouldBe true
    }

    "work with pattern matching" in {
      val validEntity = Entity(IntComponent(0))

      val predicate = (c: IntComponent) => c.n == 0

      val collection = Set(validEntity, emptyEntity)

      val res = collection.collectFirst {
        case e if e ?> predicate => e
      }

      res should contain(validEntity)
    }
  }
}
