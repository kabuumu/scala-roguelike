package core.refactor.system

import core.refactor.TestFixture
import core.refactor.entity.Entity
import core.refactor.event.EventBuilder._
import org.scalatest.{Matchers, WordSpec}

/**
  * Created by rob on 03/03/17.
  */
class GameStateSpec extends WordSpec with Matchers with TestFixture {
  "update" should {
    "apply all events to each matching entity" in {
      val entity = Entity(BooleanComponent(false))
      val state = GameState(Seq(entity))

      val event = buildEvent when booleanIsFalse update setToTrue

      val expectedEntity = Entity(BooleanComponent(true))

      state.update(Seq(event)) should contain(expectedEntity)
    }

    "not apply an event when there is not a matching entity" in {
      val entity = Entity(BooleanComponent(false))
      val state = GameState(Seq(entity))

      val event = buildEvent when intIsZero update increment

      val expectedEntity = Entity(IntComponent(1))

      state.update(Seq(event)) shouldNot contain(expectedEntity)
    }

    "apply an event to multiple entities" in {
      val entity1 = Entity(StringComponent("1"), IntComponent(0))
      val entity2 = Entity(StringComponent("2"), IntComponent(1))
      val state = GameState(Seq(entity1, entity2))

      val event = buildEvent when hasIntComponent update increment

      val expected1 = Entity(StringComponent("1"), IntComponent(1))
      val expected2 = Entity(StringComponent("2"), IntComponent(2))

      val res = state.update(Seq(event))

      res should contain(expected1)
      res should contain(expected2)
    }

    "trigger more events when events are run, and run those" in {
      val entity = Entity(IntComponent(0))
      val state = GameState(Seq(entity))

      val triggeredEvent = buildEvent update increment
      val event = buildEvent update increment trigger triggeredEvent

      val expectedEntity = Entity(IntComponent(2))

      state.update(Seq(event)) should contain(expectedEntity)
    }

    "update an entity when an event has two matching predicates" in {
      val entity = Entity(BooleanComponent(false), IntComponent(0))
      val state = GameState(Seq(entity))

      val event = buildEvent when intIsZero when booleanIsFalse update setToTrue

      val expectedEntity = Entity(BooleanComponent(true), IntComponent(0))

      state.update(Seq(event)) should contain(expectedEntity)
    }

    "apply two updates to an entity within the same event" in {
      val entity = Entity(BooleanComponent(false), IntComponent(0))
      val state = GameState(Seq(entity))

      val event = buildEvent update increment update setToTrue

      val expectedEntity = Entity(BooleanComponent(true), IntComponent(1))

      state.update(Seq(event)) should contain(expectedEntity)
    }

    "handle gracefully when an entity is updated with a component it does not contain" in {
      val state = GameState(Seq(emptyEntity))

      val event = buildEvent update setToTrue

      state.update(Seq(event)) should contain(emptyEntity)
    }
  }
}
