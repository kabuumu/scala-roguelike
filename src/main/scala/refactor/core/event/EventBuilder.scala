package refactor.core.event

import refactor.core.entity.{Component, Entity, ID}

import scala.reflect.ClassTag

/**
  * Created by rob on 07/03/17.
  */
object EventBuilder {
  val default: UpdateEntity.Input => UpdateEntity.Output = {
    case (_, e) => (e, Nil)
  }
  val event: UpdateEntity = new UpdateEntity(_ => true, default)

  implicit class EventBuilder(event: UpdateEntity) {
    import event._

    def update[T <: Component : ClassTag](componentUpdate: T => T): UpdateEntity = new UpdateEntity(
      predicate,
      f.andThen {
        case (entity, events) =>
          (entity ~> componentUpdate, events)
      }
    )

    def when[T <: Component : ClassTag](newPredicate: T => Boolean): UpdateEntity = new UpdateEntity(
      entity => predicate(entity) && entity ?> newPredicate,
      f
    )

    def when[T <: Component : ClassTag] = new ComponentQuery[T]

    def when(newPredicate: Entity => Boolean) = new UpdateEntity(
      entity => predicate(entity) && newPredicate(entity),
      f
    )

    def whenNot(newPredicate: Entity => Boolean) = when(!newPredicate(_))

    def trigger(newEvents: Event*): UpdateEntity = new UpdateEntity(
      predicate,
      f.andThen {
        case (entity, events) =>
          (entity, events ++ newEvents)
      }
    )

    def trigger(newEvent: Entity => Event): UpdateEntity = new UpdateEntity(
      predicate,
      f.andThen {
        case (entity, events) =>
          (entity, events :+ newEvent(entity))
      }
    )

    def trigger(optEvent: Option[Entity => Event]): UpdateEntity = optEvent match{
      case Some(e) => trigger(e)
      case _ => event
    }

    class ComponentQuery[T <: Component : ClassTag] {
      def matches(e: Entity): UpdateEntity =
        when[T](e.get[T].contains[T](_)) when(e != _)
      def eq(component: T): UpdateEntity = when[T]((_: T) == component)
      def exists(predicate: T => Boolean): UpdateEntity = when[T](predicate)
    }
  }

  def matches[T <: Component : ClassTag](component: T): Entity => Boolean = _.exists[T](_ == component)
  def matches[T <: Component : ClassTag](optComponent: Option[T]): Entity => Boolean = optComponent match {
    case None => _ => false
    case Some(c) => matches[T](c)
  }
  def matches(entity: Entity): Entity => Boolean = matches(entity[ID])
}
