package rogueLike.async

import core.{Entity, Event}
import rogueLike.combat.Projectile

/**
  * Created by rob on 27/07/16.
  */
object Async {
  val update: Event = Event {
    case e: HasInitiative if e.initiative.current == 0 =>
      (Iterable(e), getUpdateEvent(e))
    case e: HasInitiative =>
      (Iterable(e.initiative(_.--)), Nil)
  }

  def getUpdateEvent(e: Entity): Option[Event] = e match {
    case e: Projectile => Some(Projectile.updateProjectile(e.id))
    case _ => None
  }
}
