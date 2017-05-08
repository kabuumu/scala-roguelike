package core

import core.entity.{Component, Entity, ID}

/**
  * Created by rob on 03/03/17.
  */
trait TestFixture {
  val emptyEntity = Entity()
  val testComponent = new TestComponent {}
  val setToTrue = (c: BooleanComponent) => c.copy(boolean = true)
  val increment = (c: IntComponent) => c.copy(n = c.n + 1)
  val intIsZero = (c: IntComponent) => c.n == 0
  val intIsOne = (c: IntComponent) => c.n == 1
  val booleanIsFalse = (c: BooleanComponent) => !c.boolean
  val hasIntComponent = (c: IntComponent) => true
  val id = new ID


  class TestComponent extends Component

  case class BooleanComponent(boolean: Boolean) extends Component
  case class IntComponent(n: Int) extends Component
  case class StringComponent(s: String) extends Component

  val n: IntComponent => Int = _.n
}
