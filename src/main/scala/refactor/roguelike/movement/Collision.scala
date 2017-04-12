package refactor.roguelike.movement

import refactor.core.entity.{Entity, ID}
import refactor.core.event.EventBuilder._


/**
  * Created by rob on 20/03/17.
  */
object Collision {
  val onCollision = (entity: Entity) =>
    event when matches(entity[Position]) whenNot matches(entity[ID])
}
