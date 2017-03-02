package rogueLike.combat

import core.util.EntityHelpers._
import core.{Entity, Event}
import rogueLike.async.Initiative
import rogueLike.movement.{Facing, Position}

/**
  * Created by rob on 26/07/16.
  */
case class Projectile(id: String) extends Entity

object Projectile {
  val HEALTH_DECREASE = 10

  def update(id: String) = {
    Event {
      case (_, e: Initiative) if e.id == id => (Seq(e.reset), Nil)
      case (s, e: Position) if e.id == id =>
        val facing = s.entities.findEntity[Facing](_.id == id).head

        (Seq(e.move(facing.direction)), Nil)
    }
  }
}
