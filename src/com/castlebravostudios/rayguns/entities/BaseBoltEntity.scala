package com.castlebravostudios.rayguns.entities

import scala.annotation.tailrec
import scala.collection.JavaConversions.asScalaBuffer
import com.castlebravostudios.rayguns.entities.effects.BaseEffect
import com.castlebravostudios.rayguns.utils.BlockPos
import com.castlebravostudios.rayguns.utils.RaytraceUtils
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.IProjectile
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumMovingObjectType
import net.minecraft.util.MathHelper
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.Vec3
import net.minecraft.world.World
import net.minecraft.util.ResourceLocation
import com.castlebravostudios.rayguns.utils.Extensions._
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteArrayDataOutput
import com.castlebravostudios.rayguns.api.EffectRegistry

/**
 * Base class for beam entities. Most of this code is a poor translation
 * of the source of EntityThrowable, since I needed to modify something deep in
 * onUpdate. Not coincidentally, most of this code is really non-idiomatic Scala
 * and should not be taken as an example.
 */
class BaseBoltEntity( world : World ) extends BaseShootable( world ) with IProjectile {

  var depletionRate : Double = 0.05d

  def pitchOffset : Float = 0.5f
  def velocityMultiplier : Float = 1.5f

  protected var hitBlocks = Set[BlockPos]()
  protected var hitEntities = Set[Entity]()

  override def onUpdate() : Unit = {
    this.lastTickPosX = this.posX
    this.lastTickPosY = this.posY
    this.lastTickPosZ = this.posZ
    super.onUpdate()

    val startPos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ)
    val endPos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ)

    val hits = RaytraceUtils.rayTrace(world, this, startPos, endPos)(
        ( block, metadata, pos ) => effect.canCollideWithBlock( this, block, metadata, pos ) && !hitBlocks.contains(pos),
        ( entity ) => effect.canCollideWithEntity(this, entity ) && !hitEntities.contains( entity ) )
    applyHitsUntilStop(hits)

    this.posX += this.motionX
    this.posY += this.motionY
    this.posZ += this.motionZ

    if (this.isInWater())
    {
        for ( _ <- 0 until 4 )
        {
            val f4 = 0.25F
            this.worldObj.spawnParticle("bubble",
                this.posX - this.motionX * f4.doubleValue,
                this.posY - this.motionY * f4.doubleValue,
                this.posZ - this.motionZ * f4.doubleValue,
                this.motionX, this.motionY, this.motionZ)
        }
    }

    this.setPosition(this.posX, this.posY, this.posZ)

    charge -= depletionRate
    if ( charge <= 0 ) {
      setDead()
    }
  }

  /**
   * Applies the collisions in hits until the beam signals stop or hits is empty.
   */
  @tailrec
  private def applyHitsUntilStop( hits : Stream[MovingObjectPosition] ) : Unit =  hits match {
    case h #:: hs => if ( onImpact(h) ) () else applyHitsUntilStop(hs)
    case _ => ()
  }

  override def setSize( width : Float, height : Float ) = super.setSize( width, height )

  def onImpact( pos : MovingObjectPosition ) : Boolean = {
    effect.createImpactParticles( this, posX, posY, posZ )
    val shouldDie = pos.typeOfHit match {
      case EnumMovingObjectType.ENTITY => {
        hitEntities += pos.entityHit
        effect.hitEntity( this, pos.entityHit )
      }
      case EnumMovingObjectType.TILE => {
        hitBlocks += BlockPos(pos.blockX, pos.blockY, pos.blockZ)
        effect.hitBlock( this, pos.blockX, pos.blockY, pos.blockZ, pos.sideHit )
      }
    }

    if ( shouldDie ) setDead
    shouldDie
  }

  override def setDead() {
    effect match {
      case e : TriggerOnDeath => e.triggerAt(this, posX, posY, posZ)
      case _ => ()
    }
    super.setDead
  }

  override def writeEntityToNBT( tag : NBTTagCompound ) : Unit = {
    super.writeEntityToNBT(tag)
    tag.setDouble("depletionRate", depletionRate)
  }

  override def readEntityFromNBT( tag : NBTTagCompound ) : Unit = {
    super.readEntityFromNBT(tag)
    depletionRate = tag.getDouble("depletionRate")
  }

  override def writeSpawnData( out : ByteArrayDataOutput ) : Unit = {
    super.writeSpawnData(out)
    out.writeDouble( depletionRate )
  }

  override def readSpawnData( in : ByteArrayDataInput ) : Unit = {
    super.readSpawnData(in)
    depletionRate = in.readDouble()
  }

  override def isInRangeToRenderDist(limit : Double) : Boolean = {
    var d1 = this.boundingBox.getAverageEdgeLength() * 4.0D
    d1 *= 64.0D
    return limit < d1 * d1
  }

  override def setThrowableHeading(xIn : Double, yIn : Double, zIn : Double, velocityMult : Float, scatterMult : Float ) : Unit = {
    var x = xIn
    var y = yIn
    var z = zIn
    val f2 = MathHelper.sqrt_double(x * x + y * y + z * z)
    x /= f2.doubleValue
    y /= f2.doubleValue
    z /= f2.doubleValue
    x += this.rand.nextGaussian() * 0.007499999832361937D * scatterMult.doubleValue
    y += this.rand.nextGaussian() * 0.007499999832361937D * scatterMult.doubleValue
    z += this.rand.nextGaussian() * 0.007499999832361937D * scatterMult.doubleValue
    x *= velocityMult.doubleValue
    y *= velocityMult.doubleValue
    z *= velocityMult.doubleValue
    this.motionX = x
    this.motionY = y
    this.motionZ = z
    val f3 = MathHelper.sqrt_double(x * x + z * z)
    this.rotationYaw = (Math.atan2(x, z) * 180.0D / Math.PI).floatValue()
    this.prevRotationYaw = rotationYaw
    this.rotationPitch = (Math.atan2(y, f3.doubleValue) * 180.0D / Math.PI).floatValue()
    this.prevRotationPitch = rotationPitch
  }

  override def setPositionAndRotation2( x: Double, y : Double, z : Double, yaw : Float, pitch : Float, par9 : Int ) : Unit = {
    setPosition(x, y, z)
    setRotation(yaw, pitch)
  }

}
