package roguelike.ai

import core.entity.Entity
import core.event.Event
import core.event.EventBuilder._
import roguelike.actors.Actor._
import roguelike.actors.Affinity._
import roguelike.async.Async._
import roguelike.combat.Attack._
import roguelike.movement.{Direction, Facing, Position}

/**
  * Created by rob on 04/05/17.
  */
object EnemyAI {

  def enemyMoveEvent(player:Entity) = onActivate when hasAffinity(Enemy) trigger getEnemyAction(player)

  def getEnemyAction(player: Entity): Entity => Iterable[Event] = (enemy: Entity) => {
    def getDistance(axis: Position => Int) = for {
      playerVal <- player.get(axis)
      enemyVal <- enemy.get(axis)
    } yield enemyVal - playerVal

    val (direction, distance) = Set(
      Direction.Up -> getDistance(Position.y).filter(_ > 0),
      Direction.Down -> getDistance(Position.y).filter(_ < 0),
      Direction.Right -> getDistance(Position.x).filter(_ < 0),
      Direction.Left -> getDistance(Position.x).filter(_ > 0)
    ).maxBy(_._2)

    if((distance exists (_ > 1)) || !enemy[Facing].exists(_.dir == direction) ) Some(actorMove(direction)(enemy))
    else if ((distance contains 1) && enemy[Facing].exists(_.dir == direction)) Some(attackEvent(enemy))
    else None
  }
}
