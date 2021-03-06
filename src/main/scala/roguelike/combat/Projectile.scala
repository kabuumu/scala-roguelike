package roguelike.combat

import core.entity.{Creator, Entity, ID}
import core.event.CoreEvents._
import core.event.Event._
import core.event._
import data.GameData._
import roguelike.actors.Affinity
import roguelike.combat.Health._
import roguelike.experience.Experience._
import roguelike.movement.Collision._
import roguelike.movement.{Facing, Position, Velocity}
import roguelike.scenery.Scenery._

/**
  * Created by rob on 28/04/17.
  */
object Projectile {
  import core.event.EventBuilder._

  def projectileCollision: Triggered[Update] = e =>
    onCollision(e) when not(isScenery) trigger DeleteEntity(e) trigger {
      (target:Entity) =>
        damageCheck(e)(target) trigger deathCheck(e)(target)
    }

  def deathCheck(projectile: Entity): Triggered[Update] = target =>
    onIDMatch(target) when noHealth trigger ((_:Entity) => giveExperience(projectile))

  def giveExperience(projectile: Entity): Event =
    increaseExperienceEvent(DEFAULT_EXP_AMOUNT)(projectile[Creator].entity)

  def createProjectile: Triggered[Event] = creator => CreateEntity(
    Entity(
      creator[Position],
      creator[Affinity],
      creator[Facing],
      Attack(20),
      Velocity(20, creator[Facing].dir)
    )
  )

  def damageCheck(origin: Entity): Entity => Update = target =>
    onIDMatch(target) when not(matches(origin[Affinity])) update decreaseHealth(origin[Attack])
}
