package rogueLike.async

import core.Entity

/**
  * Created by rob on 22/07/16.
  */
class Initiative private(val max: Int, val current: Int, val id: String) extends Entity{
  lazy val -- = new Initiative(max, current - 1, id)
  lazy val reset = new Initiative(max, max, id)
}

object Initiative {
  val DEFAULT = new Initiative(-1, -1, "")

  def apply(value: Int, id: String = "pc") = new Initiative(value, value, id)
}
