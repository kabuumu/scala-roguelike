package core.refactor

/**
  * Created by rob on 19/12/16.
  */
class EntityUpdate[T](val target: T, f: T => T) extends (T => T) {
  override def apply(entity: T): T = f(entity)
}
