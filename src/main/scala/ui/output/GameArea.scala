package ui.output

import core.entity.Entity
import core.system.GameState
import roguelike.actors.Affinity
import roguelike.combat.Attack
import roguelike.movement.lineofsight.{RememberedTiles, VisibleTiles}
import roguelike.movement.{Blocker, Floor, Position}
import roguelike.scenery.{Floor, Wall}
import ui.output.OutputConfig._

import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color

/**
  * Created by rob on 15/05/17.
  */
class GameArea(canvas: Canvas) {
  val g2d = canvas.graphicsContext2D

  val width = canvas.getWidth - size * 5
  val height = canvas.getHeight
  val xTiles = width / size
  val yTiles = height / size

  def update(state: GameState, player: Entity) = {
    g2d.setFill(Color.Black.brighter.brighter)
    g2d.fillRect(0, 0, width, height)

    drawEntities(state, player)
  }

  def drawEntities(state: GameState, player: Entity) = {
    state.entities.toSeq.sortBy(getDrawPriority).foreach {
      entity =>
        for {
          Position(x, y) <- entity[Position]
          Position(playerX, playerY) <- player[Position]
          VisibleTiles(visibleTiles) <- player[VisibleTiles]
          RememberedTiles(rememberedTiles) <- player[RememberedTiles]
        } {
          val isVisible = visibleTiles.contains(Position(x, y))
          val isRemembered = rememberedTiles.contains(Position(x, y))

          val xOffset = (xTiles / 2) - (playerX - x)
          val yOffset = (yTiles / 2) - (playerY - y)

          val (displayX, displayY) = (xOffset * size, yOffset * size)

          if (displayX >= 0 && displayX < width && displayY >= 0 && displayY < height) {
            val colour =
              if (entity[Attack].isDefined) Color.LightGrey
              else if (entity[Affinity].exists(_.faction == Affinity.Player)) Color.Green
              else if (entity[Affinity].exists(_.faction == Affinity.Enemy)) Color.DarkRed
              else if (entity[Wall].isDefined) Color.Grey.darker
              else if (entity[Floor].isDefined) Color.SaddleBrown.darker.darker.desaturate
              else Color.Black

            g2d.setFill(colour)

            def drawTile() = g2d.fillRect(displayX, displayY, size, size)

            if (isVisible) {
              if(entity[Attack].isDefined) println(entity)
              drawTile()
            }
            else if (isRemembered) {
              g2d.setFill(colour.desaturate.desaturate.darker)
              drawTile()
            }
          }
        }
    }

    def getDrawPriority: Entity => Int = {
      case e if e[Floor].isDefined => 0
      case e if e[Blocker].isDefined => 0
      case e if e[Attack].isDefined => 2
      case _ => 1
    }
  }
}
