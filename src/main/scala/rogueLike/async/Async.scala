package rogueLike.async

import core.Event
import rogueLike.actors.{Enemy, Player}
import rogueLike.combat.Projectile

/**
  * Created by rob on 27/07/16.
  */
object Async {
  val update: Event =
    Event.get {
      case Player(id) =>
        Event { case e: Initiative if e.id == id && e.current > 0 => (Seq(e), Seq(Event {
          case e: Initiative if e.current == 0 =>
            (Iterable(e), Seq(updateEvent(e.id)))
          case e: Initiative =>
            (Iterable(e.--), Nil)
        }))}
    }

  def updateEvent(id: String): Event = Event {
    case e@Projectile(`id`) => (Seq(e), Seq(Projectile.update(id)))
    case e@Enemy(`id`) => (Seq(e), Seq(Enemy.update(id)))
  }
}
