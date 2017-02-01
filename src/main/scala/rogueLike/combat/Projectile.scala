package rogueLike.combat

import core.{Entity, Event}
import rogueLike.async.Initiative
import rogueLike.health.Health
import rogueLike.movement.Position

/**
  * Created by rob on 26/07/16.
  */
case class Projectile(id: String) extends Entity

object Projectile {
  val HEALTH_DECREASE = 10

  def update(id: String) = {
    Event {
      case (_, e: Initiative) if e.id == id => (Seq(e.reset), Nil)
      case (_, e: Position) if e.id == id => (Seq(e.move()), Nil)
    }
  }
}
