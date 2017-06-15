package roguelike.combat

import core.entity.Entity
import core.event.CoreEvents._
import core.event.EventBuilder._
import core.event.{CreateEntity, Event, EventComponent, Update}
import data.GameData
import roguelike.actors.Affinity
import roguelike.actors.attributes.Speed
import roguelike.async.Initiative
import roguelike.combat.Projectile.projectileCollision
import roguelike.movement.lineofsight.{BresenhamLine, ShadowCaster}
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
    val attackPath = BresenhamLine(e[Position], attackTarget)

    (onIDMatch(e)
      update Initiative.increase(120 / e[Speed].value)
      trigger attack(attackPath)
      )
  }

  def attack(path: Iterator[Position]): Entity => Event = user => {
    val affinity = user[Affinity]
    val attackSpeed = getAttackSpeed(user)

    CreateEntity(GameData.createProjectile(user, path, affinity, BASE_DAMAGE, attackSpeed))
  }

  def getRange(entity: Entity) = entity.get[Weapon].fold(2)(_.range)
  def getAttackSpeed(entity: Entity) = entity.get[Weapon].fold(0)(_.speed)
}