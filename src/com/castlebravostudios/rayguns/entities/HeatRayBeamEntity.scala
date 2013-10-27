package com.castlebravostudios.rayguns.entities

import net.minecraft.world.World
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.EntityDamageSource
import net.minecraft.util.EnumMovingObjectType
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.block.Block


class HeatRayBeamEntity( world : World ) extends BaseBeamEntity(world) {

  override def colorRed : Float = 1.0f
  override def colorBlue : Float = 0.0f
  override def colorGreen : Float = 0.5f

  def hitEntity( hit : Entity ) : Unit = {
    hit.setFire(20)
    hit.attackEntityFrom(new EntityDamageSource("heatray", shooter), 2)
  }

  def hitBlock( hitX : Int, hitY : Int, hitZ : Int, side : Int ) = {
    val (x, y, z) = adjustCoords( hitX, hitY, hitZ, side )

    if ( !shooter.isInstanceOf[EntityPlayer] ||
         shooter.asInstanceOf[EntityPlayer].canPlayerEdit(x, y, z, side, null) ) {
      if ( world.isAirBlock(x, y, z) ) {
        world.setBlock(x, y, z, Block.fire.blockID)
      }
    }
  }

  def adjustCoords( x : Int, y : Int, z : Int, side : Int ) : (Int, Int, Int) = side match {
    case 0 => (x, y - 1, z)
    case 1 => (x, y + 1, z)
    case 2 => (x, y, z - 1)
    case 3 => (x, y, z + 1)
    case 4 => (x - 1, y, z)
    case 5 => (x + 1, y, z)
  }

  override def onImpact( pos : MovingObjectPosition ) {

    pos.typeOfHit match {
      case EnumMovingObjectType.ENTITY => hitEntity( pos.entityHit )
      case EnumMovingObjectType.TILE => hitBlock( pos.blockX, pos.blockY, pos.blockZ, pos.sideHit )
    }

    for ( _ <- 0 until 4 ) {
      this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
    }

    super.onImpact(pos)
  }
}