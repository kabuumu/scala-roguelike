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
      case (_, Player(pid)) =>
        Event.get { case (_, e: Initiative) if e.id == pid && e.current > 0 =>
          Event {
            case (_, e: Initiative) if e.current == 0 =>
              (Iterable(e), Seq(updateEvent(e.id)))
            case (_, e: Initiative) =>
              (Iterable(e.--), Nil)
          }
        }
    }

  def updateEvent(id: String): Event = Event {
    case (_, e@Projectile(`id`)) => (Seq(e), Seq(Projectile.update(id)))
    case (_, e@Enemy(`id`)) => (Seq(e), Seq(Enemy.update(id)))
  }
}
