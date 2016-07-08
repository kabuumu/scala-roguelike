package events

import movement.Position.movePos
import movement.{Direction, Movement, Mover, Position}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by rob on 29/06/16.
  */
class GameStateSpec extends FlatSpec with Matchers {

  "processEvents" should "when given no entities, and an empty list of events, return an empty gameState" in {
    GameState(Seq()).processEvents(Seq()) shouldBe GameState(Seq())
  }

  it should "when given an event which operates on an entity, modify that entity" in {
    case class TestEntity(test: Boolean) extends Entity
    val entity = TestEntity(false)
    val event = new Event(0, {
      case (s, e) => (TestEntity(true), Nil)
    })
    GameState(Seq(entity)).processEvents(Seq(event)) shouldBe GameState(Seq(TestEntity(true)))
  }

  it should "not modify an entity when any requirements are not met" in {
    case class TestEntity(num: Int) extends Entity
    val entity = TestEntity(0)
    val event = new Event(0, {
      case (s, e: TestEntity) if e.num == 1 =>
        (TestEntity(2), Seq(new Event(0, { case (_, e) => (e, Nil) })))
    })
    GameState(Seq(entity)).processEvents(Seq(event)) shouldBe GameState(Seq(TestEntity(0)))
  }

  it should "move an entity when given a 'move command' event" in {
    case class TestMover(pos: Position) extends Mover[TestMover] {
      override def pos(f: (Position) => Position): TestMover = copy(pos = f(pos))
    }

    val entity = TestMover(Position(0, 0))
    val event = new Event(0, {
      case (s, e: Mover[_]) => (e.pos(p => movePos(Direction.Up)(p)), Nil)
    })
    val startingState = GameState(Seq(entity))
    startingState.processEvents(Seq(event)) shouldBe GameState(Seq(TestMover(Position(0, -1))))

  }

  it should "process events which themselves return events, until no more remain" in {
    case class TestEntity(num: Int) extends Entity

    val entity = TestEntity(0)

    lazy val event = new Event(0, {
      case (s, e: TestEntity) if e.num == 0 =>
        (TestEntity(1), Seq(returnedEvent))
    })

    lazy val returnedEvent = new Event(0, {
      case (s, e: TestEntity) if e.num == 1 =>
        (TestEntity(2), Nil)
    })

    val startingState = GameState(Seq(entity))
    startingState.processEvents(Seq(event)) shouldBe GameState(Seq(TestEntity(2)))
  }

  it should "process multiple events across multiple entities that rely upon each other" in {
    case class TestEntity(num: Int) extends Entity

    val bound = 10

    val entities = (0 until bound).map(TestEntity) //Entities holding a number from 0 to 9, mapped to the corresponding indices

    def event(i: Int) = new Event(i, { //An event which triggers another event to check the num value of another entity, and create an updating event
      // if that check is successful
      case (s, e: TestEntity) if e.num == i =>
        (e, Seq(checkN((bound - 1) - i, updateN(i))))
    })

    def checkN(i: Int, f: Event) = new Event(i, { //An event which checks to see if an entity at seq(i) is entity(i)
      case (s, e: TestEntity) if e.num == i =>
        (e, Seq(f))
    })

    def updateN(i: Int) = new Event(i, { //An event which increments the num of an entity
      case (s, e: TestEntity) => (TestEntity(i + 1), Nil)
    })

    val events = (0 until bound).map(event)

    val expected = (1 until bound+1).map(TestEntity)

    GameState(entities)
      .processEvents(events) shouldBe GameState(expected)
  }

  "Movement Event" should "modify the position of an entity where it is free" in {
    case class TestEntity(pos: Position) extends Entity with Mover[TestEntity]{
      override def pos(f: Position => Position) = copy(pos = f(pos))
    }

    val entity = TestEntity(Position(0,0))
    val position = Position(0,-1)

    val entities = Seq(entity, position)

    GameState(entities).processEvents(Seq(Movement.moveEvent(0, Direction.Up))) shouldBe GameState(Seq(TestEntity(Position(0,-1)),Position(0,-1,true)))
  }
}
