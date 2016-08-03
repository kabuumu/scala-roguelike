package rogueLike.combat

/**
  * Created by rob on 27/07/16.
  */
trait Expires {
  val timer: Int
  def timer(f: Int => Int): Expires
}

object Expires {
  val DEFAULT = 10
}
