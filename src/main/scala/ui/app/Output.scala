package ui.app

import core.system.GameState
import roguelike.actors.Affinity
import roguelike.combat.Attack
import roguelike.movement.{Blocker, Position}

import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color

/**
  * Created by rob on 13/04/16.
  */
class Output(state: GameState, canvas: Canvas) {
  val g2d = canvas.getGraphicsContext2D
  val size = 32

  val gameArea = new {
    val width = ((canvas.getWidth * 0.75) / size).toInt * size
    val height = canvas.getHeight.toInt
  }

  val xOffset = (gameArea.width / size) / 2 - 1
  val yOffset = (gameArea.height / size) / 2 - 1

  def update(): Unit = {
    g2d.clearRect(0, 0, canvas.getWidth, canvas.getHeight)
    g2d.setFill(Color.Black)
    g2d.fillRect(0, 0, canvas.getWidth, canvas.getHeight)

    drawEntities()
  }

  def drawEntities() = {
    state.entities.foreach {
      entity =>
        for {
          (x, y) <- entity[Position].map(pos => (pos.x, pos.y))
        } {
          val colour = if (entity[Blocker].isDefined) Color.Grey
          else if(entity[Attack].isDefined) Color.LightGrey
          else if(entity[Affinity].exists(_.faction == Affinity.Player)) Color.Green
          else if(entity[Affinity].exists(_.faction == Affinity.Enemy)) Color.DarkRed
          else Color.White

          println(entity)

          g2d.setFill(colour)
          g2d.fillRect(x * size, y * size, size, size)
        }
    }
  }
}
