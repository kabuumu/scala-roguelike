package rogueLike.health

import core.util.EventHelpers._
import core.{Entity, Event}

/**
  * Created by rob on 12/01/17.
  */
case class Health(id: String, current: Int, max: Int) extends Entity

object Health {
  def deathEvent(id: String) = Event.delete(id)

  def resetHealthEvent(id: String) = Event {
    case (_, e: Health) if e.id == id => EventOutput(e.copy(current = e.max))
  }

  def decreaseHealthEvent(id: String, amount: Int) = Event {
    case (_, e: Health) if e.id == id =>
      e.current - amount match {
        case health if health <= 0 => EventOutput(e.copy(current = 0)).withEvents(deathEvent(id))
        case newHealth => EventOutput(e.copy(current = newHealth))
      }
  }
}
