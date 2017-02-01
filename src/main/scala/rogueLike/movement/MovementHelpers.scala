package rogueLike.movement

/**
  * Created by rob on 04/01/17.
  */
object MovementHelpers {
  def getNearest(origin: Position)(a: Position, b: Position) = {
    val xDiff1 = origin.x - a.x abs
    val yDiff1 = origin.y - a.y abs

    val xDiff2 = origin.x - b.x abs
    val yDiff2 = origin.y - b.y abs

    if ((xDiff1 + yDiff1) > (xDiff2 + yDiff2)) a else b
  }
}
