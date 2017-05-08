package roguelike.movement

import org.scalatest.{Matchers, WordSpec}
import core.TestFixture
import core.entity.Entity

/**
  * Created by rob on 03/04/17.
  */
class CollisionSpec extends WordSpec with Matchers with TestFixture {
  "onCollision" should {
    val position = Position(0,0)

    "match the predicate on an entity with the same position that does not share an ID" in {
      val entity = Entity(id, position)
      val wall = Entity(position)

      Collision.onCollision(entity).predicate(wall) shouldBe true
    }

    "not match when the position is different" in {
      val entity = Entity(id, position)
      val wall = Entity(Position(0, 1))

      Collision.onCollision(entity).predicate(wall) shouldBe false
    }

    "not match on the same entityID" in {
      val entity1 = Entity(id, position)
      val entity2 = Entity(id, position)

      Collision.onCollision(entity1).predicate(entity2) shouldBe false
    }
  }
}