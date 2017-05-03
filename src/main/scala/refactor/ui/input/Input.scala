package refactor.ui.input

import refactor.core.entity.Entity
import refactor.core.event.CoreEvents._
import refactor.core.event.Event
import refactor.core.event.Event.Triggered
import refactor.core.event.EventBuilder._
import refactor.roguelike.actors.Actor
import refactor.roguelike.combat.Projectile._
import refactor.roguelike.movement.Direction
import refactor.roguelike.movement.Direction.Direction

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
    KeyCode.A -> Attack
  )

  def apply(key: KeyCode): Option[Triggered[Event]] = {
    inputMap.get(key).collect[Entity => Event] {
      case dir: Direction => Actor.actorMove(dir)
      case Attack => e:Entity => onIDMatch(e) trigger createProjectile
    }
  }
}