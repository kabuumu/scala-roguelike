package data

import core.entity.{Creator, Entity, ID}
import core.event.EventComponent._
import data.map.MapConverter._
import roguelike.actors.Affinity.{Enemy, Player}
import roguelike.actors.attributes.Speed
import roguelike.actors.{Affinity, Spawner}
import roguelike.async.{Initiative, Temporary}
import roguelike.combat.{Attack, Health, Weapon}
import roguelike.experience.{Experience, Level}
import roguelike.light.{LightBlocker, LightCaster, LightMap}
import roguelike.movement.Direction.Up
import roguelike.movement._
import roguelike.movement.lineofsight.{RememberedTiles, VisibleTiles}
import roguelike.scenery.{Floor, Wall}

/**
  * Created by rob on 15/05/17.
  */
object GameData {
  val playerID = new ID

  def startingPlayer(pos: Position) = new Entity(Seq(
    playerID,
    Affinity(Player),
    pos,
    Facing(Up),
    Initiative(max = 10, current = 2), //Current is 2 to enable automatic events to trigger before first player action
    //such as visible tiles
    Health(max = 200),

    Experience(0, 100),
    Level(1),
    Speed(6),

    VisibleTiles(Set.empty),
    RememberedTiles(Set.empty),
    LightMap(),

    LightBlocker(0.5),

    Weapon(range = 100, projectileSpeed = 50, attackSpeed = 150)
  ))

  def orc = Entity(
    Affinity(Enemy),
    Initiative(max = 21),
    Health(max = 30),
    Speed(4),
    Blocker,
    LightBlocker(0.5),
    Weapon(range = 1, projectileSpeed = 0, attackSpeed = 500)
  )

  def goblin = Entity(
    Affinity(Enemy),
    Initiative(max = 7),
    Health(max = 7),
    Speed(7),
    Blocker,
    LightBlocker(0.5),
    Weapon(range = 50, projectileSpeed = 40, attackSpeed = 1000)
  )

  val map = convert(dungeonMap)
  val DEFAULT_EXP_AMOUNT = 60

  def wall(position: Position) = Entity(
    Wall,
    Blocker,
    position,
    LightBlocker(0)
  )

  def floor(position: Position) = Entity(
    Floor,
    position
  )

  def enemySpawner(enemy: Entity, pos: Position) = Entity(
    pos,
    Spawner(enemy),
    Initiative(max = 100)
  )

  def createProjectile(creator: Entity, path: Iterator[Position], affinity: Affinity, damage: Int, attackSpeed: Int): Entity =
    Entity(
      path.next(),
      affinity,
      Creator(creator),
      Attack(damage),
      Velocity(attackSpeed, path),
      Temporary,
      LightBlocker(0.8),
      Health(30)
    )

  def torch(pos: Position): Entity =
    Entity(
      pos,
      LightCaster(.8)
    )

  val startingData = map
}
