package data.map

import core.entity.{Entity, ID}
import data.GameData._
import roguelike.movement.Position

/**
  * Created by rob on 21/04/17.
  */
object MapConverter {
  val tileMap: Seq[String] =
    Seq(
      "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
      "XXXOOOOOOOXXXXXXXXXXXXXXXXOXOOOOOOOX",
      "XXXOOOOOOOXXXOOOXXOOOOOOOXOXOOOOOOOX",
      "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
      "XOXOOOOOOOXXXOOOXXOOOOOOOXOXOOOOOOOX",
      "XOXOOOOOOOXXXXOXXXOOOOOOOXOXOOOOOOOX",
      "XOXXXXOXXXXXXXOXXXOOOOOOOXOXOOOOOOOX",
      "XOXXXXOXXXXXXXOXXXXXXOOXXXOXXXXOXXXX",
      "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
      "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
      "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
      "XOXXXXOXXXXXXXOXXXXXXOOXXXXXXXXOX",
      "XOXOOOOOOOOOOOOXOOOOOOOOOOXXXXXOX",
      "XOXOXXXXOXXXXXOXOOOOOOOOOOXXXXXOX",
      "XOXOXOOOOOOOOXOOOOOOOOOOOOXXXXXOX",
      "XOXOXOXXXXXXOOOOOOOOOOOOOOOOOOOOX",
      "XOXOXOOOOOOXOXOOOOOOOOOOOOXXXXXXX",
      "XOXOXXXXXXXXOXOOOOOOOOOOOOX",
      "XOOOOOOOOOOOOXOOOOOOOOOOOOX",
      "XXXXXXXXXXXXXXXXXXXXXXXXXXX"
    )
  val newMap: Seq[String] =
    Seq(
      //123456789012345678901234567890123456789
      "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
      "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
      "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
      "XOOXXXXOOXXXOOXXXXXOOXXXXXXOOXXXOOXXXOOX",
      "XOOXOOOOOOOXOOXOOOOOOOOOOOXOOXOOOOOOXOOX",
      "XOOXOOOOOOOXOOXOOOOOOOOOOOXOOXOOOOOOXOOX",
      "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
      "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
      "XOOXOOOOOOOXOOXOOOOOOOOOOOXOOXOOOOOOXOOX",
      "XOOXOOOOOOOXOOXOOOOOOOOOOOXOOXOOOOOOXOOX",
      "XOOXXXXOOXXXOOXXXXXOOXXXXXXXXXXXOOXXXOOX",
      "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
      "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
      "XOOXXXXOOXXXOOXXXXXOOXXXXXXXXXXXOOXXXOOX",
      "XOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOX",
      "XOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOX",
      "XOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOX",
      "XOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOX",
      "XOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOX",
      "XOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOX",
      "XOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOX",
      "XOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOX",
      "XOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOX",
      "XOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOX",
      "XOOX",
      "XOOX",
      "XOOX",
      "XOOX",
      "XOOX",
      "XOOX",
      "XOOX",
      "XOOX",
      "XOOX",
      "XOOX",
      "XOOX",
      "XOOX",
      "XOOX",
      "XOOX",
      "XOOX",
      ""
    )
  val arenaMap: Seq[String] = Seq(
    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
    "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
    "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
    "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
    "XOOOOOOXXXXXXXXXOOOOOOOOXXXXXXXXXOOOOOOX",
    "XOOOOOOXOOOOOOOOOOOOOOOOOOOOOOOOXOOOOOOX",
    "XOOOOOOXOOOOOOOOOOOOOOOOOOOOOOOOXOOOOOOX",
    "XOOOXXXXOOOOOOOOOOXXXXOOOOOOOOOOXXXXOOOX",
    "XOOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOOX",
    "XOOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOOX",
    "XOOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOOX",
    "XOOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOOX",
    "XOOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOOX",
    "XOOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOOX",
    "XOOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOOX",
    "XOOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOOX",
    "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
    "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
    "XOOOOOOXOOOOOOOOOOOOOOOOOOOOOOOOXOOOOOOX",
    "XOOOOOOXOOOOOOOOOOOOOOOOOOOOOOOOXOOOOOOX",
    "XOOOOOOXOOOOOOOOOOOOOOOOOOOOOOOOXOOOOOOX",
    "XOOOOOOXOOOOOOOOOOOOOOOOOOOOOOOOXOOOOOOX",
    "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
    "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
    "XOOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOOX",
    "XOOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOOX",
    "XOOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOOX",
    "XOOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOOX",
    "XOOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOOX",
    "XOOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOOX",
    "XOOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOOX",
    "XOOOXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOXOOOX",
    "XOOOXXXXOOOOOOOOOOXXXXOOOOOOOOOOXXXXOOOX",
    "XOOOOOOXOOOOOOOOOOOOOOOOOOOOOOOOXOOOOOOX",
    "XOOOOOOXOOOOOOOOOOOOOOOOOOOOOOOOXOOOOOOX",
    "XOOOOOOXXXXXXXXXOOOOOOOOXXXXXXXXXOOOOOOX",
    "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
    "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
    "XOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOX",
    "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
  )
  val dungeonMap: Seq[String] = Seq(
    "                                                            ",
    "                                                            ",
    "                                                            ",
    "             XXXXXXXXXX                                     ",
    "             X        X                                     ",
    "             X        X                                     ",
    "             X        X                                     ",
    "             X        X                                     ",
    "             X  O  O  X                                     ",
    "             X        X                                     ",
    "             X        X                                     ",
    "             XXXT  TXXX                                     ",
    "               X    X                                       ",
    "               X    X             XXXXXXXXXX                ",
    "               X    X             X        X                ",
    "               X    X             X        X                ",
    "               X    X             X   O    X                ",
    "      XXXXXXXXXXT  TXXXXXXXXX     X G    G X                ",
    "      X                     X     X        X                ",
    "      X                     X     X  T  T  X                ",
    "      X  XXXXXXXXXXXXXXXXX  X     XXXX  XXXX                ",
    "      X  X               X  X        X  X                   ",
    "    XXX  XXX             X  X        X  X                   ",
    "    X      X  XXXXX XXXXXX  XXXXXX   X  X                   ",
    "    X G   TXXXX   X X    T  T    XXXXX  X                   ",
    "    X             X X                   X                   ",
    "    X G   TXXXX   X X    O  O           X                   ",
    "    X      X  XX XX X            XXXXXXXX                   ",
    "    X T  T X   X X  X            X                          ",
    "    XXXXXXXX XXX XXXX            X                          ",
    "             X      X            X                          ",
    "             X  G  TX            X                          ",
    "             X                   X                          ",
    "             X                   X                          ",
    "             X  G  TX            X                          ",
    "             X      X    T  T    X                          ",
    "             XXXXXXXXXXXXX  XXXXXX                          ",
    "                         XP X                               ",
    "                         XXXX                               "
  )

  def convert(tileMap: Seq[String]): Seq[Entity] =
    tileMap.zipWithIndex.flatMap {
      case (row, y) =>
        row.zipWithIndex.collect {
          case (tile, x) =>
            (tile match {
              case 'X' => wall _
              case ' ' => floor _
              case 'T' => torch _
              case 'P' => startingPlayer _
              case 'G' => goblin + _
              case 'O' => orc + _
            }) apply Position(x, y)
        }
    }
}
