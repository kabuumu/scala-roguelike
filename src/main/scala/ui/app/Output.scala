package ui.app

import rogueLike.combat.Projectile
import rogueLike.movement.Position
import rogueLike.state.Actor
import core.GameState

import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color

/**
  * Created by rob on 13/04/16.
  */
object Output {
  def update(state: GameState, canvas: Canvas): Unit = {
    val g2d = canvas.getGraphicsContext2D
    val size = 32

    val height = 512
    val width = 512

    g2d.clearRect(0, 0, height, width)

    state.entities.collect{
      case a:Actor =>
        g2d.setFill(Color.Green)
        g2d.fillRect(a.pos.x * size, a.pos.y * size, size, size)
      case p:Projectile =>
        g2d.setFill(Color.Red)
        g2d.fillRect(p.pos.x * size, p.pos.y * size, size, size)
    }
  }
}
