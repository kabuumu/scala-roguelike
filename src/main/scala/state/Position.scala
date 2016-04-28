package state

import input._

/**
  * Created by rob on 26/04/16.
  */
case class Position(x: Int, y: Int) {
  def move(input: Input): Option[Position] = Option(
    input match {
      case UP => copy(y = y - 1)
      case DOWN => copy(y = y + 1)
      case RIGHT => copy(x = x + 1)
      case LEFT => copy(x = x - 1)
      case _ => null
    }
  )
}
