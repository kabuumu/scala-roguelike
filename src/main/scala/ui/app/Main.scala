package ui.app

import core.{EventLock, GameState}
import rogueLike.async.{Async, Initiative}
import rogueLike.data.Entities._
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
    Seq(EventLock())
      ++ player(playerID, initiative = 20, x = 0, y = 0)
      ++ wall(2, 0)
      ++ wall(2, 1)
      ++ wall(2, 2)
      ++ enemy(25, 5, 5)
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

