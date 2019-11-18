package edu.austral.starship.scala.model.starship

import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.model.weapon.Weapon
import edu.austral.starship.scala.model.CollisionableObject
import edu.austral.starship.scala.model.player.Player

abstract class Starship extends CollisionableObject{

  var positionVector: Vector2
  var directionVector: Vector2
  var weapon: Weapon
  var player: Player

  def fire()
  def crash(){player.takeLife()}
  def setPositionVector(vector2: Vector2) {positionVector = vector2}
  def setDirectionVector(vector2: Vector2) {directionVector = vector2}
}
