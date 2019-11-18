package edu.austral.starship.scala.model.asteroid
import java.awt.{Rectangle, Shape}

import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.model.CollisionableObject

case class AsteroidImpl(posX: Float, posY: Float, dirX: Float, dirY: Float, maxX: Int, maxY: Int) extends Asteroid{
  override var positionVector: Vector2 = Vector2(posX, posY)
  override var directionVector: Vector2 = Vector2(dirX, dirY)
  override var shape: Shape = new Rectangle(positionVector.x toInt, positionVector.y toInt, 30, 30)
  override var destroyed: Boolean = false

  override def accelerate(vector: Vector2): Unit = {
    positionVector = positionVector + vector
  }

  override def break(vector: Vector2): Unit = {
    positionVector = positionVector - vector
  }

  override def turn(vector2: Vector2): Unit = ???

  override def getShape: Shape = shape

  def destroy(): Unit = {
    destroyed = true
  }

  override def collisionedWith(collisionable: CollisionableObject): Unit = {
    destroy()
  }

  override def calculateNewPosition(): Unit = {
    positionVector = positionVector + directionVector
    shape = new Rectangle(positionVector.x toInt, positionVector.y toInt, 30, 30)
  }

  override def checkDestroy(): Unit = {
    if(positionVector.x <= 0 || positionVector.x >= maxX  || positionVector.y >= maxY) destroy()
  }
}
