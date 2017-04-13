package refactor.roguelike.async

import refactor.core.entity.Component

/**
  * Created by rob on 12/04/17.
  */
case class Initiative(current: Int, max: Int) extends Component

object Initiative {
  private type Update = Initiative => Initiative
  private type Predicate = Initiative => Boolean

  val reset: Update = c => c.copy(current = c.max)

  val decrement: Update = c => c.copy(current = c.current - 1)

  val isReady: Predicate = _.current == 0

  val notReady: Predicate = _.current > 0
}
