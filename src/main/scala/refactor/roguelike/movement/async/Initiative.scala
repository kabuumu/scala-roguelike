package refactor.roguelike.movement.async

/**
  * Created by rob on 12/04/17.
  */
case class Initiative(current: Int, max: Int) {

}

object Initiative {
  type Update = Initiative => Initiative
  type Predicate = Initiative => Boolean

  val reset: Update = c => c.copy(current = c.max)

  val isReady: Predicate = _.current == 0
}
