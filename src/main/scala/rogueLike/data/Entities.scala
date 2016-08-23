package rogueLike.data

import core.Entity._
import rogueLike.actors._
import rogueLike.async.Initiative
import rogueLike.movement.{Direction, Position}
import rogueLike.output.Sprite

import scalafx.scene.paint.Color

object Entities {
  def player(playerID: String, initiative: Int, x: Int, y: Int) = createEntity(
    Player(_),
    Sprite(Color.Blue, _),
    Initiative(initiative, _),
    Position(x, y, Direction.Down, blocker = false, _)
  )(playerID)

  def enemy(initiative: Int, x: Int, y: Int) = createEntity(
    Enemy(_),
    Sprite(Color.Green, _),
    Initiative(initiative, _),
    Position(x, y, Direction.Left, blocker = false, _)
  )

  def wall(x: Int, y: Int) = createEntity(
    Sprite(Color.Black, _),
    Position(x, y, Direction.Down, blocker = true, _)
  )
}
