package rogueLike.combat

/**
  * Created by rob on 27/07/16.
  */
trait Expires {
  type T <: Expires
  val timer: Int
  def timer(f: Int => Int): Expires
}

object Expires {
  val DEFAULT = 10
}
