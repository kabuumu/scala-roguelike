package ui.input

import cats.implicits._
import core.event.Event
import core.event.Event.Triggered
import core.event.EventBuilder._
import roguelike.actors.Actor._
import roguelike.async.Initiative.isReady
import roguelike.combat.Attack._
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

  def apply(key: KeyCode): Option[Triggered[Event]] = for {
      event <- inputMap.get(key).collect {
        case dir: Direction => actorMove(dir)
        case AttackInput => attackEvent
      }
    } yield event.map(_ when isReady)
}