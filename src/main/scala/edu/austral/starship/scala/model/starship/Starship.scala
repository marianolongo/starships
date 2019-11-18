package edu.austral.starship.scala.model.starship

import edu.austral.starship.scala.base.collision.CollisionableType
import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.model.weapon.Weapon
import edu.austral.starship.scala.model.CollisionableObject
import edu.austral.starship.scala.model.player.Player

abstract class Starship extends CollisionableObject{

  override val collisionableType: CollisionableType.Value = CollisionableType.Starship
  var positionVector: Vector2
  var directionVector: Vector2
  var weapons: List[Weapon]
  var selectedWeapon: Int
  var player: Player

  def fire(): Boolean
  def crash(){player.takeLife()}
  def setPositionVector(vector2: Vector2) {positionVector = vector2}
  def setDirectionVector(vector2: Vector2) {directionVector = vector2}
  def changeWeapon()
}
