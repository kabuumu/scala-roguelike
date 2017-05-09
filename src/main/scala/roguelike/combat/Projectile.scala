package roguelike.combat

import core.entity.{Entity, ID}
import core.event.Event._
import core.event._
import roguelike.actors.Affinity
import roguelike.combat.Health.decreaseHealthEvent
import roguelike.movement.Collision._
import roguelike.movement.{Facing, Position, Velocity}

/**
  * Created by rob on 28/04/17.
  */
object Projectile {
  import core.event.EventBuilder._

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

  def damageCheck(origin: Entity): Entity => Iterable[Event] = target => for {
      originAffinity <- origin[Affinity]
      targetAffinity <- target[Affinity]
      if originAffinity != targetAffinity
      Attack(damage) <- origin[Attack]
    } yield decreaseHealthEvent(damage)(target)
}
