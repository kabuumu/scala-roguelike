package main

import events.GameState
import input.Input
import movement.{Mover, Position}
import state.Actor

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
  val canvas = new Canvas(512, 512)

  var lastDelta = 0
  var keyCode:KeyCode = null
  var state:GameState = GameState(Seq(Actor(0, Position(0,0), Map(), isPC = true), Position(0,0), Position(0,1), Position(1,0), Position(1,1),Position(1,2)))
  val frameRate = 1 //200ms

  stage = new PrimaryStage {
    title = "scala-roguelike"
    scene = new Scene {
      content = canvas

      onKeyPressed = {
        key:KeyEvent => keyCode = key.code
      }
    }
  }

  AnimationTimer { now: Long =>
    state = state.processEvents(Input(keyCode))
    Output.update(state, canvas)
    keyCode = null
  }.start()

  stage.show()
}

