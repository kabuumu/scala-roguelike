package roguelike.combat

import core.entity.Entity
import core.event.CoreEvents._
import core.event.EventBuilder._
import core.event.{CreateEntity, Event, EventComponent, Update}
import data.GameData.meleeAttackEntity
import roguelike.actors.Affinity
import roguelike.actors.attributes.Speed
import roguelike.async.Initiative
import roguelike.combat.Projectile.projectileCollision
import roguelike.movement.{Facing, Position}

/**
  * Created by rob on 08/05/17.
  */
case class Attack(damage: Int) extends EventComponent {
  override val entityEvents: (Entity) => Iterable[Event] =
    entity =>
      Seq(
        projectileCollision(entity)
      )
}

object Attack {
  val BASE_DAMAGE = 10

  val meleeAttack: Entity => Event = user => {
    val pos = user[Position]
    val Facing(dir) = user[Facing]
    val affinity = user[Affinity]

    CreateEntity(meleeAttackEntity(user, pos, dir, affinity, BASE_DAMAGE))
  }

  val attackEvent: Entity => Update = e => (
    onIDMatch(e)
      update Initiative.increase(120 / e[Speed].value)
      trigger meleeAttack
      update (_.-[AttackMode])
    )
}