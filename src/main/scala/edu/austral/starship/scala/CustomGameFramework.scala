package edu.austral.starship.scala

import edu.austral.starship.scala.base.collision.CollisionEngine
import edu.austral.starship.scala.base.framework.{GameFramework, ImageLoader, WindowSettings}
import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.model.CollisionableObject
import edu.austral.starship.scala.model.asteroid.{Asteroid, AsteroidImpl}
import edu.austral.starship.scala.model.bullet.Bullet
import edu.austral.starship.scala.model.player.Player
import edu.austral.starship.scala.model.starship.{MyStarship, Starship}
import edu.austral.starship.scala.view.{AsteroidView, BulletView, StarshipView}
import edu.austral.starship.scala.view.screen.Screen
import processing.core.{PGraphics, PImage}
import processing.event.KeyEvent

import scala.language.postfixOps

object CustomGameFramework extends GameFramework {
  var screen = Screen(1000, 700)

  var player = Player(0)
  var player2 = Player(0)

  var starship = MyStarship(player, 333, 500)
  var bullets: List[Bullet] = starship.weapon.bullets
  var starshipImage: PImage = new PImage()
  var starshipView = StarshipView(starship, starshipImage)
  var bulletViews: List[BulletView] = List()

  var asteroids: List[Asteroid] = List()
  var asteroidImage: PImage = new PImage()
  var asteroidViews: List[AsteroidView] = List()
  var bulletImage: PImage = new PImage()

  var collisionEngine: CollisionEngine[CollisionableObject] = _

  override def setup(windowsSettings: WindowSettings, imageLoader: ImageLoader): Unit = {
    windowsSettings
      .setSize(screen.x, screen.y)
    starshipImage = imageLoader.load("C:\\Users\\Mariano\\projects\\starships\\src\\resources\\starship2.png")
    bulletImage = imageLoader.load("C:\\Users\\Mariano\\projects\\starships\\src\\resources\\bullet.jpg")
    asteroidImage = imageLoader.load("C:\\Users\\Mariano\\projects\\starships\\src\\resources\\asteroid.jpg")
    starshipView.setImg(starshipImage)

    collisionEngine = new CollisionEngine[CollisionableObject]
  }

  def checkCollisions(): Unit = {
    val collisionables: List[CollisionableObject] = starship :: asteroids ++ bullets
    collisionEngine.checkCollisions(collisionables)
  }

  def drawBullets(graphics: PGraphics): Unit = {
    bullets.foreach(bullet => {
      bullet.calculateNewPosition()
      bullet.checkDestroy()
    })
    bullets = bullets.filter(!_.destroyed)

    bulletViews.foreach(bull => {
      bull.calculateNewPosition()
      bull.checkDestroyed()
      graphics.image(bull.image, bull.positionVector.x, bull.positionVector.y, 20, 20)
    })

    bulletViews = bulletViews.filter(!_.destroyed)
  }

  def generateAsteroid(): Unit = {
    val aux = Math.random()
    if (aux < 0.05) {
      val asteroid: AsteroidImpl = AsteroidImpl(
        Math.random() * 1000 toFloat,
        -40 toFloat,
        Math.random() * 2 toFloat,
        Math.random() * 2 toFloat,
        screen.x,
        screen.y
      )
      asteroids = asteroid :: asteroids
      asteroidViews = AsteroidView(asteroid, asteroidImage) :: asteroidViews
    }
  }

  def drawAsteroids(graphics: PGraphics): Unit = {
    asteroids.foreach(asteroid => {
      asteroid.calculateNewPosition()
      asteroid.checkDestroy()
    })

    asteroids = asteroids.filter(!_.destroyed)

    asteroidViews.foreach(asteroidView => {
      asteroidView.calculateNewPosition()
      asteroidView.checkDestroyed()
      graphics.image(asteroidView.image, asteroidView.positionVector.x, asteroidView.positionVector.y, 30, 30)
    })

    asteroidViews = asteroidViews.filter(!_.destroyed)
  }

