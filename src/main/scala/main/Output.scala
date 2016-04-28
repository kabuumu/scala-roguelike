package main

import state.GameState

import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color

/**
  * Created by rob on 13/04/16.
  */
object Output {
  def update(state: GameState, canvas: Canvas): Unit = {
    val g2d = canvas.getGraphicsContext2D
    val size = 32

    g2d.clearRect(0, 0, 512, 512)

    g2d.setFill(Color.Green)
    state.actors.foreach(a => {
      a match {
        case pc if pc.isPC =>
          g2d.setFill(Color.Green)
        case enemy => enemy match {
          case alive if alive.isAlive =>
            g2d.setFill(Color.Red)
          case dead =>
            g2d.setFill(Color.Black)
        }
      }
      g2d.fillRect(a.pos.x * size, a.pos.y * size, size, size)
    })
  }
}
