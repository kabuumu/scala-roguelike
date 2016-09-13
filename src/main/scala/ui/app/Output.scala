package ui.app

import core.GameState
import rogueLike.movement.Position
import rogueLike.output.Sprite

import scalafx.scene.canvas.Canvas

/**
  * Created by rob on 13/04/16.
  */
object Output {
  def update(state: GameState, canvas: Canvas): Unit = {
    val g2d = canvas.getGraphicsContext2D
    val size = 32

    val (height, width) = (canvas.getHeight, canvas.getWidth)

    val tiles = height / size

    val offset = tiles / 2 - 1

    g2d.clearRect(0, 0, height, width)

    val (playerX, playerY) = state
      .entities
      .collectFirst { case e: Position if e.id == Main.playerID => e }
      .map(pos => (pos.x, pos.y))
      .get

    state.entities.collect {
      case sprite: Sprite =>
        g2d.setFill(sprite.col)
        state.entities
          .collectFirst { case pos: Position if pos.id == sprite.id => pos }
          .foreach{pos =>
            g2d.fillRect((pos.x + offset - playerX) * size, (pos.y + offset - playerY) * size, size, size)}
    }
  }
}
