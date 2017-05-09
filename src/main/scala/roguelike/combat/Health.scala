package roguelike.combat

import core.entity.{Component, Entity}
import core.event.CoreEvents._
import core.event.Event.Triggered
import core.event.EventBuilder._
import core.event.{DeleteEntity, Event}

/**
  * Created by rob on 08/05/17.
  */
case class Health(current: Int, max: Int) extends Component

object Health {
  def apply(max: Int): Health = Health(max, max)

  def decreaseHealthEvent(amount: Int): Triggered[Event] = e =>
    onIDMatch(e) update decreaseHealth(amount) trigger deathCheck

  def decreaseHealth(amount: Int): Health => Health = h => {
    val decreasedHealth = (h.current - amount).max(0)
    h.copy(current = decreasedHealth)
  }

  def deathCheck: Entity => Option[Event] = e => for {
    health <- e[Health]
    if health.current == 0
  } yield DeleteEntity(e)
}