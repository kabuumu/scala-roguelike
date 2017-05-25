package roguelike.ai

import core.entity.Entity
import core.event.Event
import core.event.EventBuilder._
import roguelike.actors.Affinity._
import roguelike.async.Async._
import roguelike.combat.Attack._
import roguelike.movement.pathfinding.PathfindingEvent
import roguelike.movement.{Direction, Facing, Position}

/**
  * Created by rob on 04/05/17.
  */
object EnemyAI {

  def enemyMoveEvent(player: Entity) = onActivate when hasAffinity(Enemy) trigger getEnemyAction(player)

  def getEnemyAction(player: Entity): Entity => Iterable[Event] = (enemy: Entity) => {
    val playerPos = player[Position]

    val enemyPos = enemy[Position]
    val Facing(enemyFacing) = enemy[Facing]

    val distance = enemyPos.distance(playerPos)
    val direction = Direction(enemyPos.x, enemyPos.y, playerPos.x, playerPos.y)

    if (distance < 20) {
      if ((distance > 1) || !(enemyFacing == direction)) Some(PathfindingEvent(player)(enemy))
      else Some(attackEvent(enemy))
    }
    else None
  }
}
