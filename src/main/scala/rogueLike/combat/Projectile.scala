package rogueLike.combat

import java.util.UUID

import core.{Entity, Event}
import rogueLike.async.Initiative
import rogueLike.movement.Position

/**
  * Created by rob on 26/07/16.
  */
case class Projectile(id: String) extends Entity

object Projectile {
  def updateProjectile(id: String) = {
    Event {
      case e: Initiative if e.id == id => (Seq(e.reset), Nil)
      case e: Position if e.id == id => (Seq(e.move()), Nil)
    }
  }
}
