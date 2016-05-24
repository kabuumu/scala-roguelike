package state

import input.Input

import scalaz.State

/**
  * Created by rob on 05/05/16.
  */
abstract class Entity (val pos: Position, val attributes: Map[AttributeName, Attribute])

object Entity{
  def update(state: GameState, input: Input = input.NONE) = (e : Entity) => {
    if (e.get(INITIATIVE).current > 0) Some(state.mod(e)(_.mod(INITIATIVE)(_.current(_-1))))
  }

  def get(name: AttributeName) = attributes.getOrElse(name, Attribute(0))

  def mod(name: AttributeName)(f: Attribute => Attribute) = (e: Entity) => new Entity(e.pos, e.attributes
      .+((name,f(get(name)))
      )
  )
}

