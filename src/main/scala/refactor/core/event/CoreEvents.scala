package refactor.core.event

import refactor.core.entity.Entity

/**
  * Created by rob on 05/04/17.
  */
object CoreEvents {
  def resetEntity(baseEntity: Entity) = new Update(predicate = _ => true,
    f = {
      case(state, entity) =>
        (baseEntity, Nil)
    }
  )
}
