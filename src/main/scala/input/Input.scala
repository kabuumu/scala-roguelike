package input

import movement.Direction

import scalafx.scene.input.KeyCode

/**
  * Created by rob on 22/04/16.
  */
trait Input

object Input {
  def apply(key: KeyCode) = key match {
    case KeyCode.UP => Direction.Up
    case KeyCode.DOWN => Direction.Down
    case KeyCode.LEFT => Direction.Left
    case KeyCode.RIGHT => Direction.Right
    case _ => NONE
  }
}

case object NONE extends Input