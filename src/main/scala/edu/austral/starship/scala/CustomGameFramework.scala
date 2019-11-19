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

  var player = Player(0, "1")
  var player2 = Player(0, "2")

  var asteroids: List[Asteroid] = List()
  var asteroidImage: PImage = new PImage()
  var asteroidViews: List[AsteroidView] = List()
  var bulletImage: PImage = new PImage()
  var heavyBulletImage: PImage = new PImage()
  var starships: List[Starship] = List()
  var starshipsImages: List[PImage] = List()
  var starshipsViews: List[StarshipView] = List()

  var bullets: List[List[Bullet]] = List()

  var bulletViews: List[BulletView] = List()
  var collisionEngine: CollisionEngine[CollisionableObject] = _

  override def setup(windowsSettings: WindowSettings, imageLoader: ImageLoader): Unit = {
    windowsSettings
      .setSize(screen.x, screen.y)
    bulletImage = imageLoader.load("C:\\Users\\Mariano\\projects\\starships\\src\\resources\\bullet.jpg")
    heavyBulletImage = imageLoader.load("C:\\Users\\Mariano\\projects\\starships\\src\\resources\\heavy_bullet.png")
    asteroidImage = imageLoader.load("C:\\Users\\Mariano\\projects\\starships\\src\\resources\\asteroid.jpg")

    collisionEngine = new CollisionEngine[CollisionableObject]

    starships = List(MyStarship(player, 333, 500), MyStarship(player2, 666, 500))
    starshipsImages = List(imageLoader.load("C:\\Users\\Mariano\\projects\\starships\\src\\resources\\starship2.png"), imageLoader.load("C:\\Users\\Mariano\\projects\\starships\\src\\resources\\starship3.png"))
    starshipsViews = List(StarshipView(starships.head, starshipsImages.head), StarshipView(starships(1), starshipsImages(1)))

    starships.foreach(starship => {
      starship.weapons.foreach(weapon => {
        bullets = weapon.bullets :: bullets
      })
    })
  }

  def checkCollisions(): Unit = {
    val collisionables: List[CollisionableObject] = starships ++ asteroids ++ bullets.head ++ bullets(1)
    collisionEngine.checkCollisions(collisionables)
  }

  def drawBullets(graphics: PGraphics): Unit = {
    bullets.foreach(starshipBullets => {
      starshipBullets.foreach(bullet => {
        bullet.calculateNewPosition()
        bullet.checkDestroy()
      })
    })
    bullets = bullets.map(_.filter(!_.destroyed))

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
//    checkEndGame(graphics)
    checkPlayerLoss() match {
      case Left(player) => showGameOver(player, graphics)
      case Right(false) =>
        drawHUD(graphics)
        drawStarships(graphics)
        drawBullets(graphics)
        generateAsteroid()
        drawAsteroids(graphics)
        checkCollisions()
        keyHandler(keySet)
    }
  }

  def keyHandler(keySet: Set[Int]): Unit ={
    keySet.foreach {
      case 87 => starships.head.accelerate(Vector2(0, -0.25 toFloat))
      case 65 => starships.head.accelerate(Vector2(-0.25 toFloat, 0))
      case 83 => starships.head.accelerate(Vector2(0, 0.25 toFloat))
      case 68 => starships.head.accelerate(Vector2(0.25 toFloat, 0))
      case 77 =>
        if(starships.head.fire()){
          val selectedWeapon = starships.head.selectedWeapon
          bullets = List(starships.head.weapons(selectedWeapon).bullets, bullets(1))
          if(selectedWeapon == 0){
            bulletViews = BulletView(starships.head.weapons(selectedWeapon).bullets.head, bulletImage) :: bulletViews
          }else{
            bulletViews = BulletView(starships.head.weapons(selectedWeapon).bullets.head, heavyBulletImage) :: bulletViews
          }
        }
      case 78 => starships.head.changeWeapon()
      case 38 => starships(1).accelerate(Vector2(0, -0.25 toFloat))
      case 37 => starships(1).accelerate(Vector2(-0.25 toFloat, 0))
      case 40 => starships(1).accelerate(Vector2(0, 0.25 toFloat))
      case 39 => starships(1).accelerate(Vector2(0.25 toFloat, 0))
      case 96 =>
        if(starships(1).fire()){
          val selectedWeapon = starships(1).selectedWeapon
          bullets = List(bullets.head, starships(1).weapons(selectedWeapon).bullets)
          if(selectedWeapon == 0){
            bulletViews = BulletView(starships(1).weapons(selectedWeapon).bullets.head, bulletImage) :: bulletViews
          }else{
            bulletViews = BulletView(starships(1).weapons(selectedWeapon).bullets.head, heavyBulletImage) :: bulletViews
          }
        }
      case 101 => starships(1).changeWeapon()
      case _ =>
    }
  }
  override def keyPressed(event: KeyEvent): Unit = {
  }

  override def keyReleased(event: KeyEvent): Unit = {
  }


  def drawStarships(graphics: PGraphics): Unit = {

    var i = 0
    starshipsViews.foreach(starshipView => {
      var directionX = starshipView.directionVector.x
      var directionY = starshipView.directionVector.y
      var positionX = starshipView.positionVector.x + directionX
      var positionY = starshipView.positionVector.y + directionY

      if (positionX > screen.x - 40) {
        positionX = screen.x - 40
        starships(i).break(Vector2(directionX * 0.1 toFloat, directionY * 0.1 toFloat))
      }
      if (positionX < 10) {
        positionX = 10
        starships(i).break(Vector2(directionX * 0.1 toFloat, directionY * 0.1 toFloat))
      }
      if (positionY > screen.y - 40) {
        positionY = screen.y - 40
        starships(i).break(Vector2(directionX * 0.1 toFloat, directionY * 0.1 toFloat))
      }
      if (positionY < 10) {
        positionY = 10
        starships(i).break(Vector2(directionX * 0.1 toFloat, directionY * 0.1 toFloat))
      }

      graphics.image(starshipView.image,
        positionX,
        positionY,
        40,
        40)
      starships(i).setPositionVector(Vector2(positionX, positionY))
      starships(i).calculateNewPosition()
      starshipView.setDirectionVector(starships(i).directionVector)
      starshipView.setPositionVector(starships(i).positionVector)
      starships(i).weapons.foreach(_.increaseCooling())
      starships(i).sumCooldown()
      i = i + 1
      //    println("Starship Position X: " + starship.positionVector.x)
      //    println("StarshipView Position X: " + starshipView.positionVector.x)
      //    println("Starship Position Y: " + starship.positionVector.y)
      //    println("StarshipView Position Y: " + starshipView.positionVector.y)
      //    println("Starship Direction X: " + starship.directionVector.x)
      //    println("StarshipView Direction X: " + starshipView.directionVector.x)
      //    println("Starship Direction Y: " + starship.directionVector.y)
      //    println("StarshipView Direction Y: " + starshipView.directionVector.y)
    })
  }

  /*def inertiaStarship(inertia: Float): Unit = {
    val starshipDirectionX = starship.directionVector.x
    val starshipDirectionY = starship.directionVector.y
    if (starshipDirectionX != 0) {
      starship.break(Vector2(starshipDirectionX * inertia, starshipDirectionY))
    }
    if (starshipDirectionY != 0) {
      starship.break(Vector2(starshipDirectionX, starshipDirectionY * inertia))
    }
  }*/

  def giveWeapon(selectedWeapon: Int): Any = {
    selectedWeapon match {
      case 0 => "Normal"
      case 1 => "Heavy"
    }
  }

  def drawHUD(graphics: PGraphics): Unit = {
    graphics.text("Player 1" +
      "\nPoints: " + player.points +
      "\nLives: " + player.lives +
      "\nWeapon: " + giveWeapon(starships.head.selectedWeapon),
      10, 20)
    graphics.text("Player 2" +
      "\nPoints: " + player2.points +
      "\nLives: " + player2.lives +
      "\nWeapon: " + giveWeapon(starships(1).selectedWeapon),
      900, 20)
  }

  def checkEndGame(graphics: PGraphics): Unit ={
    if(player.lives == 0 || player2.lives == 0){
      graphics.dispose()
      System.exit(0)
    }
  }

  def showGameOver(player: Player, graphics: PGraphics): Unit = {
      graphics.text("GAME OVER!" +
        "\n" +
        "Player " + player.name + " lost" +
        "\n-------------" +
        "\nPlayer 1 points: " + player.points +
        "\n-------------" +
        "\nPlayer 2 points: " + player2.points,
        (screen.x / 2) - 50,
        screen.y / 2)
  }

  def checkPlayerLoss(): Either[Player, Boolean] = {
    if(player.lives == 0) Left(player)
    else if(player2.lives == 0) Left(player2)
    else Right(false)
  }
}
