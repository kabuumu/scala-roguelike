package data.map

import core.entity.Entity
import data.GameData._
import roguelike.movement.Position

/**
  * Created by rob on 21/04/17.
  */
object MapConverter {
  def convert(tileMap: Seq[String]): Seq[Entity] =
    tileMap.zipWithIndex.flatMap {
      case (row, y) =>
        row.zipWithIndex.collect {
          case ('X', x) => wall(Position(x,y))
          case ('O', x) => floor(Position(x,y))
        }
    }

  val tileMap: Seq[String] =
    Seq(
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

}
