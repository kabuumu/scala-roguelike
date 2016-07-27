package rogueLike.async

/**
  * Created by rob on 22/07/16.
  */
class Initiative private(val max: Int, val current: Int){
  lazy val -- = new Initiative(max, current - 1)
  lazy val reset = new Initiative(max, max)
}

object Initiative {
  val DEFAULT = new Initiative(-1, -1)

  def apply(value: Int) = new Initiative(value, value)
}
