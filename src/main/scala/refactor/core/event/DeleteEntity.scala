package refactor.core.event

import refactor.core.entity.Entity

/**
  * Created by rob on 28/04/17.
  */
case class DeleteEntity(entity: Entity) extends Event
