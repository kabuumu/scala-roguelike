package refactor.roguelike.movement

import refactor.core.entity.{Entity, ID}
import refactor.core.event.Event.Triggered
import refactor.core.event.EventBuilder._
import refactor.core.event.Update


/**
  * Created by rob on 20/03/17.
  */
object Collision {
  val onCollision: Triggered[Update] = (entity) =>
    event when matches(entity[Position]) whenNot matches(entity[ID])
}
