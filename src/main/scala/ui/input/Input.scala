package ui.input

import core.entity.Entity
import core.event.Event
import roguelike.actors.Actor._
import roguelike.async.Initiative.isReady
import roguelike.combat.Attack._
import roguelike.combat.AttackMode
import roguelike.combat.AttackMode._
import roguelike.movement.Direction
import roguelike.movement.Direction.Direction

import scalafx.scene.input.KeyCode

/**
  * Created by rob on 22/04/16.
  */
trait Input

object Input {
  val inputMap = Map(
    KeyCode.Up -> Direction.Up,
    KeyCode.Down -> Direction.Down,
    KeyCode.Left -> Direction.Left,
    KeyCode.Right -> Direction.Right,
    KeyCode.A -> AttackInput
  )

  def apply(keys: Set[KeyCode], entity: Entity): Iterable[Event] = {
    val inputs = keys.flatMap(inputMap.get)

    if(entity exists isReady) {
      if (entity.has[AttackMode]) {
        if(inputs.isEmpty) Some(attackEvent(entity))
        else inputs.collect {
          case dir: Direction => actorMove(dir, isStrafing = true)(entity)
        }
      }
      else inputs collectFirst {
        case dir: Direction => actorMove(dir)(entity)
        case AttackInput => setAttackMode(entity)
      }
    }
    else None
  }
}