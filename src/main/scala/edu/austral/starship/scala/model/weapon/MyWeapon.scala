package edu.austral.starship.scala.model.weapon

import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.model.bullet.Bullet
import edu.austral.starship.scala.model.starship.Starship

case class MyWeapon(ship: Starship) extends Weapon{

  var bullets: List[Bullet] = List()
  override val starship: Starship = ship

  override def fire(direction: Vector2, position: Vector2): Boolean = {
    if(coolingTime >= 30){
      val correctPosition = Vector2(position.x + 10, position.y - 20)
      val correctDirection = Vector2(0, -20)
      val bullet = Bullet(this, correctPosition, correctDirection, 1000, 700, 20, 20, pierce = false)
      addBullet(bullet)
      coolingReturn()
      true
    }
    else false
  }
}
