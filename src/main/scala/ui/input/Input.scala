package ui.input

import javafx.scene.paint.Color

import core.Entity._
import core.{Entity, Event}
import rogueLike.async.Initiative
import rogueLike.combat.{Combat, Projectile}
import rogueLike.movement.{Direction, Movement, Position}
import rogueLike.output.Sprite
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

    if(key == KeyCode.A) Some(Combat.projectileEvent("pc", createEntity(Projectile(_), Initiative(2, _), Position(0,0,Direction.Up, isBlocker = true, _), Sprite(Color.RED, _))))
    else dir.map(dir =>
      Event{case e:Initiative if e.id == Main.playerID && e.current == 0 => (Iterable(e.reset), Some(Movement.moveEvent(Main.playerID, dir)))})
  }
}

case object NONE extends Input