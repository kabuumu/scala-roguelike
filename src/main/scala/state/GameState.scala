package state

import ai.AI
import input.{Input, NONE}

/**
  * Created by rob on 22/04/16.
  */
case class GameState(actors: Seq[Actor], pcTimer: Int) {

  def update(input: Input) = actors.indices.foldLeft(this)((state, actorID) => (state.actors(actorID), pcTimer, input) match{
    case (pc, _, _) if pc.isPC =>
      pc.update(input, state)
        .getOrElse(state)
        .copy(pcTimer = pc.get(INITIATIVE).current)
    case (_, 0, NONE) => state //If it is the player's turn, but there is no input, do nothing.
    case (npc, _, _) if npc.isAlive =>
      npc.update(AI.getCommand(npc, state), state)
        .getOrElse(state)
    case _ => state
  })

  def mod(actor: Actor)(f: Actor => Actor) = copy(actors = actors.map(a => if(a == actor) f(actor) else a))

  def getActor(pos: Position) = actors.find(actor => actor.pos.x == pos.x && actor.pos.y == pos.y)

  def getHero = actors.find(_.isPC)
}

object GameState{
  val startingState = GameState(
    Seq(
      Actor(
        Position(5, 5),
        Map(
          (INITIATIVE, Attribute(10)),
          (HEALTH, Attribute(10))
        ),
        isPC = true
      ),
      Actor(
        Position(3, 3),
        Map(
          (INITIATIVE, Attribute(7)),
          (HEALTH, Attribute(10))
        ),
        isPC = false)
    ),
    10
    )
}