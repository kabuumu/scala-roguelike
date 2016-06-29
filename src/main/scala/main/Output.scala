package main

import movement.Position
import state.{Actor, GameState}

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

    val pos = state.getHero.map(_.pos).getOrElse(Position(0,0))
    val offset = (n: Int) => n-(height/size/2)+1

    g2d.clearRect(0, 0, height, width)

    g2d.setFill(Color.Green)
    state.actors.foreach(a => {
      a match {
        case pc:Actor if pc.isPC =>
          g2d.setFill(Color.Green)
        case enemy:Actor => enemy match {
          case alive if alive.isAlive =>
            g2d.setFill(Color.Red)
          case dead =>
            g2d.setFill(Color.Black)
        }
      }
      g2d.fillRect((a.pos.x - offset(pos.x)) * size, (a.pos.y - offset(pos.y)) * size, size, size)
    })
  }
}
