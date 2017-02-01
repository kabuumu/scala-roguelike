package ui.app

import core.GameState
import core.util.EntityHelpers._
import rogueLike.actors.Player
import rogueLike.movement.{Direction, Position}
import rogueLike.output.Sprite

import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color

/**
  * Created by rob on 13/04/16.
  */
class Output(state: GameState, canvas: Canvas) {
  val g2d = canvas.getGraphicsContext2D
  val size = 32

  val gameArea = new {
    val width = (((canvas.getWidth * 0.75) / size) toInt) * size
    val height = canvas.getHeight toInt
  }

  val xOffset = (gameArea.width / size) / 2 - 1
  val yOffset = (gameArea.height / size) / 2 - 1

  def update(): Unit = {
    g2d.clearRect(0, 0, gameArea.width, gameArea.height)
    g2d.setFill(Color.Black)
    g2d.fillRect(0, 0, gameArea.width, gameArea.height)

    val (playerX, playerY) = state
      .entities
      .findEntity[Position]()
      .where[Player]()
      .headOption
      .map(pos => (pos.x, pos.y))
      .get //Will crash if player does not exist

    drawEntities(playerX, playerY)
  }

  def drawEntities(playerX: Int, playerY: Int) = {
    state.entities.collect {
      case sprite: Sprite =>

        g2d.setFill(sprite.col)
        state.entities
          .collectFirst {
            case pos: Position if pos.id == sprite.id && isDrawablePosition(pos, playerX, playerY) =>
              pos
          }
          .foreach { pos =>
            val left = (pos.x + xOffset - playerX) * size
            val centerX = left + (size / 2)
            val right = left + size

            val bottom = (pos.y + yOffset - playerY) * size
            val centerY = bottom - (size / 2)
            val top = bottom - size

            pos.facing match {

              case Direction.Right =>
                g2d.fillPolygon(
                  Array(left, right, left),
                  Array(bottom, centerY, top),
                  3
                )
              case Direction.Left =>
                g2d.fillPolygon(
                  Array(right, left, right),
                  Array(bottom, centerY, top),
                  3
                )
              case Direction.Up =>
                g2d.fillPolygon(
                  Array(left, centerX, right),
                  Array(bottom, top, bottom),
                  3
                )
              case Direction.Down =>
                g2d.fillPolygon(
                  Array(left, centerX, right),
                  Array(top, bottom, top),
                  3
                )
            }
          }
    }
  }

  def isDrawablePosition(pos: Position, playerX: Int, playerY: Int) = {
    val left = (pos.x + xOffset - playerX) * size
    val right = left + size

    val bottom = (pos.y + yOffset - playerY) * size
    val top = bottom - size

    left >= 0 && right <= gameArea.width && top >= 0 && bottom <= gameArea.height
  }
}
