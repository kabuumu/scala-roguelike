package refactor.roguelike.movement

import org.scalatest.{Matchers, WordSpec}
import refactor.core.TestFixture
import refactor.core.entity.Entity
import refactor.roguelike.movement.Direction._
import refactor.roguelike.movement.Position._

/**
  * Created by rob on 09/03/17.
  */
class PositionSpec extends WordSpec with Matchers with TestFixture {
  val testX = 0
  val testY = 1
  val testPosition = Position(testX, testY)
  val testEntity = Entity(testPosition)

  "when in an entity" should {
    "return x as a value" in {
      testEntity get x should contain(testX)
    }

    "update x" in {
      val expectedPosition = Position(testX + 1, testY)

      testEntity ~> move(Right) should contain(expectedPosition)
    }

    "return y as a value" in {
      testEntity get y should contain(testY)
    }

    "update y" in {
      val expectedPosition = Position(testX, testY + 1)

      testEntity ~> move(Down) should contain(expectedPosition)
    }
  }

  "move" should {
    "create a collision check event which will apply a provided event if it matches" in {

    }
  }
}
