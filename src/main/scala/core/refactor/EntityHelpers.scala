package core.refactor

import scala.reflect.ClassTag

/**
  * Created by rob on 17/01/17.
  */
object EntityHelpers {
  implicit class Entity(components: Iterable[Component]) {
    def +(component: Component) = components.++(Seq(component))

    def ~>[T <: Component : ClassTag](f: T => T) = update[T](f)

    def ?>[T <: Component : ClassTag](f: T => Boolean) = exists[T](f)

    def update[T <: Component : ClassTag](f: T => T) = components.map {
      case c: T => f(c)
      case c => c
    }

    def get[T <: Component : ClassTag]: Option[T] = components.collectFirst { case c: T => c }

    def exists[T <: Component : ClassTag](pred: T => Boolean): Boolean =
      components.exists {
        case c: T => pred(c)
        case _ => false
      }
  }

  object Entity {
    def apply(components: Component*) = Set[Component](components:_*)
  }
}

