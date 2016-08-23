package rogueLike.async

import core.Event
import rogueLike.actors.Enemy
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

  def updateEvent(id: String): Event = Event{
    case e @ Projectile(`id`) => (Seq(e),Seq(Projectile.update(id)))
    case e @ Enemy(`id`) => (Seq(e), Seq(Enemy.update(id)))
  }
}
