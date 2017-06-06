package roguelike.movement.pathfinding

import scala.annotation.tailrec
import Pathfinder._

/**
  * Created by rob on 15/05/17.
  */

class Pathfinder(origin: (Int, Int), target: (Int, Int), blockers: Set[(Int, Int)]) {
  lazy val path: Option[Seq[(Int, Int)]] = loop()
  lazy val getNext: Option[(Int, Int)] = path flatMap (_.headOption)
  var loopCount = 0

  @tailrec
  private def loop(
                    openPaths: Seq[ScoredTile] = Seq(
                      ScoredTile(
                        score = getDistance(origin, target),
                        parent = origin,
                        position = origin
                      )
                    ),
                    closedTiles: Seq[ScoredTile] = Seq.empty
                  ): Option[Seq[(Int, Int)]] = {
    loopCount += 1

    openPaths match {
      case currentTile :: tail =>
        val ScoredTile(score, _, pos) = currentTile

        val options = getOptions(pos)
          .filterNot(closedTiles.contains)
          .map (
            newPos => ScoredTile(score + 1 + getDistance(newPos, target), pos, newPos)
          )

        currentTile match {
          case ScoredTile(_, parent, currentTarget) if currentTarget == target || loopCount > LOOP_LIMIT =>
            @tailrec
            def getPath(path: Seq[(Int, Int)]): Seq[(Int, Int)] = {
              if (path.head == origin) path.tail
              else getPath((closedTiles.find(_.position == path.head).map(_.parent) ++ path).toSeq)
            }
            Some(getPath(Seq(parent)))

          case _ =>
            val newOpenPaths =
              ((options ++ tail) groupBy (_.position) map(_._2.sortBy(_.score).head)).toSeq.sortBy(_.score)

            val newClosedTiles = closedTiles :+ currentTile

            loop(newOpenPaths, newClosedTiles)
        }
      case Nil =>
        None
    }


  }

  private def getOptions(origin: (Int, Int)) = {
    Seq[(Int, Int) => (Int, Int)](
      (x, y) => (x + 1, y),
      (x, y) => (x - 1, y),
      (x, y) => (x, y + 1),
      (x, y) => (x, y - 1)
    ).map(_.tupled(origin))
      .filterNot(blockers.contains)
  }

  private def getDistance(a: (Int, Int), b: (Int, Int)): Int = (a, b) match {
    case ((x1, y1), (x2, y2)) =>
      sq(x1 - x2) + sq(y1 - y2)
  }

  private def sq(n: Int) = n * n
}

object Pathfinder {
  val PATH_LIMIT = Integer.MAX_VALUE
  val LOOP_LIMIT = 250
}

case class ScoredTile(score: Int, parent: (Int, Int), position: (Int, Int))

