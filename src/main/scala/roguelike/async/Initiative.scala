package roguelike.async

import core.entity.Entity
import core.event.CoreEvents._
import core.event.EventBuilder._
import core.event.{CoreEvents, Event, EventComponent}
import roguelike.async.Initiative._

/**
  * Created by rob on 12/04/17.
  */
case class Initiative(max: Int, current: Int = 0) extends EventComponent {
  override val entityEvents: Entity => Iterable[Event] = entity =>
    Seq(onIDMatch(entity) when notReady update decrement)
}

object Initiative {
  private type Update = Initiative => Initiative
  private type Predicate = Initiative => Boolean

  val reset: Update = c => c.copy(current = c.max)

  val decrement: Update = c => c.copy(current = c.current - 1)

  val isReady: Predicate = _.current == 0

  val notReady: Predicate = _.current > 0

  def increase(amount: Int): Update = c => c.copy(current = c.current + amount)
}
