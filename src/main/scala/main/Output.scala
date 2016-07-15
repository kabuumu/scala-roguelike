package main

import movement.Position
import state.Actor
import events.GameState

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

    g2d.setFill(Color.Green)

    state.entities.collect{
      case a:Actor => g2d.fillRect(a.pos.x * size, a.pos.y * size, size, size)
    }
  }
}
