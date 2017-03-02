package rogueLike.data

import core.Entity._
import rogueLike.actors._
import rogueLike.async.Initiative
import rogueLike.combat.{Attack, Projectile}
import rogueLike.health.Health
import rogueLike.movement.{Facing, _}
import rogueLike.output.Sprite

import scalafx.scene.paint.Color

object Entities {
  //TODO - Replace this with JSON
  def playerProjectile = createEntity(
    Projectile(_),
    Attack(_, 10),
    Initiative(2, _),
    Position(0, 0, _),
    Facing(Direction.Up, _),
    Blocker(_),
    Sprite(Color.Red, _)
  )

  def player(playerID: String, initiative: Int, x: Int, y: Int) = createEntity(
    Player(_),
    Sprite(Color.Blue, _),
    Health(_, 100, 100),
    Initiative(initiative, _),
    Position(x, y, _),
    Facing(Direction.Down, _),
    Blocker(_)
  )(playerID)

  def enemy(initiative: Int, x: Int, y: Int) = createEntity(
    Enemy(_),
    Sprite(Color.Green, _),
    Health(_, 20, 20),
    Initiative(initiative, _),
    Position(x, y, _),
    Facing(Direction.Down, _),
    Blocker(_)
  )

  def wall(x: Int, y: Int) = createEntity(
    Sprite(Color.Grey, _),
    Position(x, y, _),
    Facing(Direction.Down, _),
    Blocker(_)
  )
}
