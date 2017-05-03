package refactor.roguelike.combat

import refactor.core.entity.{Entity, ID}
import refactor.core.event.Event._
import refactor.core.event.EventBuilder._
import refactor.core.event.{CreateEntity, DeleteEntity, Event, Update}
import refactor.roguelike.movement.Collision._
import refactor.roguelike.movement.Direction.Direction
import refactor.roguelike.movement.{Facing, Position, Velocity}

/**
  * Created by rob on 28/04/17.
  */
object Projectile {
  def projectileCollision: Triggered[Update] = e =>
    onCollision(e) trigger DeleteEntity(e)

  def createProjectile: Triggered[Option[Event]] = e => for {
    facing <- e[Facing]
    position <- e[Position]
  } yield CreateEntity(projectile(position, facing.dir))

  private def projectile(pos: Position, facing: Direction) = Entity(new ID, pos, Velocity(20, facing))
}
