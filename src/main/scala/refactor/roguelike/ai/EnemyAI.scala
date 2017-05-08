package refactor.roguelike.ai

import refactor.core.entity.Entity
import refactor.core.event.Event
import refactor.core.event.Event.Triggered
import refactor.core.event.EventBuilder._
import refactor.roguelike.actors.Actor._
import refactor.roguelike.actors.Affinity._
import refactor.roguelike.async.Async._
import refactor.roguelike.movement.{Direction, Position}

/**
  * Created by rob on 04/05/17.
  */
object EnemyAI {

  def enemyMoveEvent(player:Entity) = onActivate when hasAffinity(Enemy) trigger getEnemyAction(player)

  def getEnemyAction(player: Entity): Triggered[Event] = (enemy: Entity) => {
    def getDistance(axis: Position => Int) = for {
      playerVal <- player.get(axis)
      enemyVal <- enemy.get(axis)
    } yield enemyVal - playerVal

    val (direction, _) = Set(
      Direction.Up -> getDistance(Position.y).filter(_ > 0),
      Direction.Down -> getDistance(Position.y).filter(_ < 0),
      Direction.Right -> getDistance(Position.x).filter(_ < 0),
      Direction.Left -> getDistance(Position.x).filter(_ > 0)
    ).maxBy(_._2)

    actorMove(direction)(enemy)
  }
}
