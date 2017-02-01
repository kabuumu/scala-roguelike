package core.refactor

import java.util.UUID

/**
  * Created by rob on 17/01/17.
  */
class Entity private()

object Entity {
  type EntityType = Entity

  def apply(components: ((EntityType) => Component)*): Iterable[Component] = {
    val id = new Entity()

    components.map(_.apply(id))
  }
}
