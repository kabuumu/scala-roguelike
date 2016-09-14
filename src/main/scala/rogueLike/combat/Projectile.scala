package rogueLike.combat

import core.{Entity, Event}
import rogueLike.actors.Enemy
import rogueLike.async.Initiative
import rogueLike.movement.Position

/**
  * Created by rob on 26/07/16.
  */
case class Projectile(id: String) extends Entity

object Projectile {
  def update(id: String) = {
    Event {
      case e: Initiative if e.id == id => (Seq(e.reset), Nil)
      case e: Position if e.id == id => (Seq(e.move()), Nil)
    }
  }

  def collide(a: String, b: String) = Event {
    case e: Enemy if e.id == b => (Seq(e),Seq(Event.delete(b)))
    case e if e.id == b => (Seq(e), Seq(Event.delete(a)))
  }
}
