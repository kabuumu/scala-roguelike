package ui.output

import core.entity.Entity
import core.system.GameState
import roguelike.actors.Affinity
import roguelike.combat.Attack
import roguelike.light.{LightBlocker, LightCaster}
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
  sealed trait Shape
  case object Square extends Shape
  case object Circle extends Shape

  val g2d = canvas.graphicsContext2D

  val width = canvas.getWidth - size * 5
  val height = canvas.getHeight
  val xTiles = width / size
  val yTiles = height / size

  val minLightLevel: Double = .5

  def update(state: GameState, player: Entity, input: InputController) = {
    g2d.setFill(Color.Black.brighter.brighter)
    g2d.fillRect(0, 0, width, height)

    drawEntities(state, player, input)
  }

  def drawEntities(state: GameState, player: Entity, input: InputController) = {
    val lightCasters = state.entities.filter (_.has[LightCaster])
    val blockers = state.entities collect { case e if e.has[LightBlocker] => e[Position] -> e[LightBlocker]} toSet

    state.entities.toSeq.sortBy(getDrawPriority).foreach {
      entity =>
        val Position(x, y) = entity[Position]
        val Position(playerX, playerY) = player[Position]
        val VisibleTiles(visibleTiles) = player[VisibleTiles]
        val RememberedTiles(rememberedTiles) = player[RememberedTiles]

        val isVisible = visibleTiles.contains(Position(x, y))
        val isRemembered = rememberedTiles.contains(Position(x, y))
        val isSceneryEntity = entity.has[Scenery]

        val xOffset = (xTiles / 2) - (playerX - x)
        val yOffset = (yTiles / 2) - (playerY - y)

        val (displayX, displayY) = (xOffset * size, yOffset * size)

        if (displayX >= 0 && displayX < width && displayY >= 0 && displayY < height) {


          val lightLevels: Iterable[Double] = (lightCasters map (lc =>
            LightCaster.getLightLevel(lc[LightCaster], lc[Position], entity[Position], blockers)
            )).filterNot(_ == 0)//.sum.min(2).max(minLightLevel)

          val lightLevel = if(lightLevels.isEmpty) minLightLevel
          else (lightLevels.sum / lightLevels.size).max(minLightLevel)

//          val lightLevel = (lightLevels.sum / 2).max(minLightLevel)

          def getColour(colour: Color, lightLevel: Double) = {
            def colourOf(colour:Double) = (colour * lightLevel * 255).toInt.min(255)

            Color.rgb(colourOf(colour.red), colourOf(colour.green), colourOf(colour.blue))
          }

          def draw(shape: Shape, colour: Color) = {
            def drawShape() = shape match {
              case Square => g2d.fillRect(displayX, displayY, size, size)
              case Circle => g2d.fillOval(displayX, displayY, size, size)
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
    case e if e.has[Blocker] => 0
    case e if e.has[Attack] => 2
    case _ => 1
  }
}
