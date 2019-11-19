package edu.austral.starship.scala.model.starship

import java.awt.{Rectangle, Shape}

import edu.austral.starship.scala.base.collision.CollisionableType
import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.model.weapon.{HeavyWeapon, MyWeapon, Weapon}
import edu.austral.starship.scala.model.CollisionableObject
import edu.austral.starship.scala.model.player.Player

case class MyStarship(pilot: Player, initalX: Int, initialY: Int) extends Starship{

  override var cooldown: Int = 0
  override var positionVector: Vector2 = Vector2(initalX, initialY)
  override var directionVector: Vector2 = Vector2(0, 0)
  override var weapons: List[Weapon] = List(MyWeapon(this), HeavyWeapon(this))
  override var selectedWeapon: Int = 0
  override var player: Player = pilot
  override var shape: Shape = new Rectangle(positionVector.x toInt, positionVector.y toInt, 40, 40)


  override def accelerate(vector: Vector2): Unit = {
    directionVector = directionVector + vector
  }

  override def break(vector: Vector2): Unit = {
    directionVector = directionVector - vector
  }

  override def turn(vector2: Vector2): Unit = {
  }

  override def getShape: Shape = shape

  override def collisionedWith(collisionable: CollisionableObject): Unit = {
    if(collisionable.collisionableType == CollisionableType.Asteroid) crash()
  }

  override def fire(): Boolean = {
    weapons(selectedWeapon).fire(directionVector, positionVector)
  }

  def calculateNewPosition(): Unit = {
    positionVector = positionVector + directionVector
    shape = new Rectangle(positionVector.x toInt, positionVector.y toInt, 40, 40)
  }

  def changeWeapon(): Unit = {
    if(cooldown >= 60){
      if(selectedWeapon == weapons.size - 1) selectedWeapon = 0
      else selectedWeapon = selectedWeapon + 1
      resetCooldown()
    }
  }
}
