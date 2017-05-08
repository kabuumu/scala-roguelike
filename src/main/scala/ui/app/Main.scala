package ui.app

import core.entity.{Entity, ID}
import core.system.GameState
import roguelike.actors.Affinity.{Enemy, Player}
import roguelike.actors.{Affinity, Affinity$}
import roguelike.ai.EnemyAI
import roguelike.async.{Async, Initiative}
import roguelike.combat.Health
import roguelike.map.MapConverter._
import roguelike.movement.Direction._
import roguelike.movement.Movement._
import roguelike.movement._
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
  val canvas = new Canvas(800, 640)
  val frameRate = 1
  //200ms
  var lastDelta = 0L
  var keyCode: KeyCode = _

  val playerID = new ID
  val enemyID = new ID

  val startingPlayer = Entity(playerID, Affinity(Player), Position(1, 1), Facing(Up))
  val startingEnemy = Entity(new ID, Affinity(Enemy), Position(20, 5), Facing(Left), Initiative(max = 25), Health(max = 30))
  val walls = convert(tileMap)

  var state: GameState = GameState(Seq(
    startingPlayer,
    startingEnemy
  ) ++ walls)

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
    if(lastDelta < now - 1000000000/150) {
      val inputEvent = for {
        player <- state.entities.find(_[ID] contains playerID)
        inputEvent <- Input(keyCode)
      } yield inputEvent(player)

      val enemyMoveEvent = for {
        player <- state.entities.find(_[ID] contains playerID)
      } yield EnemyAI.enemyMoveEvent(player)

      state = state.update(
        Seq(velocityUpdate,
          Async.updateInitiative
        ) ++ inputEvent ++ enemyMoveEvent
      )

      new Output(state, canvas).update()
      keyCode = null
      lastDelta = now
    }
  }.start()

  stage.show()
}

