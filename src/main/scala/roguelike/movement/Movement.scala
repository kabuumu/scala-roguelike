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
  def moveEvent(dir: Direction): Triggered[Update] = e => onIDMatch(e) update moveUpdate(dir)

  def moveUpdate(dir:Direction, isStrafing: Boolean = false): Entity => Entity = {
    case e if e.exists[Facing](_.dir == dir) || isStrafing => e update move(dir)
    case e => e update changeFacing(dir)
  }

  val isImpassable: Entity => Boolean = _.has[Blocker]
}
