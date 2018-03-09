package ui.app

import core.entity.{Entity, ID}
import core.event.EventComponent._
import core.system.GameState
import data.GameData._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.GLFWKeyCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11._
import org.lwjgl.system.MemoryUtil._
import roguelike.actors.Affinity
import roguelike.ai.EnemyAI
import roguelike.async.Initiative._
import roguelike.combat.{Attack, Health}
import roguelike.light.{LightBlocker, LightCaster}
import roguelike.movement.Position
import roguelike.movement.lineofsight.VisibleTiles
import roguelike.scenery.{Floor, Wall}
import ui.input.{InputController, KeyEventConsumer}

import scalafx.scene.paint.Color

class LWJGLMain extends Runnable {
  private var thread: Thread = _
  var running: Boolean = _
  private var window: Long = _
  var state: GameState = _
  val input = new InputController
  var keyConsumer = new KeyEventConsumer

  val radius = 8

  val (width, height) = (640, 480)
  val size = 20

  def start(): Unit = {
    running = true
    thread = new Thread(this, "EndlessRunner")
    thread.start()
    state = GameState(startingData)
  }

  def init(): Unit = {
    if (!glfwInit()) {
      System.err.println("GLFW initialization failed!")
    }

    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

    window = glfwCreateWindow(width, height, "Endless Runner", NULL, NULL)

    if (window == NULL) {
      System.err.println("Could not create our Window!")
    }

    val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())

    glfwSetWindowPos(window, 100, 100)

    glfwMakeContextCurrent(window)
    GL.createCapabilities()

    glfwShowWindow(window)

    glMatrixMode(GL_PROJECTION)
    glLoadIdentity()
    glOrtho(-((width / size) / 2), (width / size) / 2 , (height / size) / 2, -((height / size) / 2), 1, -1)

    glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix
    glLoadIdentity()

    glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
    glClearDepth(1.0f) // Depth Buffer Setup
    glEnable(GL_DEPTH_TEST) // Enables Depth Testing
    glDepthFunc(GL_LEQUAL)
    glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST)
  }

  def update(): Unit = {
    glfwPollEvents()

    glfwSetKeyCallback(window, GLFWKeyCallback.create((window, key, scancode, action, mods) =>
      if(action == GLFW_PRESS) keyConsumer += key
      else if (action == GLFW_RELEASE) keyConsumer -= key
    ))

    //if(lastDelta < now - 1000000000/120) {
    for {
      player <- state.entities.find(_[ID] == playerID)
    } {
      val inputEvents = input.getInputEvents(player, keyConsumer)

      if ((player exists notReady) || inputEvents.nonEmpty) {
        val events = Seq(
          EnemyAI.enemyActionEvent(player),
          triggerEntityEvents
        ) ++ inputEvents

        state = state.update(events)
      }

      //lastDelta = now
    }
  }

  //}

  def render(): Unit = {

    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

    glLoadIdentity()

    val player = state.entities.find(_[ID] == playerID).get

    val Position(playerX, playerY) = player[Position]

    state.entities foreach { e =>
      val x = e[Position].x - playerX
      val y = e[Position].y - playerY

      if(e.has[LightBlocker]) {
        glBegin(GL_QUADS)
          if(player[VisibleTiles].tiles.contains(e[Position])) {
            setColour(e)
          }
          else glColor3f(0.25f, 0.2f, 0.15f)
          glVertex2i(x, y + 1)
          glVertex2i(x + 1, y + 1)
          glVertex2i(x + 1, y)
          glVertex2i(x, y)
        glEnd()
      }
    }

    input.target foreach { e =>
      val x = e.x - playerX
      val y = e.y - playerY

      glBegin(GL_QUADS)
        glColor3f(1f, 1f, 0f)
        glVertex2i(x, y + 1)
        glVertex2i(x + 1, y + 1)
        glVertex2i(x + 1, y)
        glVertex2i(x, y)
      glEnd()

    }

    glfwSwapBuffers(window)
  }

  override def run(): Unit = {
    init()
    while (running) {
      update()
      render()

      if (glfwWindowShouldClose(window)) running = false
    }
  }

  def setColour(entity: Entity): Unit = {
    if (entity.has[Attack]) glColor3f(.5f, .5f, .5f)
    else if (entity.exists[Affinity](_.faction == Affinity.Player)) glColor3f(0f, 1f, 0f)
    else if (entity.exists[Affinity](_.faction == Affinity.Enemy)) glColor3f(.5f, 0f, 0f)
    else if (entity.has[Wall]) glColor3f(.3f, .3f, .3f)
    else if (entity.has[Floor]) glColor3f(.3f, .3f, 0f)
    else if (entity.has[LightCaster]) glColor3f(.5f, .2f, .2f)
  }
}

object LWJGLMain {
  def main(args: Array[String]): Unit = {
    val game = new LWJGLMain()
    game.start()
  }
}