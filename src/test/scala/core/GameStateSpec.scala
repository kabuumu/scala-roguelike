//package core
//
//import rogueLike.movement.Direction.Direction
//import rogueLike.movement._
//import org.scalatest.{FlatSpec, Matchers}
//
///**
//  * Created by rob on 29/06/16.
//  */
//class GameStateSpec extends FlatSpec with Matchers {
//
//  "processEvents" should "when given no entities, and an empty list of events, return an empty gameState" in {
//    GameState(Seq()).processEvents(Seq()) shouldBe GameState(Seq())
//  }
//
//  it should "when given an event which operates on an entity, modify that entity" in {
//    case class TestEntity(override val id: Int, test: Boolean) extends Entity
//    val entity = TestEntity(0, false)
//    val event = Event{case (e) => (Iterable(TestEntity(0, true)), Nil)}
//    GameState(Seq(entity)).processEvents(Seq(event)) shouldBe GameState(Seq(TestEntity(0, true)))
//  }
//
//  it should "not modify an entity when any requirements are not met" in {
//    case class TestEntity(override val id: Int, num: Int) extends Entity
//    val entity = TestEntity(0, 0)
//    val event = Event{
//      case (e: TestEntity) if e.num == 1 =>
//        (Iterable(TestEntity(0, 2)), Seq(Event{ case (e) => (Iterable(e), Nil) }))
//    }
//    GameState(Seq(entity)).processEvents(Seq(event)) shouldBe GameState(Seq(TestEntity(0, 0)))
//  }
//
//  it should "move an entity when given a 'move command' event" in {
//    case class TestMover(override val id: Int, pos: Position, facing: Direction = Direction.Up) extends Mover {
//      override def pos(f: (Position) => Position): TestMover = copy(pos = f(pos))
//      override def facing(dir: Direction):TestMover = copy(facing = dir)
//    }
//
//    val entity = TestMover(0, Position(0, 0))
//    val event = Event{ case (e: Mover) => (Iterable(e.pos(_.move(Direction.Up))), Nil)}
//    val startingState = GameState(Seq(entity))
//    startingState.processEvents(Seq(event)) shouldBe GameState(Seq(TestMover(0, Position(0, -1))))
//
//  }
//
//  it should "process events which themselves return events, until no more remain" in {
//    case class TestEntity(override val id: Int, num: Int) extends Entity
//
//    val entity = TestEntity(0, 0)
//
//    lazy val event = new Event({
//      case (e: TestEntity) if e.num == 0 =>
//        (Iterable(TestEntity(0, 1)), Seq(returnedEvent))
//    })
//
//    lazy val returnedEvent = new Event({
//      case (e: TestEntity) if e.num == 1 =>
//        (Iterable(TestEntity(0, 2)), Nil)
//    })
//
//    val startingState = GameState(Seq(entity))
//    startingState.processEvents(Seq(event)) shouldBe GameState(Seq(TestEntity(0, 2)))
//  }
//
//  it should "process multiple events across multiple entities that rely upon each other" in {
//    case class TestEntity(override val id: Int, num: Int) extends Entity
//
//    val bound = 10
//
//    val entities = (0 until bound).map(n => TestEntity(n, n)) //Entities holding a number from 0 to 9, mapped to the corresponding indices
//
//    def event(i: Int) = new Event({
//      //An event which triggers another event to check the num value of another entity, and create an updating event
//      // if that check is successful
//      case (e: TestEntity) if e.num == i =>
//        (Iterable(e), Seq(checkN((bound - 1) - i, updateN(i))))
//    })
//
//    def checkN(i: Int, f: Event) = Event{
//      //An event which checks to see if an entity at seq(i) is entity(i)
//      case (e: TestEntity) if e.id == i && e.num == i =>
//        (Iterable(e), Seq(f))
//    }
//
//    def updateN(i: Int) = Event{
//      //An event which increments the num of an entity
//      case (e: TestEntity) if e.id == i && e.num == i => (Iterable(TestEntity(i, i + 1)), Nil)
//    }
//
//    val events = (0 until bound).map(event)
//
//    val expected = (1 until bound + 1).map(n => TestEntity(n - 1, n))
//
//    GameState(entities)
//      .processEvents(events) shouldBe GameState(expected)
//  }
//
//  "Movement Event" should "modify the position of an entity where it is free" in {
//    case class TestEntity(override val id: Int, pos: Position, facing: Direction = Direction.Up) extends Entity with Mover {
//      override def pos(f: Position => Position) = copy(pos = f(pos))
//      override def facing(dir: Direction): TestEntity = copy(facing = dir)
//    }
//
//    val entity = TestEntity(0, Position(0, 0))
//
//    val entities = Seq(entity, EventLock())
//
//    val res = GameState(entities)
//      .processEvents(
//        Seq(
//          Movement.moveEvent(0, Direction.Up)))
//    res.entities should contain(TestEntity(0, Position(0,-1)))
//  }
//
//  it should "only allow the first of two entities to move to a position, when both receive an event to move there within the same frame" in {
//    case class TestEntity(override val id: Int, pos: Position, facing: Direction) extends Entity with Mover {
//      override def pos(f: Position => Position) = copy(pos = f(pos))
//      override def facing(dir: Direction): TestEntity = copy(facing = dir)
//
//    }
//
//    val entities = Seq(
//      TestEntity(0, Position(0, 0), Direction.Right),
//      TestEntity(1, Position(2, 0), Direction.Left),
//      EventLock()
//    )
//
//    val events = Seq(
//      Movement.moveEvent(0, Direction.Right),
//      Movement.moveEvent(1, Direction.Left)
//    )
//
//    val res = GameState(entities)
//      .processEvents(events)
//
//    res.entities should contain(TestEntity(0, Position(1,0), Direction.Right))
//    res.entities should contain(TestEntity(1, Position(2,0), Direction.Left))
//  }
//}
