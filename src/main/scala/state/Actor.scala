package state

import combat.{Attacker, Defender}
import input._
import movement.Direction.Direction
import movement.{Direction, Mover, Position}

case class Actor(id: Int, pos: Position, attributes: Map[AttributeName, Attribute], isPC: Boolean) extends Mover[Actor] {
  def getCollision(pos: Position, state: GameState) = {
    state.getActor(pos)
      .filterNot(_ == this)
      .filter { case a: Actor => a.isAlive }
  }

  //Combat functions
  def getAttack = 5

  def attack(target: Actor) = {
    target.getHit(getAttack)
  }

  def getHit(damage: Int): Actor = mod(HEALTH)(_.current(_ - damage))

  def interact = (target: Actor) => target match {
    case a if a.isAlive => attack(target)
    case _ => target
  }

  val isAlive = get(HEALTH).current > 0

  /*
   * Attribute Code
   */
  def get(name: AttributeName) = attributes.getOrElse(name, Attribute(0))

  def mod(name: AttributeName)(f: Attribute => Attribute) = copy(
    attributes = attributes
      .+((name, f(get(name))))
  )

  override def pos(f: (Position) => Position) = copy(pos = f(pos))
}

object Actor {

  import movement.Mover._
  import movement.Position._

  def update(input: Input, state: GameState)(actor: Actor): Option[GameState] = {
    if (actor.get(INITIATIVE).current > 0) Some(state.mod(actor)(_.mod(INITIATIVE)(_.current(_ - 1))))
    else {
      Some(state)
    }
  }
}