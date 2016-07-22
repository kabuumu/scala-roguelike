package input

import combat.Combat
import movement.{Direction, Movement, Mover, Position}
import state.Actor

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

    if(key == KeyCode.A) Some(Combat.projectileEvent(0, new Actor(1, Position(0,0), Direction.Up)))
    else dir.map(dir => Movement.moveEvent(0, dir))
  }
}

case object NONE extends Input