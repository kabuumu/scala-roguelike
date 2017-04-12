package refactor.event

import org.scalatest.{Matchers, WordSpec}
import refactor.core.TestFixture
import refactor.core.entity.{Entity, ID}
import refactor.core.event.EventBuilder._
import refactor.core.system.GameState

/**
  * Created by rob on 24/03/17.
  */
class EventBuilderSpec extends WordSpec with Matchers with TestFixture {
  "Component query eq" should {
    "match when a provided component matches" in {
      val component = BooleanComponent(false)
      val entity = Entity(component)

      val testEvent = event.when[BooleanComponent] eq component update setToTrue

      val expectedEntity = Entity(BooleanComponent(true))

      GameState(Seq(entity)).update(Seq(testEvent)) should contain(expectedEntity)
    }

    "not match when a component does not equal" in {
      val component = BooleanComponent(false)
      val entity = Entity(component)

      val testEvent = event.when[BooleanComponent] eq BooleanComponent(true) update setToTrue

      val updatedEntity = entity ~> setToTrue

      GameState(Seq(entity)).update(Seq(testEvent)) shouldNot contain(updatedEntity)
    }
  }

  "Component query matches" should {
    "match when a provided entity contains the same component, but is not the same component" in {
      val entity = Entity(new ID(), BooleanComponent(false))

      val matchingEntity = Entity(new ID(), BooleanComponent(false))

      val testEvent = event.when[BooleanComponent] matches matchingEntity update setToTrue

      val updatedEntity = entity ~> setToTrue

      GameState(Seq(entity)).update(Seq(testEvent)) should contain(updatedEntity)
    }

    "not match when a provided entity does not contain the same component" in {
      val entity = Entity(BooleanComponent(false))

      val nonMatchingEntity = Entity(BooleanComponent(true))

      val testEvent = event.when[BooleanComponent] matches nonMatchingEntity update setToTrue

      val updatedEntity = entity ~> setToTrue

      GameState(Seq(entity)).update(Seq(testEvent)) shouldNot contain(updatedEntity)
    }

    "not match if the entity is identical" in {
      val entity = Entity(new ID(), BooleanComponent(false))

      val testEvent = event.when[BooleanComponent] matches entity update setToTrue

      val updatedEntity = entity ~> setToTrue

      GameState(Seq(entity)).update(Seq(testEvent)) shouldNot contain(updatedEntity)

    }

    "not match when a provided entity does not contain the same component type" in {
      val entity = Entity(BooleanComponent(false))

      val nonMatchingEntity = emptyEntity

      val testEvent = event.when[BooleanComponent] matches nonMatchingEntity update setToTrue

      val updatedEntity = entity ~> setToTrue

      GameState(Seq(entity)).update(Seq(testEvent)) shouldNot contain(updatedEntity)
    }
  }
}
