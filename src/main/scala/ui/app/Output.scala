package ui.app

import core.GameState
import core.util.EntityHelpers._
import rogueLike.actors.Player
import rogueLike.async.Initiative
import rogueLike.health.Health
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
    g2d.clearRect(0, 0, canvas.getWidth, canvas.getHeight)
    g2d.setFill(Color.Black)
    g2d.fillRect(0, 0, canvas.getWidth, canvas.getHeight)

    val (playerX, playerY, playerID) = state
      .entities
      .findEntity[Position]()
      .where[Player]()
      .headOption
      .map(pos => (pos.x, pos.y, pos.id))
      .get //Will crash if player does not exist

    drawEntities(playerX, playerY)
    drawPlayerDetails(playerID)
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

  def drawPlayerDetails(playerID: String) = {
    val entities = state.entities

    for{
      health <- entities.findEntity[Health](_.id == playerID)
      initiative <- entities.findEntity[Initiative](_.id == playerID)
    } {
      g2d.setFill((health.current, health.max) match {
        case (c, m) if c == m => Color.Green
        case (c, m) if c > m / 2 => Color.Yellow
        case _ => Color.Red
      })

      g2d.fillText(s"HP - ${health.current}/${health.max}", gameArea.width + size, size)
      g2d.fillText(s"I  - ${initiative.current}/${initiative.max}", gameArea.width + size, size * 2)

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
