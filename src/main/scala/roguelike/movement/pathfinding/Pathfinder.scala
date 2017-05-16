package roguelike.movement.pathfinding

import roguelike.movement.pathfinding.Pathfinder.PATH_LIMIT

import scala.annotation.tailrec

/**
  * Created by rob on 15/05/17.
  */

class Pathfinder(origin: (Int, Int), target: (Int, Int), blockers: Set[(Int, Int)]) {
  val path: Seq[(Int, Int)] = loop(getOptions(origin).sortBy(getDistance(target, _)) map (Seq(_)))
  val getNext: (Int, Int) = path.head

  @tailrec
  private def loop(openPaths: Seq[Seq[(Int, Int)]],
                   closedTiles: Set[(Int, Int)] = Set.empty,
                   successPaths: Seq[Seq[(Int, Int)]] = Nil,
                   pathLimit: Int = PATH_LIMIT
                  ): Seq[(Int, Int)] = {
    val newSuccessPaths = successPaths ++ openPaths.filter(_.contains(target)) sortBy(_.size)
    val newPathLimit = newSuccessPaths.headOption.fold(pathLimit)(_.size)

    openPaths filter(_.size < newPathLimit) match {
      case head :: tail =>
        val nextSteps: Seq[Seq[(Int, Int)]] = getOptions(head.last).sortBy(getDistance(target, _))
          .filterNot(closedTiles.contains)
          .map(head :+ _)

        val newPaths = nextSteps ++ openPaths.tail

        loop(
          openPaths = newPaths.diff(newSuccessPaths),
          closedTiles = closedTiles ++ newPaths.flatten.toSet,
          successPaths = newSuccessPaths,
          pathLimit = newPathLimit
        )
      case Nil =>
        newSuccessPaths.head
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
  val PATH_LIMIT = 23
}

