package rogueLike.async

import core.{Entity, Event}
import rogueLike.combat.Projectile

/**
  * Created by rob on 27/07/16.
  */
object Async {
  val update: Event = Event {
    case e: Initiative if e.current == 0 =>
      (Iterable(e), Seq(updateEvent(e.id)))
    case e: Initiative =>
      (Iterable(e.--), Nil)
  }

  def updateEvent(id: String): Event = {
    Event {
      case e: Projectile => (Seq(e), Seq(Projectile.updateProjectile(e.id)))
    }
  }
}
