package data

import core.entity.{Entity, ID}
import data.map.MapConverter._
import roguelike.actors.Affinity.{Enemy, Player}
import roguelike.actors.attributes.Speed
import roguelike.actors.{Affinity, Spawner}
import roguelike.async.Initiative
import roguelike.combat.Health
import roguelike.movement.Direction.Up
import roguelike.movement.lineofsight.{RememberedTiles, VisibleTiles}
import roguelike.movement.{Blocker, Facing, Position}
import roguelike.scenery.{Floor, Wall}

/**
  * Created by rob on 15/05/17.
  */
object GameData {
  val playerID = new ID

  val startingPlayer = Entity(
    playerID,
    Affinity(Player),
    Position(3, 3),
    Facing(Up),
    Initiative(max = 10, current = 1),  //Current is 1 to enable automatic events to trigger before first player action
                                        //such as visible tiles
    Health(max = 100),
    Speed(5),
    VisibleTiles(Set.empty),
    RememberedTiles(Set.empty)
  )

  val enemy = Entity(
    Affinity(Enemy),
    Initiative(max = 15),
    Health(max = 30),
    Speed(4)
  )

  def wall(position: Position) = Entity(
    new ID,
    Wall,
    Blocker,
    position
  )

  def floor(position: Position) = Entity(
    new ID,
    Floor,
    position
  )

  def enemySpawner(enemy: Entity, pos: Position) = Entity(
    new ID,
    pos,
    Spawner(enemy),
    Initiative(max = 100)
  )

  val walls = convert(tileMap)
}
