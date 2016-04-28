package state

/**
  * Created by rob on 27/04/16.
  */
case class Attribute(current: Int, max: Int){
  def current(f: Int => Int):Attribute = copy(current = f(current))

  def max(f: Int => Int):Attribute = copy(max = f(max))

  def reset:Attribute = copy(current = max)
}

object Attribute{
  def apply(max: Int) = new Attribute(max, max)
  def current(attribute: Attribute) = (f: Int => Int) => attribute.copy(current = f(attribute.current))
}
