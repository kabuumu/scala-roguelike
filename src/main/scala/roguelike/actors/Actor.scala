package roguelike.actors

import core.entity.Entity
import core.event.CoreEvents._
import core.event.Event.Triggered
import core.event.EventBuilder._
import core.event.Update
import roguelike.movement.Collision._
import roguelike.movement.Direction.Direction
import roguelike.movement.Movement._

/**
  * Created by rob on 13/04/17.
  */
trait Actor

object Actor {
  def actorMove(dir: Direction): Triggered[Update] = e => moveEvent(dir)(e) trigger actorCollision(e)

  def actorCollision(originalEntity: Entity): Triggered[Update] = e =>
    onCollision(e) when isImpassable trigger resetEntity(originalEntity)
}