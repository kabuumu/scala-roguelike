package refactor.roguelike.movement

import refactor.core.entity.Entity
import refactor.core.event.CoreEvents._
import refactor.core.event.Event.Triggered
import refactor.core.event.EventBuilder._
import refactor.core.event.Update
import refactor.roguelike.movement.Direction.Direction
import refactor.roguelike.movement.Position.move

/**
  * Created by rob on 20/03/17.
  */
object Movement {
  def moveEvent(dir: Direction): Triggered[Update] = e => onIDMatch(e) update move(dir)

  val isImpassable: Entity => Boolean = _[Blocker].isDefined
}
