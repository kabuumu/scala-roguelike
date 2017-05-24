package roguelike.combat

import core.entity.{Entity, ID}
import core.event.CoreEvents._
import core.event.EventBuilder._
import core.event.{CreateEntity, Event, EventComponent, Update}
import roguelike.actors.Affinity
import roguelike.async.{Initiative, Temporary}
import roguelike.combat.Projectile.projectileCollision
import roguelike.movement.{Facing, Position}
import data.GameData.meleeAttackEntity

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
  val meleeAttack: Entity => Iterable[Event] = user => {
    for {
      pos <- user[Position]
      Facing(dir) <- user[Facing]
      affinity <- user[Affinity]
    } yield CreateEntity(meleeAttackEntity(user, pos, dir, affinity))
  }

  val attackEvent: Entity => Update = e => onIDMatch(e) update Initiative.reset trigger meleeAttack
}