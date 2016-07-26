package core

import combat.Projectile
import core.Initiative._

/**
  * Created by rob on 29/06/16.
  */
trait Entity {
  val id: String;

  val initiative: Initiative = defaultInitiative
  def initiative(f: Initiative => Initiative): Entity
}

object Entity {
  val update: Event = Event {
    case e if e.initiative.current == 0 =>
      (Iterable(e.initiative(_.reset)), getUpdateEvent(e))
    case e =>
      (Iterable(e.initiative(_.--)), Nil)
  }

  def getUpdateEvent(e: Entity): Option[Event] = e match{
    case e:Projectile => Some(Projectile.updateProjectile(e.id))
    case _ => None
  }
}