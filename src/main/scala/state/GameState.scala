package state

import ai.AI
import input.{Input, NONE}
import movement.Position

/**
  * Created by rob on 22/04/16.
  */
case class GameState(actors: Seq[Actor], projectiles: Seq[Projectile], pcTimer: Int) {
  def mod(actor: Actor)(f: Actor => Actor) = copy(actors = actors.map(a => if(a == actor) f(actor) else a))

  def getActor(pos: Position) = actors.find(actor => actor.pos.x == pos.x && actor.pos.y == pos.y)

  def getHero = actors.find{case a:Actor => a.isPC}
}

object GameState{
  def update(input: Input)(state: GameState) = state.actors.indices.foldLeft(state)((state, actorID) => (state.actors(actorID), state.pcTimer, input) match{
    case (pc:Actor, _, _) if pc.isPC =>
      Actor.update(input, state)(pc)
        .getOrElse(state)
        .copy(pcTimer = pc.get(INITIATIVE).current)
    case (_, 0, NONE) => state //If it is the player's turn, but there is no input, do nothing.
    case (npc:Actor, _, _) if npc.isAlive =>
      Actor.update(AI.getCommand(npc, state), state)(npc)
        .getOrElse(state)
    case _ => state
  })

  val startingState = GameState(
    Seq(
      Actor(0,
        Position(5, 5),
        Map(
          (INITIATIVE, Attribute(10)),
          (HEALTH, Attribute(10))
        ),
        isPC = true
      ),
      Actor(0,
        Position(3, 3),
        Map(
          (INITIATIVE, Attribute(12)),
          (HEALTH, Attribute(10))
        ),
        isPC = false)
    ),
    Nil,
    10
    )
}