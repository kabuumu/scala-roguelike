package roguelike.movement.lineofsight

import org.scalatest.{Matchers, WordSpec}
import roguelike.movement.lineofsight.ShadowCaster.isVisible

/**
  * Created by rob on 21/05/17.
  */
class ShadowCasterSpec extends WordSpec with Matchers {
  "isVisible" should {
    "return true where there are no blockers" in {
      val res = isVisible(0, 0, 0, 0, Set.empty)
      res shouldBe true
    }

    "return false on a horizontal line where it is blocked" in {
      val res = isVisible(0, 0, 2, 0, Set(1 -> 0))
      res shouldBe false
    }

    "return false on a negative horizontal line where is is blocked" in {
      val res = isVisible(0, 0, -2, 0, Set(-1 -> 0))
      res shouldBe false
    }

    "return true for a point at x+1, y+2 where its x neighbours are blocked" in {
      val res = isVisible(0, 0, 1, 2, Set(0 -> 2, 2 -> 2))
      res shouldBe true
    }
  }
}
