package refactor.roguelike.combat

import refactor.core.entity.{Entity, ID}
import refactor.core.event.CoreEvents._
import refactor.core.event.Event._
import refactor.core.event._
import refactor.roguelike.actors.Affinity
import refactor.roguelike.combat.Health.{decreaseHealth, decreaseHealthEvent}
import refactor.roguelike.movement.Collision._
import refactor.roguelike.movement.{Facing, Position, Velocity}

/**
  * Created by rob on 28/04/17.
  */
object Projectile {
  import refactor.core.event.EventBuilder._

  def projectileCollision: Triggered[Update] = e =>
    onCollision(e) trigger DeleteEntity(e) trigger damageCheck(e)

  def createProjectile: Triggered[Option[Event]] = creator => for {
    facing <- creator[Facing]
    position <- creator[Position]
    affinity <- creator[Affinity]
  } yield CreateEntity(
    Entity(
      new ID,
      position,
      affinity,
      facing,
      Attack(20),
      Velocity(20, facing.dir)
    )
  )

  def damageCheck(origin: Entity): Entity => Option[Event] = target => for {
      originAffinity <- origin[Affinity]
      targetAffinity <- target[Affinity]
      if originAffinity != targetAffinity
      Attack(damage) <- origin[Attack]
    } yield decreaseHealthEvent(damage)(target)
}
