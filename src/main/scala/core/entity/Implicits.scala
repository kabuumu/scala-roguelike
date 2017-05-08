package core.entity


/**
  * Created by rob on 17/01/17.
  */
object Implicits {
  implicit class ImplicitEntity(components: Iterable[Component]) extends Entity(components)
}

