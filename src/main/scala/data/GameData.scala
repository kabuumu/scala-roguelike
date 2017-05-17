package data

import core.entity.{Entity, ID}
import data.map.MapConverter._
import roguelike.actors.{Affinity, Spawner}
import roguelike.actors.Affinity.{Enemy, Player}
import roguelike.actors.attributes.Speed
import roguelike.async.Initiative
import roguelike.combat.Health
import roguelike.movement.Direction.{Left, Up}
import roguelike.movement.{Blocker, Facing, Position}

/**
  * Created by rob on 15/05/17.
  */
object GameData {
  val playerID = new ID

  val startingPlayer = Entity(
    playerID,
    Affinity(Player),
    Position(1, 1),
    Facing(Up),
    Initiative(max = 10),
    Health(max = 100),
    Speed(5)
  )

  val enemy = Entity(
    Affinity(Enemy),
    Initiative(max = 15),
    Health(max = 30),
    Speed(4)
  )

  def enemySpawner(enemy: Entity, pos: Position) = Entity(
    new ID,
    pos,
    Spawner(enemy),
    Initiative(max = 100)
  )

  val walls = convert(tileMap)
}
