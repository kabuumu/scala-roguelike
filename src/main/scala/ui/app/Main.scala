package ui.app

import core.{Event, EventLock, GameState}
import rogueLike.async.{Async, HasInitiative, Initiative}
import rogueLike.movement.{Direction, Position, Wall}
import rogueLike.state.Actor
import ui.input.Input

import scala.language.postfixOps
import scalafx.Includes._
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.input.{KeyCode, KeyEvent}

/**
  * Created by rob on 13/04/16.
  */
object Main extends JFXApp {
  lazy val playerID: String = "pc"

  val canvas = new Canvas(512, 512)
  val frameRate = 1
  //200ms
  var lastDelta = 0
  var keyCode: KeyCode = null
  var state: GameState = GameState(
    Seq(
      EventLock(),
      new {
        override val id = playerID
      }
        with Actor(Position(0, 0), Initiative(20), Direction.Up),
      Wall(Position(2, 0)), Wall(Position(2, 1)), Wall(Position(2, 2))
    )
  )
    .processEvents(Seq(Event{ case e @ Wall(pos, _) => (Seq(e), Seq(EventLock.lockingEvent(pos, e)))}))

  stage = new PrimaryStage {
    title = "scala-roguelike"
    scene = new Scene {
      content = canvas

      onKeyPressed = {
        key: KeyEvent => keyCode = key.code
      }
    }
  }

  AnimationTimer { now: Long =>
    state = state.processEvents(
      Some(Async.update)
        .filter(_ => state.entities.exists {
          case e: HasInitiative =>
            e.id == playerID && e.initiative.current > 0
          case _ => false
        }) ++ Input(keyCode))
    Output.update(state, canvas)
    keyCode = null
  }.start()

  stage.show()
}

