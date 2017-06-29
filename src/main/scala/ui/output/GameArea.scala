package ui.output

import core.entity.{Entity, ID}
import core.system.GameState
import roguelike.actors.Affinity
import roguelike.combat.Attack
import roguelike.light.{LightBlocker, LightCaster, LightMap}
import roguelike.movement.lineofsight.{RememberedTiles, VisibleTiles}
import roguelike.movement.{Blocker, Position}
import roguelike.scenery.{Floor, Scenery, Wall}
import ui.input.InputController
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
  val minLightLevel: Double = 0.4

  def update(state: GameState, player: Entity, input: InputController) = {
    g2d.setFill(Color.Black.brighter.brighter)
    g2d.fillRect(0, 0, width, height)

    drawEntities(state, player, input)
  }

  def drawEntities(state: GameState, player: Entity, input: InputController) = {
    val Position(playerX, playerY) = player[Position]
    val VisibleTiles(visibleTiles) = player[VisibleTiles]
    val RememberedTiles(rememberedTiles) = player[RememberedTiles]
    def getXOffset(x: Int) = (xTiles / 2) - (playerX - x)
    def getYOffset(y: Int) = (yTiles / 2) - (playerY - y)

    val lightMap = player[LightMap].values

    state.entities.toSeq
      .sortBy(getDrawPriority).foreach {
      entity =>
        val pos @ Position(x, y) = entity[Position]

        lazy val isVisible = visibleTiles.contains(pos)
        lazy val isRemembered = rememberedTiles.contains(pos)
        val isSceneryEntity = entity.has[Scenery]

        val xOffset = getXOffset(x)
        val yOffset = getYOffset(y)

        val (displayX, displayY) = (xOffset * size, yOffset * size)

        if (displayX >= 0 && displayX < width && displayY >= 0 && displayY < height) {

          val lightLevel = lightMap.getOrElse(pos, minLightLevel).max(minLightLevel)

          def getColour(colour: Color, lightLevel: Double) = {
            def colourOf(colour: Double) = (colour * lightLevel * 255).toInt.min(255)

            Color.rgb(colourOf(colour.red), colourOf(colour.green), colourOf(colour.blue))
          }

          def draw(shape: Shape, colour: Color) = {
            def drawShape() = shape match {
              case Square => g2d.fillRect(displayX, displayY, size, size)
              case Circle =>
                g2d.fillOval(displayX, displayY, size, size)
            }

            if (isVisible) {
              g2d.setFill(getColour(colour, lightLevel))
              drawShape()
            }
            else if (isRemembered && isSceneryEntity) {
              g2d.setFill(getColour(colour.desaturate.desaturate, minLightLevel))
              drawShape()
            }
          }

          if (entity.has[Attack]) draw(Square, Color.LightGrey)
          else if (entity.exists[Affinity](_.faction == Affinity.Player)) draw(Circle, Color.Green)
          else if (entity.exists[Affinity](_.faction == Affinity.Enemy)) draw(Circle, Color.DarkRed)
          else if (entity.has[Wall]) draw(Square, Color.Grey.darker)
          else if (entity.has[Floor]) draw(Square, Color.SaddleBrown.darker.darker.desaturate)
          else if (entity.has[LightCaster]) draw(Square, Color.Orange)
        }
    }

    g2d.setFill(Color.Green)
    g2d.fillText(s"Enemies ${state.entities.count(_.get[Affinity].exists(_.faction == Affinity.Enemy))}", 0, 16)

    input.target.foreach { case Position(x, y) =>
      g2d.setStroke(Color.Yellow)

      val Position(playerX, playerY) = player[Position]

      val xOffset = (xTiles / 2) - (playerX - x)
      val yOffset = (yTiles / 2) - (playerY - y)

      val (displayX, displayY) = (xOffset * size, yOffset * size)

      g2d.strokeRect(displayX, displayY, size, size)
    }
  }

  def getDrawPriority: Entity => Int = {
    case e if e.has[Floor] => 0
    case e if e.has[Attack] => 2
    case _ => 1
  }

  sealed trait Shape

  case object Square extends Shape

  case object Circle extends Shape
}
