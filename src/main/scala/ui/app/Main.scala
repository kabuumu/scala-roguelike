package ui.app

import core.entity.ID
import core.event.EventComponent._
import core.system.GameState
import data.GameData._
import roguelike.ai.EnemyAI
import roguelike.async.Initiative._
import ui.input.{InputController, KeyEventConsumer}
import ui.output.OutputConfig._
import ui.output.OutputController

import scala.language.postfixOps
import scalafx.Includes._
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas

/**
  * Created by rob on 13/04/16.
  */
object Main extends JFXApp {
  val canvas = new Canvas(canvasWidth, canvasHeight)
  //200ms
  var lastDelta = 0L
  val output = new OutputController(canvas)
  var keyConsumer = new KeyEventConsumer
  val input = new InputController

  var state: GameState = GameState(startingData)//.update(Seq(triggerEntityEvents))

  stage = new PrimaryStage {
    title = "scala-roguelike"
    scene = new Scene {
      content = canvas

      onKeyPressed = event => keyConsumer += event
      onKeyReleased = event => keyConsumer -= event
    }
  }

  AnimationTimer { now: Long =>
    if(lastDelta < now - 1000000000/120) {
      for {
        player <- state.entities.find(_[ID] == playerID)
      } {
        val inputEvents = input.getInputEvents(player, keyConsumer)
        
        output.update(state, player, input)

        if ((player exists notReady) || inputEvents.nonEmpty) {
          val events = Seq(
            EnemyAI.enemyActionEvent(player),
            triggerEntityEvents
          ) ++ inputEvents

          state = state.update(events)
        }

        lastDelta = now
      }
    }
  }.start()

  stage.show()
}

