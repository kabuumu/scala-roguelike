package roguelike.movement

import ui.input.Input

/**
  * Created by rob on 21/06/16.
  */
object Direction {
  sealed trait Direction extends Input
  case object Up extends Direction
  case object Down extends Direction
  case object Left extends Direction
  case object Right extends Direction

  def apply(x1: Int, y1: Int, x2: Int, y2: Int) = {
    (x1 - x2, y1 - y2) match {
      case (x, _) if x < 0 => Right
      case (x, _) if x > 0 => Left
      case (_, y) if y < 0 => Down
      case _ => Up
    }
  }
}
