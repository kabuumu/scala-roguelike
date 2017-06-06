package core.event

import core.entity.{Component, Entity}

/**
  * Created by rob on 09/05/17.
  */
trait EventComponent extends Component {
  val entityEvents: Entity => Iterable[Event]
}

object EventComponent {

  import core.event.EventBuilder._

  val getEntityEvent: Entity => Iterable[Event] = entity => entity.components.collect {
    case EventComponent(events) => events(entity)
  }.flatten

  val triggerEntityEvents = event when (_.has[EventComponent]) trigger getEntityEvent

  def unapply(component: EventComponent): Option[Entity => Iterable[Event]] = Some(component.entityEvents)
}
