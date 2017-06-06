package roguelike.movement

import org.scalatest.{Matchers, WordSpec}
import core.TestFixture
import core.entity.Entity
import roguelike.movement.Direction._
import roguelike.movement.Position._

/**
  * Created by rob on 09/03/17.
  */
class PositionSpec extends WordSpec with Matchers with TestFixture {
  val testX = 0
  val testY = 1
  val testPosition = Position(testX, testY)
  val testEntity = Entity(testPosition)

  "when in an entity" should {
    "update x" in {
      val expectedPosition = Position(testX + 1, testY)

      testEntity ~> move(Right) should contain(expectedPosition)
    }

    "update y" in {
      val expectedPosition = Position(testX, testY + 1)

      testEntity ~> move(Down) should contain(expectedPosition)
    }
  }
}
