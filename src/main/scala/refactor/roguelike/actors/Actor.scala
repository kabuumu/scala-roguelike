package refactor.roguelike.actors

import refactor.core.entity.Entity
import refactor.core.event.CoreEvents._
import refactor.core.event.Event.Triggered
import refactor.core.event.EventBuilder._
import refactor.core.event.Update
import refactor.roguelike.movement.Collision._
import refactor.roguelike.movement.Direction.Direction
import refactor.roguelike.movement.Movement._

/**
  * Created by rob on 13/04/17.
  */
trait Actor

object Actor {
  def actorMove(dir: Direction): Triggered[Update] = e => moveEvent(dir)(e) trigger actorCollision(e)

  def actorCollision(originalEntity: Entity): Triggered[Update] = e =>
    onCollision(e) when isImpassable trigger resetEntity(originalEntity)
}