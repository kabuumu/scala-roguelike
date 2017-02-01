package core.util

import core.Entity

import scala.reflect.ClassTag

/**
  * Created by rob on 21/12/16.
  */
object EntityHelpers {

  implicit class ComponentFinder(container: Iterable[Entity]) {
    def get[T <: Entity : ClassTag](condition: Entity => Boolean): Iterable[T] = {
      container.filter(condition)
        .flatMap(entity =>
          container.collect { case e: T if e.id == entity.id => e }
        )
    }

    def findEntity[T <: Entity : ClassTag](filter: T => Boolean = (_:T) => true) = new EntityCursor[T](container, filter)
  }

  class EntityCursor[T <: Entity : ClassTag](container: Iterable[Entity], filter: T => Boolean) extends Iterable[T] {
    def where[T2 <: Entity : ClassTag](f: T2 => Boolean = (_:T2) => true) = {
      container.collect { case e: T2 if f(e) => e }
        .flatMap(entity =>
          container.collect { case e: T if e.id == entity.id && filter(e) => e }
        )
    }

    private lazy val get = container.collect {case e: T if filter(e) => e}

    override def iterator: Iterator[T] = get.iterator
  }
}
