package combat

import combat.Defender._

/**
  * Created by rob on 22/06/16.
  */
trait Attacker {
  val attackDamage: Int
}

object Attacker {
  def getAttack(attacker: Attacker): (Defender) => Defender = getHit(attacker.attackDamage)
}
