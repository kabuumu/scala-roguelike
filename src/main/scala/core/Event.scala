package core

import core.Event.EventReturn

/**
  * Created by rob on 29/06/16.
  */
class Event(f: PartialFunction[Entity, EventReturn]) extends (Entity => EventReturn){
  def apply(e: Entity): EventReturn = f.apply(e)

  def isDefinedAt(e: Entity): Boolean = f.isDefinedAt(e)
}

object Event{
  type EventReturn = (Iterable[Entity], Iterable[Event])

  def apply(f: PartialFunction[Entity, EventReturn]) = new Event(f)
}
