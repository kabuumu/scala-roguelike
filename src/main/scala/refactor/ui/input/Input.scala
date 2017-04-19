package refactor.ui.input

import javafx.scene.paint.Color

import refactor.roguelike.movement.{Direction, Movement}
import ui.app.Main
import refactor.core.event.EventBuilder._
import refactor.roguelike.actors.Actor

import scalafx.scene.input.KeyCode

/**
  * Created by rob on 22/04/16.
  */
trait Input

object Input {
  def apply(key: KeyCode) = {
    val dir = Option(key match {
      case KeyCode.Up => Direction.Up
      case KeyCode.Down => Direction.Down
      case KeyCode.Left => Direction.Left
      case KeyCode.Right => Direction.Right
      case _ => null
    })

    dir map Actor.actorMove
  }
}

case object NONE extends Input