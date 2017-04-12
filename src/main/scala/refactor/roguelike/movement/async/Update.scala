package refactor.roguelike.movement.async

import refactor.core.event.Event.TriggeredEvent
import refactor.core.event.EventBuilder._
import refactor.roguelike.movement.async.Initiative._

/**
  * Created by rob on 12/04/17.
  */
object Update {
  val initiativeCheck: TriggeredEvent = e => event when isReady update reset

  val updateEvent = initiativeCheck trigger
}
