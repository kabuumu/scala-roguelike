package rogueLike.los

import core.{Entity, Event}
import rogueLike.movement.Position

/**
  * Created by rob on 14/09/16.
  */
object LineOfSight {
  def losBlocked(origin: Position, target: Position, entities: Iterable[Entity]): Option[Position] = {
    val line = drawLine(origin.x, origin.y, target.x, target.y)

    val res = for{
      pos <- entities.collectFirst {
        case pos: Position if line.contains((pos.x, pos.y)) && pos != origin && pos != target => pos
      }
      blocker <- entities.collectFirst {
        case blocker: Entity if blocker.id == pos.id => blocker
      }
    } yield pos

    res
  }

  def drawLine(origX: Int, origY: Int, targX: Int, targY: Int): Seq[(Int, Int)] = {
    val dx = (origX - targX).abs
    val dy = (origY - targY).abs

    val sx = if(origX < targX) 1 else -1
    val sy = if(origY < targY) 1 else -1

    val err = dx-dy

    def loop(origX: Int, origY: Int, targX: Int, targY: Int, err: Int, line: Seq[(Int, Int)] = Nil): Seq[(Int, Int)] = {
      if (targX == origX && targY == origY) {
        line
      }
      else if (err * 2 > -1 * dy) {
        loop(origX + sx, origY, targX, targY, err - dy, line :+(origX, origY))
      }
      else loop(origX, origY + sy, targX, targY, err + dx, line :+(origX, origY))
    }

    loop(origX, origY, targX, targY, err, Nil)
  }
}
