package roguelike.combat

import core.entity.Entity
import core.event.CoreEvents._
import core.event.Event.Triggered
import core.event.EventBuilder._
import core.event.{DeleteEntity, Event, EventComponent}
import roguelike.combat.Health._

/**
  * Created by rob on 08/05/17.
  */
case class Health(current: Int, max: Int) extends EventComponent {
  val entityEvents: Entity => Iterable[Event] = entity =>
    Seq(onIDMatch(entity) when noHealth trigger DeleteEntity)
}

object Health {
  def apply(max: Int): Health = Health(max, max)

  def decreaseHealthEvent(amount: Int): Triggered[Event] = e =>
    onIDMatch(e) update decreaseHealth(amount)

  def decreaseHealth(amount: Int): Health => Health = h => {
    val decreasedHealth = (h.current - amount).max(0)
    h.copy(current = decreasedHealth)
  }

  def noHealth: Health => Boolean = _.current == 0
}
