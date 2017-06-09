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
      Seq(projectileCollision(entity))
}

object Attack {
  val BASE_DAMAGE = 10

  def attackEvent(e: Entity, target: Option[Position] = None): Update = {
    val attackTarget = target getOrElse (e[Position] move e[Facing].dir)

    (onIDMatch(e)
      update Initiative.increase(120 / e[Speed].value)
      trigger meleeAttack(attackTarget)
      update (_.-[AttackMode])
      )
  }

  def meleeAttack(pos: Position): Entity => Event = user => {
    val affinity = user[Affinity]

    CreateEntity(meleeAttackEntity(user, pos, affinity, BASE_DAMAGE))
  }
}