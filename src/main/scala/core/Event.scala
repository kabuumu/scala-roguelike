package core

import core.Event.EventReturn

import scala.util.Try

/**
  * Created by rob on 29/06/16.
  */
class Event(f: Entity => EventReturn) extends (Entity => EventReturn) {
  def apply(e: Entity): EventReturn = Try(f.apply(e)).getOrElse(Seq(e), Nil)

  def filter(pred: Entity => Boolean) = Event { case e if pred(e) => (Seq(e), Seq(this)) }
}

object Event {
  type EventReturn = (Iterable[Entity], Iterable[Event])

  def filter(pred: Entity => Boolean, events: Event*) = Event {
    case e if pred(e) => (Seq(e), events)
  }

  // def apply(f: PartialFunction[Entity, Entity]) = new Event(PartialFunction(f.andThen(e => (Seq(e), Seq[Event]()))))

  def apply(f: Entity => EventReturn) = new Event(f)

  def filter(pred: Entity => Boolean)(event: Event) = Event {
    case e if pred(e) => (Seq(e), Seq(event))
  }
}
