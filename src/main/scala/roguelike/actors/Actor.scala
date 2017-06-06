package roguelike.actors

import core.entity.Entity
import core.event.CoreEvents._
import core.event.Event.Triggered
import core.event.EventBuilder._
import core.event.Update
import roguelike.actors.attributes.Speed
import roguelike.async.Initiative._
import roguelike.movement.Collision._
import roguelike.movement.Direction.Direction
import roguelike.movement.Facing
import roguelike.movement.Movement._

/**
  * Created by rob on 13/04/17.
  */
trait Actor

object Actor {
  val defaultMoveAmount = 100
  val defaultFacingAmount = 50

  def actorMove(dir: Direction, isStrafing: Boolean = false): Triggered[Update] = e => (
    onIDMatch(e)
      update actorMoveUpdate(dir, isStrafing)
      trigger actorCollision(e)
    )

  def actorMoveUpdate(dir: Direction, isStrafing: Boolean): Entity => Entity = actor => {
    val Facing(facing) = actor[Facing]
    val Speed(speed) = actor[Speed]

    val initiativeIncrease = if (facing == dir || isStrafing) defaultMoveAmount / speed
    else defaultFacingAmount / speed

    moveUpdate(dir, isStrafing)(actor update increase(initiativeIncrease))
  }

  def actorCollision(originalEntity: Entity): Triggered[Update] = e =>
    onCollision(e) when isImpassable trigger resetEntity(originalEntity)
}