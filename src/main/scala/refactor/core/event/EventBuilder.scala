package refactor.core.event

import refactor.core.entity.{Component, Entity, ID}

import scala.reflect.ClassTag

/**
  * Created by rob on 07/03/17.
  */
object EventBuilder {
  val default: Update.Input => Update.Output = {
    case (_, e) => (e, Nil)
  }
  val event: Update = new Update(_ => true, default)

  implicit class EventBuilder(event: Update) {
    import event._

    def update[T <: Component : ClassTag](componentUpdate: T => T): Update = updateEntity(_ ~> componentUpdate)

    def updateEntity(entityUpdate: Entity => Entity): Update = new Update(
      predicate,
      f.andThen { case (entity, events) => (entityUpdate(entity), events) }
    )

    def when[T <: Component : ClassTag](newPredicate: T => Boolean): Update = new Update(
      entity => predicate(entity) && entity ?> newPredicate,
      f
    )

    def when[T <: Component : ClassTag] = new ComponentQuery[T]

    def when(newPredicate: Entity => Boolean) = new Update(
      entity => predicate(entity) && newPredicate(entity),
      f
    )

    def whenNot(newPredicate: Entity => Boolean) = when(!newPredicate(_))

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

    def trigger(optEvent: Option[Entity => Event]): Update = optEvent match {
      case Some(e) => trigger(e)
      case _ => event
    }

    class ComponentQuery[T <: Component : ClassTag] {
      def matches(e: Entity): Update =
        when[T](e.get[T].contains[T](_)) when(e != _)
      def eq(component: T): Update = when[T]((_: T) == component)
      def exists(predicate: T => Boolean): Update = when[T](predicate)
    }
  }

  def matches[T <: Component : ClassTag](component: T): Entity => Boolean = _.exists[T](_ == component)
  def matches[T <: Component : ClassTag](optComponent: Option[T]): Entity => Boolean = optComponent match {
    case None => _ => false
    case Some(c) => matches[T](c)
  }

  def matches(entity: Entity): Entity => Boolean = matches(entity[ID])
}
