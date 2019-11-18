package edu.austral.starship.scala.model.starship

import java.awt.{Rectangle, Shape}

import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.model.weapon.{MyWeapon, Weapon}
import edu.austral.starship.scala.model.CollisionableObject
import edu.austral.starship.scala.model.player.Player

case class MyStarship(pilot: Player, initalX: Int, initialY: Int) extends Starship{

  override var positionVector: Vector2 = Vector2(initalX, initialY)
  override var directionVector: Vector2 = Vector2(0, 0)
  override var weapon: Weapon = MyWeapon(this)
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
    crash()
  }

  override def fire(): Unit = {
    weapon.fire(directionVector, positionVector)
  }

  def calculateNewPosition(): Unit = {
    positionVector = positionVector + directionVector
    shape = new Rectangle(positionVector.x toInt, positionVector.y toInt, 40, 40)
  }
}
