package refactor.roguelike.async

import refactor.core.event.EventBuilder._
import refactor.roguelike.async.Initiative._
import refactor.core.event.Update

/**
  * Created by rob on 12/04/17.
  */
object Async {
  val onActivate: Update = event when isReady update reset
  val updateInitiative: Update = event when notReady update decrement
}
