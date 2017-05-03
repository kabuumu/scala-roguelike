package refactor.roguelike.movement

import refactor.core.entity.Entity
import refactor.core.event.CoreEvents._
import refactor.core.event.Event.Triggered
import refactor.core.event.EventBuilder._
import refactor.core.event.Update
import refactor.roguelike.combat.Projectile._
import refactor.roguelike.movement.Direction.Direction
import refactor.roguelike.movement.Position.move
import refactor.roguelike.movement.Velocity._
import refactor.roguelike.movement.Facing._

/**
  * Created by rob on 20/03/17.
  */
object Movement {
  def moveEvent(dir: Direction): Triggered[Update] = e => onIDMatch(e) updateEntity moveUpdate(dir)

  def moveUpdate(dir:Direction): Entity => Entity = {
    case e if e.exists[Facing](_.dir == dir) => e apply move(dir)
    case e => e apply changeFacing(dir)
  }

  val isImpassable: Entity => Boolean = _[Blocker].isDefined

  val velocityUpdate: Update = event when contains[Velocity] trigger moveByVelocity

  def moveByVelocity: Entity => Option[Update] = e => for {
    Velocity(_, direction, timer) <- e[Velocity]
  } yield if (timer == 0) onIDMatch(e) update move(direction) update resetVelocity trigger projectileCollision
    else onIDMatch(e) update velocityCount
}
