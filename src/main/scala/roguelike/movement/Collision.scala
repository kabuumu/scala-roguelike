package roguelike.movement

import core.entity.{Entity, ID}
import core.event.Event.Triggered
import core.event.EventBuilder._
import core.event.Update


/**
  * Created by rob on 20/03/17.
  */
object Collision {
  val onCollision: Triggered[Update] = (entity) =>
    event when matches(entity[Position]) when not(matches(entity[ID]))
}
