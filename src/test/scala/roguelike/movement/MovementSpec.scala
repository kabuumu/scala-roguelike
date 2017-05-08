package roguelike.movement

import org.scalatest.{Matchers, WordSpec}
import core.TestFixture
import core.entity.Entity
import core.system.GameState
import roguelike.movement.Direction.Up

/**
  * Created by rob on 09/03/17.
  */
class MovementSpec extends WordSpec with Matchers with TestFixture {
  "Movement event" should {
    "match on ID" in {
      val entity = Entity(id, Position(0,0))

      val f = Movement.moveEvent(Up)(entity)

      f.predicate(entity) shouldBe true
    }

    "move an entity in the supplied direction" in {
      val entity = Entity(id, Position(0,0), Facing(Up))

      val f = Movement.moveEvent(Up)(entity)

      val expectedEntity = Entity(id, Position(0, -1), Facing(Up))

      f.f(GameState(Seq.empty), entity)._1 shouldBe expectedEntity
    }
  }
}
