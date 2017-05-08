package core.event

import org.scalatest.{Matchers, WordSpec}
import core.TestFixture
import core.entity.{Entity, ID}
import core.system.GameState

/**
  * Created by rob on 19/04/17.
  */
class CoreEventsSpec extends WordSpec with Matchers with TestFixture {
  "resetEntity" should {
    "reset the entity to the original one defined in the event" in {
      val id = new ID

      val originalEntity = Entity(id, BooleanComponent(false))
      val event = CoreEvents.resetEntity(originalEntity)

      val updatedEntity = Entity(id, BooleanComponent(true))

      event.f(GameState(Seq.empty), updatedEntity)._1 shouldBe originalEntity
    }
  }
}
