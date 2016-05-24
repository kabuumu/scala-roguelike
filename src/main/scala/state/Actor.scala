package state

import input._

/**
  * Created by rob on 22/04/16.
  */
case class Actor(pos: Position, attributes: Map[AttributeName, Attribute], isPC: Boolean) extends Entity(pos, attributes){
//  def update(input: Input, state: GameState): Option[GameState] = {
//    if (get(INITIATIVE).current > 0) Some(state.mod(this)(_.mod(INITIATIVE)(_.current(_-1))))
//    else pos.move(input)
//      .map(move =>
//        getCollision(move, state)
//          .map(state.mod(_)(interact))
//          .getOrElse(state.mod(this)(_.copy(pos = move).mod(INITIATIVE)(_.reset)))
//      )
//  }

  def getCollision(pos: Position, state: GameState) = {
    state.getActor(pos)
      .filterNot(_ == this)
      .filter(_.isAlive)
  }

  //Combat functions
  def getAttack = 5

  def attack(target: Actor) = {
    target.getHit(getAttack)
  }

  def getHit(damage: Int): Actor = mod(HEALTH)(_.current(_-damage))

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
      .+((name,f(get(name)))
      )
  )
}

object Actor{
  def update(input: Input, state: GameState)(implicit actor: Actor): Option[GameState] = {
    if (actor.get(INITIATIVE).current > 0) Some(state.mod(actor)(_.mod(INITIATIVE)(_.current(_-1))))
    else actor.pos.move(input)
      .map(move =>
        actor.getCollision(move, state)
          .map(state.mod(_)(actor.interact))
          .getOrElse(state.mod(actor)(_.copy(pos = move).mod(INITIATIVE)(_.reset)))
      )
  }
}