  override def draw(graphics: PGraphics, timeSinceLastDraw: Float, keySet: Set[Int]): Unit = {
    checkEndGame(graphics)
    drawHUD(graphics)
    drawStarship(graphics)
    drawBullets(graphics)
    generateAsteroid()
    drawAsteroids(graphics)
    checkCollisions()
    keyHandler(keySet)
    //    inertiaStarship(0.005 toFloat)
  }

  def keyHandler(keySet: Set[Int]): Unit ={
    keySet.foreach {
      case 87 => starship.accelerate(Vector2(0, -0.25 toFloat))
      case 65 => starship.accelerate(Vector2(-0.25 toFloat, 0))
      case 83 => starship.accelerate(Vector2(0, 0.25 toFloat))
      case 68 => starship.accelerate(Vector2(0.25 toFloat, 0))
      case 77 => {
        starship.fire()
        bullets = starship.weapon.bullets
        bulletViews = BulletView(starship.weapon.bullets.head, bulletImage) :: bulletViews
      }
    }
  }
  override def keyPressed(event: KeyEvent): Unit = {
  }

  override def keyReleased(event: KeyEvent): Unit = {
  }


  def drawStarship(graphics: PGraphics): Unit = {

    var directionX = starshipView.directionVector.x
    var directionY = starshipView.directionVector.y
    var positionX = starshipView.positionVector.x + directionX
    var positionY = starshipView.positionVector.y + directionY

    if (positionX > screen.x - 40) {
      positionX = screen.x - 40
      starship.break(Vector2(directionX * 0.1 toFloat, directionY * 0.1 toFloat))
    }
    if (positionX < 10) {
      positionX = 10
      starship.break(Vector2(directionX * 0.1 toFloat, directionY * 0.1 toFloat))
    }
    if (positionY > screen.y - 40) {
      positionY = screen.y - 40
      starship.break(Vector2(directionX * 0.1 toFloat, directionY * 0.1 toFloat))
    }
    if (positionY < 10) {
      positionY = 10
      starship.break(Vector2(directionX * 0.1 toFloat, directionY * 0.1 toFloat))
    }

    graphics.image(starshipView.image,
      positionX,
      positionY,
      40,
      40)
    starship.setPositionVector(Vector2(positionX, positionY))
    starship.calculateNewPosition()
    starshipView.setDirectionVector(starship.directionVector)
    starshipView.setPositionVector(starship.positionVector)
    //    println("Starship Position X: " + starship.positionVector.x)
    //    println("StarshipView Position X: " + starshipView.positionVector.x)
    //    println("Starship Position Y: " + starship.positionVector.y)
    //    println("StarshipView Position Y: " + starshipView.positionVector.y)
    //    println("Starship Direction X: " + starship.directionVector.x)
    //    println("StarshipView Direction X: " + starshipView.directionVector.x)
    //    println("Starship Direction Y: " + starship.directionVector.y)
    //    println("StarshipView Direction Y: " + starshipView.directionVector.y)
  }

  def inertiaStarship(inertia: Float): Unit = {
    val starshipDirectionX = starship.directionVector.x
    val starshipDirectionY = starship.directionVector.y
    if (starshipDirectionX != 0) {
      starship.break(Vector2(starshipDirectionX * inertia, starshipDirectionY))
    }
    if (starshipDirectionY != 0) {
      starship.break(Vector2(starshipDirectionX, starshipDirectionY * inertia))

    }
  }

  def drawHUD(graphics: PGraphics): Unit = {
    graphics.text("Points: " + player.points + "\nLives: " + player.lives, 10, 20)
  }

  def checkEndGame(graphics: PGraphics): Unit ={
    if(player.lives == 0){
      graphics.dispose()
      System.exit(0)
    }
  }
}
