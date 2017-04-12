package refactor.lens

/**
  * Created by rob on 21/03/17.
  */
object LensFunctions {
  implicit class Getter[T,V](getter: (T) => V) {
    def contains(value: V): (T) => Boolean = getter andThen(_ == value)
    def exists(f: V => Boolean): (T => Boolean) = getter andThen f
  }
}
