package ui.input

import core.entity.Entity
import core.event.CoreEvents._
import core.event.Event
import core.event.Event.Triggered
import core.event.EventBuilder._
import roguelike.actors.Actor
import roguelike.combat.Projectile._
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
    KeyCode.A -> Attack
  )

  def apply(key: KeyCode): Option[Triggered[Event]] = {
    inputMap.get(key).collect[Entity => Event] {
      case dir: Direction => Actor.actorMove(dir)
      case Attack => e:Entity => onIDMatch(e) trigger createProjectile
    }
  }
}