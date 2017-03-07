package core.refactor.event

import core.refactor.Component

import scala.reflect.ClassTag

/**
  * Created by rob on 07/03/17.
  */
object EventBuilder {

  val default: UpdateEntity.Input => UpdateEntity.Output = {
    case (_, e) => (e, Nil)
  }
  val buildEvent: UpdateEntity = new UpdateEntity(_ => true, default)

  implicit class EventBuilder(event: UpdateEntity) {

    import event._

    def update[T <: Component : ClassTag](componentUpdate: T => T) = new UpdateEntity(
      predicate,
      f.andThen {
        case (entity, events) =>
          (entity ~> componentUpdate, events)
      }
    )

    def when[T <: Component : ClassTag](newPredicate: T => Boolean) = new UpdateEntity(
      entity => predicate(entity) && entity ?> newPredicate,
      f
    )

    def trigger(newEvents: UpdateEntity*) = new UpdateEntity(
      predicate,
      f.andThen {
        case (entity, events) =>
          (entity, events ++ newEvents)
      }
    )
  }
}
