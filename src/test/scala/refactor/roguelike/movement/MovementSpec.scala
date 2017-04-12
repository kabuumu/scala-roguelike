package refactor.roguelike.movement

import org.scalatest.{Matchers, WordSpec}
import refactor.core.TestFixture
import refactor.core.entity.Entity
import refactor.core.event.EventBuilder._
import refactor.core.system.GameState

/**
  * Created by rob on 09/03/17.
  */
class MovementSpec extends WordSpec with Matchers with TestFixture {
  "Movement event" should {
    "move an entity when the ID matches" in {
      val entity = Entity(id, Position(0, 0))
      val state = GameState(Seq(entity))

      val event = Movement.moveEvent(id, Direction.Down)

      val expectedEntity = Entity(id, Position(0, 1))

      state.update(Seq(event)) should contain(expectedEntity)
    }
  }
}
