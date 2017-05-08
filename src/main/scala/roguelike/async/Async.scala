package roguelike.async

import core.event.EventBuilder._
import roguelike.async.Initiative._
import core.event.Update

/**
  * Created by rob on 12/04/17.
  */
object Async {
  val onActivate: Update = event when isReady update reset
  val updateInitiative: Update = event when notReady update decrement
}
