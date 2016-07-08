package events

import events.Event.EventReturn

/**
  * Created by rob on 29/06/16.
  */
class Event(val id: Int, f: PartialFunction[(GameState, Entity), EventReturn]) extends ((GameState, Entity) => (Entity, Seq[Event])){
  def apply(s: GameState, e: Entity): (Entity, Seq[Event]) = f.lift(s, e).getOrElse(e, Nil)
}

object Event{
  type EventReturn = (Entity, Seq[Event])

  def apply(id: Int, f: PartialFunction[(GameState, Entity), (Entity, Seq[Event])]) = new Event(id, f)
}
