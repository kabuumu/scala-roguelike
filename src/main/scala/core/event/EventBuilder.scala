package core.event

import core.entity.{Component, Entity, ID}

import scala.reflect.ClassTag

/**
  * Created by rob on 07/03/17.
  */
object EventBuilder {
  val defaultFunction: Update.Input => Update.Output = {
    case (_, e) => (e, Nil)
  }
  val event: Update = new Update(_ => true, defaultFunction)

  def matches(entity: Entity): Entity => Boolean = matches(entity[ID])

  def matches[T <: Component : ClassTag](optComponent: Option[T]): Entity => Boolean = optComponent match {
    case None => _ => false
    case Some(c) => matches[T](c)
  }

  def matches[T <: Component : ClassTag](component: T): Entity => Boolean = _.exists[T](_ == component)

  def not[T](predicate: T => Boolean): T => Boolean = predicate.andThen(!_)

  implicit def convertUpdate[T <: Component : ClassTag](op: T => T): Entity => Entity = _ update op

  implicit def convertQuery[T <: Component : ClassTag](predicate: T => Boolean): Entity => Boolean = _ exists predicate

  implicit class EventBuilder(event: Update) {

    import event._

    def update(updates: Iterable[Entity => Entity]): Update =
      updates.foldLeft(event)(_.update(_))

    def update(entityUpdate: Entity => Entity): Update = new Update(
      predicate,
      f.andThen { case (entity, events) => (entityUpdate(entity), events) }
    )

    def when(newPredicate: Entity => Boolean) = new Update(
      entity => predicate(entity) && newPredicate(entity),
      f
    )

    def trigger(newEvents: Event*): Update = new Update(
      predicate,
      f.andThen {
        case (entity, events) =>
          (entity, events ++ newEvents)
      }
    )

    def trigger(newEvent: Entity => Event): Update = new Update(
      predicate,
      f.andThen {
        case (entity, events) =>
          (entity, events :+ newEvent(entity))
      }
    )

    def trigger(newEvents: Entity => Iterable[Event])(implicit dummyImplicit: DummyImplicit): Update = new Update(
      predicate,
      f.andThen {
        case (entity, events) =>
          (entity, events ++ newEvents(entity))
      }
    )
  }

}
