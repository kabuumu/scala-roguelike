package refactor.roguelike.movement

import org.scalatest.{Matchers, WordSpec}
import refactor.core.TestFixture
import refactor.core.entity.Entity
import refactor.core.event.EventBuilder._
import refactor.core.system.GameState

/**
  * Created by rob on 03/04/17.
  */
class CollisionSpec extends WordSpec with Matchers with TestFixture {
  "collisionCheck" should {
    val position = Position(0,0)

    "trigger the provided collision event if it matches" in {
      val entity = Entity(id, position, BooleanComponent(false))
      val wall = Entity(position)

      val state = GameState(Seq(entity, wall))

      val collisionEvent = event update setToTrue
      val collisionCheck = Collision.onCollision(collisionEvent)(entity)

      val expectedEntity = Entity(id, position, BooleanComponent(true))

      state.update(Seq(collisionCheck)) should contain(expectedEntity)
    }

    "not trigger the provided collision event if there is no collision" in {
      val entity = Entity(id, position, BooleanComponent(false))

      val state = GameState(Seq(entity))

      val collisionEvent = event update setToTrue
      val collisionCheck = Collision.onCollision(collisionEvent)(entity)

      val expectedEntity = Entity(id, position, BooleanComponent(false))

      state.update(Seq(collisionCheck)) should contain(expectedEntity)
    }
  }
}