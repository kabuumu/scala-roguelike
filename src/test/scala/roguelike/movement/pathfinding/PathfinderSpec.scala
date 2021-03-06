package roguelike.movement.pathfinding

import org.scalatest.{Matchers, WordSpec}

/**
  * Created by rob on 15/05/17.
  */
class PathfinderSpec extends WordSpec with Matchers {
  def convert(tileMap: String*): Set[(Int, Int)] =
    tileMap.zipWithIndex.flatMap {
      case (row, y) =>
        row.zipWithIndex.collect {
          case ('X', x) => (x, y)
        }
    }.toSet

  "getNext" should {
    "return (1, 0) when the target is (1, 0) and the origin is (0, 0) with no blockers" in {
      new Pathfinder((0, 0), (1, 0), Set.empty).getNext should contain (1 -> 0)
    }

    "return (0, 1) when the target is (1, 0) and the origin is (0, 0) with no blockers" in {
      new Pathfinder((0, 0), (0, 1), Set.empty).getNext should contain (0 -> 1)
    }

    "return (-1, 0) when the target is (-1, 0) and the origin (0, 0) with no blockers" in {
      new Pathfinder((0, 0), (-1, 0), Set.empty).getNext should contain (-1 -> 0)
    }

    "return (0, -1) when the target is (0, -1) and the origin is (0, 0) with no blockers" in {
      new Pathfinder((0, 0), (0, -1), Set.empty).getNext should contain (0 -> -1)
    }

    "return (1, 0) when the target is (2, 0) and the origin is (0, 0) with no blockers" in {
      new Pathfinder((0, 0), (2, 0), Set.empty).getNext should contain (1 -> 0)
    }

    "return (0, 1) when the target is (0, 2) and the origin is (0, 0) with no blockers" in {
      new Pathfinder((0, 0), (0, 2), Set.empty).getNext should contain (0 -> 1)
    }

    "return (0, 1) when the target is (2, 0) and the origin is (0, 0) and (1, 0) is blocked" in {
      new Pathfinder((0, 0), (2, 0), Set(1 -> 0)).getNext should contain (0 -> 1)
    }
  }

  "path" should {
    "get the shortest path when the direct route is blocked" in {
      val blockers = Set(1 -> 0, 0 -> -1, 0 -> 1)
      val pathfinder = new Pathfinder(0 -> 0, 2 -> 0, blockers)
      pathfinder.path map(_.length) should contain(8)
    }

    "get the shortest path through a maze" in {
      val blockers = convert(
        "OOOOOO",
        "XXXXXO",
        "XOOOXO",
        "XOXXXO",
        "XOOXOO",
        "XXOOOX",
        "XXXXXX"
      )

      val startPoint = 3 -> 2
      val target = 0 -> 0

      val pathfinder = new Pathfinder(startPoint, target, blockers)
      pathfinder.path map(_.length) should contain(19)
      pathfinder.loopCount should be < 200
    }

    "get the shortest path when it is not the nearest" in {
      val blockers = convert(
        "XXXXXXXXXXXXXXXXXXXXXXXXX",
        "XOOOOOOOXXXXXXXXXXXXXXXXX",
        "XOOOOOOOXXXOOOXXOOOOOOOXX",
        "XOOOOOOOOOOOOOOOOOOOOOOXX",
        "XOOOOOOOXXXOOOXXOOOOOOOXX",
        "XOOOOOOOXXXXOXXXOOOOOOOXX",
        "XXXXXXXXXXXXOXXXOOOOOOOXX",
        "XXXXXXXXXXXXOXXXXXXOOXXXX",
        "XOOOOOOOOOOOOOOOOOOOOOOOX",
        "XOOOOOOOOOOOOOOOOOOOOOOOX",
        "XOOOOOOOOOOOOOOOOOOOOOOOX",
        "XXXXXXXXXXXXOXXXXXXXXXXXX",
        "XOOOOOOOOOOOOXOOOOOOOOOOX",
        "XOXXXXXXXXXXOXOOOOOOOOOOX",
        "XOXOOOOOOOOXOOOOOOOOOOOOX",
        "XOXOXXXXXXOXOOOOOOOOOOOOX",
        "XOXOOOOOOXOXOOOOOOOOOOOOX",
        "XOXXXXXXXXOXOOOOOOOOOOOOX",
        "XOOOOOOOOOOXOOOOOOOOOOOOX",
        "XXXXXXXXXXXXXXXXXXXXXXXXX"
      )

      val startPoint = 10 -> 8
      val target = 2 -> 1

      val pathfinder = new Pathfinder(startPoint, target, blockers)
      pathfinder.path map(_.length) should contain(19)
      pathfinder.loopCount should be <= 300
    }
  }
}
