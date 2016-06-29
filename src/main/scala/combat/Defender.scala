package combat

import scalaz.Lens

/**
  * Created by rob on 22/06/16.
  */
trait Defender {
  def health(f: Int => Int): Defender

  val defence: Int
}

object Defender {
  def getHit(attack: Int)(target: Defender) = target.health(_ - applyDefence(attack)(target))

  def applyDefence[T](attack: Int)(target: Defender) = Math.max(attack - target.defence, 0)
}
