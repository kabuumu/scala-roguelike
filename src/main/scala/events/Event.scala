package events

import events.Event.EventReturn

/**
  * Created by rob on 29/06/16.
  */
class Event(f: PartialFunction[Entity, EventReturn]) extends (Entity => (Entity, Seq[Event])){
  def apply(e: Entity): (Entity, Seq[Event]) = f.apply(e)

  def isDefinedAt(e: Entity): Boolean = f.isDefinedAt(e)
}

object Event{
  type EventReturn = (Entity, Seq[Event])

  def apply(f: PartialFunction[Entity, EventReturn]) = new Event(f)
}
