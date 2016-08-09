package ui.app

import core.Entity._
import core.{EventLock, GameState}
import rogueLike.async.{Async, Initiative}
import rogueLike.movement.{Direction, Position}
import rogueLike.output.Sprite
import ui.input.Input

import scala.language.postfixOps
import scalafx.Includes._
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.scene.paint.Color

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
    Seq(EventLock())
      ++ createEntity(Sprite(Color.Blue, _), Initiative(20, _), Position(0, 0, Direction.Down, blocker = false, _))("pc")
      ++ createEntity(Sprite(Color.Black, _), Position(2, 0, Direction.Down, blocker = true, _))
      ++ createEntity(Sprite(Color.Black, _), Position(2, 1, Direction.Down, blocker = true, _))
      ++ createEntity(Sprite(Color.Black, _), Position(2, 2, Direction.Down, blocker = true, _))
  )
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
          case e: Initiative =>
            e.id == playerID && e.current > 0
          case _ => false
        }) ++ Input(keyCode))
    Output.update(state, canvas)
    keyCode = null
  }.start()

  stage.show()
}

