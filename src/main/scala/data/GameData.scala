package data

import core.entity.{Creator, Entity, ID}
import data.map.MapConverter._
import roguelike.actors.Affinity.{Enemy, Player}
import roguelike.actors.attributes.Speed
import roguelike.actors.{Affinity, Spawner}
import roguelike.async.{Initiative, Temporary}
import roguelike.combat.{Attack, Health}
import roguelike.experience.{Experience, Level}
import roguelike.movement.Direction.{Direction, Up}
import roguelike.movement.lineofsight.{RememberedTiles, VisibleTiles}
import roguelike.movement.{Blocker, Facing, Position}
import roguelike.scenery.{Floor, Wall}

/**
  * Created by rob on 15/05/17.
  */
object GameData {
  val playerID = new ID

  val startingPlayer = new Entity(Seq(
    playerID,
    Affinity(Player),
    Position(3, 3),
    Facing(Up),
    Initiative(max = 10, current = 1),  //Current is 1 to enable automatic events to trigger before first player action
                                        //such as visible tiles
    Health(max = 200),

    Experience(0, 100),
    Level(1),

    Speed(7),
    VisibleTiles(Set.empty),
    RememberedTiles(Set.empty)
  ))

  val enemy = Entity(
    Affinity(Enemy),
    Initiative(max = 21),
    Health(max = 30),
    Speed(4)
  )

  def wall(position: Position) = Entity(
    Wall,
    Blocker,
    position
  )

  def floor(position: Position) = Entity(
    Floor,
    position
  )

  def enemySpawner(enemy: Entity, pos: Position) = Entity(
    pos,
    Spawner(enemy),
    Initiative(max = 200)
  )

  val walls = convert(tileMap)

  def meleeAttackEntity(creator: Entity, pos: Position, dir: Direction, affinity: Affinity) = Entity(
    pos.move(dir),
    Creator(creator),
    Attack(10),
    affinity,
    Temporary,
    Health(3))

  val DEFAULT_EXP_AMOUNT = 60
}
