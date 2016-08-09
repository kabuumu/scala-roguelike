package ui.app

import rogueLike.movement.Position
import core.GameState
import rogueLike.output.Sprite

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
      case sprite:Sprite =>
        g2d.setFill(sprite.col)
        state.entities
          .collectFirst{case pos:Position if pos.id == sprite.id => pos}
          .foreach(pos => g2d.fillRect(pos.x * size, pos.y * size, size, size))
    }
  }
}
