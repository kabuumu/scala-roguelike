package refactor.core.event

import refactor.core.entity.Entity

/**
  * Created by rob on 07/03/17.
  */
case class CreateEntity(entity: Entity) extends Event
