package ui.input

import core.Event
import rogueLike.async.{HasInitiative, Initiative}
import rogueLike.combat.{Combat, Projectile}
import rogueLike.movement.{Direction, Movement, Position}
import ui.app.Main

import scalafx.scene.input.KeyCode

/**
  * Created by rob on 22/04/16.
  */
trait Input

object Input {
  def apply(key: KeyCode) = {
    val dir = Option(key match {
      case KeyCode.UP => Direction.Up
      case KeyCode.DOWN => Direction.Down
      case KeyCode.LEFT => Direction.Left
      case KeyCode.RIGHT => Direction.Right
      case _ => null
    })

    if(key == KeyCode.A) Some(Combat.projectileEvent(Main.playerID, new Projectile(Position(0,0), Direction.Up, Initiative(3))))
    else dir.map(dir =>
      Event{case e:HasInitiative if e.id == Main.playerID && e.initiative.current == 0 => (Iterable(e.initiative(_.reset)), Some(Movement.moveEvent(Main.playerID, dir)))})
  }
}

case object NONE extends Input