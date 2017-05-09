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

/**
  * Created by rob on 13/04/17.
  */
trait Actor

object Actor {
  val defaultMoveAmount = 100

  def actorMove(dir: Direction): Triggered[Update] = e => moveEvent(dir)(e) update increaseInitative trigger actorCollision(e)

  def increaseInitative: Entity => Entity = actor => (for {
    Speed(speed) <- actor[Speed]
  } yield actor apply increase(defaultMoveAmount / speed))
    .getOrElse(actor)

  def actorCollision(originalEntity: Entity): Triggered[Update] = e =>
    onCollision(e) when isImpassable trigger resetEntity(originalEntity)
}