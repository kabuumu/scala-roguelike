package roguelike.movement.lineofsight

import java.lang.Math._

import roguelike.movement.Position

/**
  * Created by rob on 20/05/17.
  */
object ShadowCaster {
  def isVisible(x1: Int, y1: Int, x2: Int, y2: Int, blockers: Set[(Int, Int)]): Boolean =
    BresenhamLine(x1, y1, x2, y2) collectFirst {
      case (`x2`, `y2`) => true
      case pos if blockers contains pos => false
    } getOrElse false
}

class BresenhamLine(var x: Int, var y: Int, x2: Int, y2: Int) extends Iterator[(Int, Int)] {
  private val dx = abs(x2 - x)
  private val dy = abs(y2 - y)
  private val sx = if (x < x2) 1 else -1
  private val sy = if (y < y2) 1 else -1

  private var err = dx - dy

  override def hasNext: Boolean = true

  override def next(): (Int, Int) = {
    val e2 = err * 2

    if (e2 > -dy) {
      x += sx
      err -= dy
    }

    if (e2 < dx) {
      y += sy
      err += dx
    }

    x -> y
  }
}

object BresenhamLine {
  def apply(origin: Position, target: Position): Iterator[Position] = {
    new BresenhamLine(origin.x, origin.y, target.x, target.y) map { case (x, y) => Position(x, y) }
  }

  def apply(x: Int, y: Int, x2: Int, y2: Int): Iterator[(Int, Int)] = {
    new BresenhamLine(x, y, x2, y2)
  }
}
