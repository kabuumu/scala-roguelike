package roguelike.actors

import core.entity.Entity
import core.event.CoreEvents._
import core.event.Event.Triggered
import core.event.EventBuilder._
import core.event.Update
import roguelike.actors.attributes.Speed
import roguelike.movement.Collision._
import roguelike.movement.Direction.Direction
import roguelike.movement.Movement._
import roguelike.async.Initiative._
import roguelike.movement.Facing

/**
  * Created by rob on 13/04/17.
  */
trait Actor

object Actor {
  val defaultMoveAmount = 100
  val defaultFacingAmount = 25

  def actorMove(dir: Direction): Triggered[Update] = e =>  onIDMatch(e) update actorMoveUpdate(dir) trigger actorCollision(e)

  def actorMoveUpdate(dir: Direction): Entity => Entity = actor => (for {
    Facing(facing) <- actor[Facing]
    Speed(speed) <- actor[Speed]
  } yield {
    val initiativeIncrease = if(facing == dir) defaultMoveAmount / speed
    else defaultFacingAmount / speed

    moveUpdate(dir)(actor apply increase(initiativeIncrease))
  })
    .getOrElse(actor)


  def actorCollision(originalEntity: Entity): Triggered[Update] = e =>
    onCollision(e) when isImpassable trigger resetEntity(originalEntity)
}