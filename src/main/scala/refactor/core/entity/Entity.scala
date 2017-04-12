package refactor.core.entity

import scala.reflect.ClassTag

/**
  * Created by rob on 03/03/17.
  */
case class Entity(components: Iterable[Component]) extends Iterable[Component] {
  def apply[T <: Component : ClassTag] = get[T]

  def +(component: Component) = Entity(components.++(Seq(component)))

  def ~>[T <: Component : ClassTag](f: T => T) = update[T](f)

  def ?>[T <: Component : ClassTag](f: T => Boolean) = exists[T](f)

  def update[T <: Component : ClassTag](f: T => T) = Entity(components.map {
    case c: T => f(c)
    case c => c
  })

  def get[T <: Component : ClassTag]: Option[T] = components.collectFirst { case c: T => c }

  def get[T <: Component : ClassTag, V](f: T => V): Option[V] = get[T].map(f)

  def exists[T <: Component : ClassTag](pred: T => Boolean): Boolean =
    components.exists {
      case c: T => pred(c)
      case _ => false
    }

  override def iterator: Iterator[Component] = components.iterator
}

object Entity {
  def apply(components: Component*) = new Entity(components)
}
