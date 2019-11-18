package edu.austral.starship.scala.model.bullet

import java.awt.{Rectangle, Shape}

import edu.austral.starship.scala.base.collision.CollisionableType
import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.model.CollisionableObject
import edu.austral.starship.scala.model.weapon.Weapon

case class Bullet(wea: Weapon, position: Vector2, direction: Vector2, maxX: Int, maxY: Int, width: Int, height: Int, pierce: Boolean) extends CollisionableObject{

  override val collisionableType: CollisionableType.Value = CollisionableType.Bullet
  var positionVector: Vector2 = position
  var directionVector: Vector2 = direction
  var destroyed: Boolean = false
  val weapon: Weapon = wea

  override var shape: Shape = new Rectangle(positionVector.x toInt, positionVector.y toInt, 20, 20)

  override def getShape: Shape = shape

  override def collisionedWith(collisionable: CollisionableObject): Unit = {
    if(collisionable.collisionableType == CollisionableType.Asteroid) {
      if(!pierce) destroy()
      weapon.starship.player.addPoints(1)
    }
  }

  override def accelerate(vector: Vector2): Unit = {
    directionVector = directionVector + vector
  }

  override def break(vector: Vector2): Unit = {
    directionVector = directionVector - vector
  }

  override def turn(vector2: Vector2): Unit = ???

  def setPositionVector(vector2: Vector2): Unit = {
    positionVector = vector2
  }

  def setDirectionVector(vector2: Vector2): Unit = {
    directionVector = vector2
  }

  def destroy(): Unit = {
    destroyed = true
  }

  def calculateNewPosition(): Unit = {
    positionVector = positionVector + directionVector
    shape = new Rectangle(positionVector.x toInt, positionVector.y toInt, width, height)
  }

  def checkDestroy(): Unit = {
    if(positionVector.x <= 0 || positionVector.x >= maxX || positionVector.y <= 0 || positionVector.y >= maxY) destroy()
  }
}
