package core.refactor.entity

import core.refactor.Component


/**
  * Created by rob on 17/01/17.
  */
object Implicits {
  implicit class ImplicitEntity(components: Iterable[Component]) extends Entity(components)
}

