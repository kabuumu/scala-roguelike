package refactor.roguelike.movement

import refactor.core.entity.{Entity, ID}
import refactor.core.event.CoreEvents._
import refactor.core.event.Event.TriggeredEvent
import refactor.core.event.EventBuilder._
import refactor.roguelike.movement.Collision.onCollision
import refactor.roguelike.movement.Direction.Direction
import refactor.roguelike.movement.Position.move
import rogueLike.movement.Blocker

/**
  * Created by rob on 20/03/17.
  */
object Movement {
  def moveEvent(dir: Direction): TriggeredEvent = e => event when matches(e[ID]) update move(dir)

  val isImpassable: Entity => Boolean = _[Blocker].isDefined

  val invalidMove: TriggeredEvent = e => onCollision(e) when isImpassable trigger resetEvent(e)
}
