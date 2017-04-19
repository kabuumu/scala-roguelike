package refactor.roguelike.movement

import org.scalatest.{Matchers, WordSpec}
import refactor.core.TestFixture
import refactor.core.entity.Entity
import refactor.core.system.GameState

/**
  * Created by rob on 09/03/17.
  */
class MovementSpec extends WordSpec with Matchers with TestFixture {
  "Movement event" should {
    "match on ID" in {
      val entity = Entity(id, Position(0,0))

      val f = Movement.moveEvent(Direction.Up)(entity)

      f.predicate(entity) shouldBe true
    }

    "move an entity in the supplied direction" in {
      val entity = Entity(id, Position(0,0))

      val f = Movement.moveEvent(Direction.Up)(entity)

      val expectedEntity = Entity(id, Position(0, -1))

      f.f(GameState(Seq.empty), entity)._1 shouldBe expectedEntity
    }
  }
}
