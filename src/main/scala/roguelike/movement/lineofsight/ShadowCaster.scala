package roguelike.movement.lineofsight

import java.lang.Math._

import scala.annotation.tailrec

/**
  * Created by rob on 20/05/17.
  */
object ShadowCaster {
  def isVisible(x1: Int, y1: Int, x2: Int, y2: Int, blockers: Set[(Int, Int)]): Boolean = {
    val dx = abs(x2 - x1)
    val dy = abs(y2 - y1)

    val sx = if(x1 < x2) 1 else -1
    val sy = if(y1 < y2) 1 else -1

    @tailrec
    def loop(x: Int, y: Int, err: Int): Boolean = {
      val point = x -> y

      val e2 = err * 2

      if (x == x2 && y == y2) true
      else if(blockers.contains(point)) false
      else {
        val newX = if (e2 > -dy) x + sx else x
        val newY = if (e2 < dx) y + sy else y
        val newErr = {
          if (e2 > -dy && e2 < dx) err - dy + dx
          else if (e2 > -dy) err - dy
          else if (e2 < dx) err + dx
          else err
        }

        loop(newX, newY, newErr)
      }
    }

    loop(x1, y1, dx - dy)

//    def next = {
//      val omitted = (x, y)
//      val e2 = 2 * err
//      if (e2 > -dy) {
//        err -= dy
//        x += sx
//      }
//      if (e2 < dx) {
//        err += dx
//        y += sy
//      }
//      omitted
//    }
  }
}
