package core

import core.Event.{EventReturn, F, In}

/**
  * Created by rob on 29/06/16.
  */
class Event(f: F) extends (F) {
  def apply(e: Entity): EventReturn = f.apply(e)

  override def isDefinedAt(x: In): Boolean = f.isDefinedAt(x)
}

object Event {
  type In = (Entity)
  type F = PartialFunction[In, EventReturn]
  type EventReturn = (Iterable[Entity], Iterable[Event])

  def get(f: PartialFunction[Entity, Event])= Event{
    case e =>
      (Seq(e), f.lift(e))
    }

  def apply(f: F) = new Event(f)

  def delete(id: String) = Event{
    case e if e.id == id => (Nil, Nil)
  }
}
