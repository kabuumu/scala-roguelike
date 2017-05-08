package roguelike.movement

import core.entity.Entity
import core.event.CoreEvents._
import core.event.Event.Triggered
import core.event.EventBuilder._
import core.event.Update
import roguelike.combat.Projectile._
import roguelike.movement.Direction.Direction
import roguelike.movement.Position.move
import roguelike.movement.Velocity._
import roguelike.movement.Facing._

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
