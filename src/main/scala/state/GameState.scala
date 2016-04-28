package state

import input.{Input, NONE}

/**
  * Created by rob on 22/04/16.
  */
case class GameState(actors: Set[Actor], pcTimer: Int) {
  def update(input: Input) = actors.foldLeft(this)((state, actor) => (actor, pcTimer, input) match{
    case (pc, _, _) if pc.isPC =>
      pc.update(input, state)
        .getOrElse(state)
        .copy(pcTimer = pc.get(INITIATIVE).current)
    case (_, 0, NONE) => state //If it is the player's turn, but there is no input, do nothing.
    case (npc, _, _) => state
  })

  def mod(actor: Actor)(f: Actor => Actor) = copy(actors = actors.-(actor).+(f(actor)))

  def getActor(pos: Position) = actors.find(actor => actor.pos.x == pos.x && actor.pos.y == pos.y)
}

//object GameState{
//  def actors = Lens.lensu[GameState, Set[Actor]](
//    (a, value) => a.copy(actors = value),
//    _.actors
//  )
//
//  def actor(actor:Actor) = Lens.lensu[Set[Actor], Actor](
//    (a, value) => a.-(actor).+(value),
//    {actor}
//  )
//}