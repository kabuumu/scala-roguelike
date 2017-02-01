package core

import core.Event.{EventReturn, F, In}

/**
  * Created by rob on 29/06/16.
  */
class Event(f: F) extends (F) {
  def apply(e: In): EventReturn = f.apply(e)

  override def isDefinedAt(x: In): Boolean = f.isDefinedAt(x)
}

object Event {
  type In = (GameState, Entity)
  type F = PartialFunction[In, EventReturn]
  type EventReturn = (Iterable[Entity], Iterable[Event])

  def get(f: PartialFunction[In, Event])= Event{
    case (s, e) =>
      (Seq(e), f.lift(s, e))
    }

  def apply(f: F) = new Event(f)

  def delete(id: String) = Event{
    case (s, e) if e.id == id => (Nil, Nil)
  }
}
