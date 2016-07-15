package events

import events.Event.EventReturn

/**
  * Created by rob on 29/06/16.
  */
class Event(val id: Int, f: PartialFunction[Entity, EventReturn]) extends (Entity => (Entity, Seq[Event])){
  def apply(e: Entity): (Entity, Seq[Event]) = f.apply(e)
  def isDefinedAt(e: Entity): Boolean = f.isDefinedAt(e)
}

object Event{
  type EventReturn = (Entity, Seq[Event])

  def apply(id: Int, f: PartialFunction[Entity, (Entity, Seq[Event])]) = new Event(id, f)
}
