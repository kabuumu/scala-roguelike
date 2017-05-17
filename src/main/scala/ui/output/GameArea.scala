package ui.output

import core.entity.Entity
import core.system.GameState
import roguelike.actors.Affinity
import roguelike.combat.Attack
import roguelike.movement.{Blocker, Position}

import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color
import OutputConfig._

/**
  * Created by rob on 15/05/17.
  */
class GameArea (canvas: Canvas) {
  val g2d = canvas.graphicsContext2D

  val width = canvas.getWidth - size * 5
  val height = canvas.getHeight
  val xTiles = width / size
  val yTiles = height / size

  def update(state: GameState, player: Entity) = {
    g2d.setFill(Color.Black.brighter)
    g2d.fillRect(0, 0, width, height)

    drawEntities(state, player)
  }

  def drawEntities(state: GameState, player: Entity) = {
    state.entities.foreach {
      entity =>
        for {
          Position(x, y) <- entity[Position]
          Position(playerX, playerY) <- player[Position]
        } {
          val xOffset = (xTiles / 2) - (playerX - x)
          val yOffset = (yTiles / 2) - (playerY - y)

          val colour =
            if (entity[Attack].isDefined) Color.LightGrey
            else if (entity[Affinity].exists(_.faction == Affinity.Player)) Color.Green
            else if (entity[Affinity].exists(_.faction == Affinity.Enemy)) Color.DarkRed
            else if (entity[Blocker].isDefined) Color.Grey
            else Color.Black

          g2d.setFill(colour)

          val (displayX, displayY) = (xOffset * size, yOffset * size)

          if(displayX < width && displayY < height) {
            g2d.fillRect(displayX, displayY, size, size)
          }
        }
    }
  }
}
