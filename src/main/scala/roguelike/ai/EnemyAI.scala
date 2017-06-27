package roguelike.ai

import core.entity.Entity
import core.event.EventBuilder._
import core.event.{Event, Update}
import roguelike.actors.Affinity._
import roguelike.async.Initiative
import roguelike.combat.Attack._
import roguelike.movement.lineofsight.ShadowCaster.isVisible
import roguelike.movement.pathfinding.PathfindingEvent
import roguelike.movement.{Blocker, Position}

/**
  * Created by rob on 04/05/17.
  */
object EnemyAI {
  def enemyActionEvent(player: Entity): Event = new Update(
    predicate = entity => (entity exists Initiative.isReady) && (entity exists hasAffinity(Enemy)) ,
    f = {
      case (state, enemy) =>
        val playerPos = player[Position]

        val enemyPos = enemy[Position]

        val distance = enemyPos.distance(playerPos)
        val attackRange = getRange(enemy)

        val blockers = state.entities collect {
          case e if e.has[Blocker] =>
            val Position(x, y) = e[Position]
            x -> y
        } toSet

        if (distance < 20) {
          if ((distance > attackRange) || !isVisible(enemyPos.x, enemyPos.y, playerPos.x, playerPos.y, blockers)) {
            PathfindingEvent(player)(enemy).f(state -> enemy)
          }
          else attackEvent(enemy, Some(playerPos)).f(state -> enemy)
        }
        else defaultFunction(state -> enemy)
    }
  )
}
