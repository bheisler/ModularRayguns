package com.castlebravostudios.rayguns.plugins.te

import cofh.api.energy.IEnergyHandler
import com.castlebravostudios.rayguns.blocks.PoweredBlock
import cpw.mods.fml.common.Optional
import com.castlebravostudios.rayguns.mod.Config
import net.minecraftforge.common.ForgeDirection

@Optional.Interface(iface="cofh.api.energy.IEnergyHandler", modid="CoFHCore", striprefs=true)
trait RFBlockPowerConnector extends IEnergyHandler {
  self : PoweredBlock =>

  val rfPowerMultiplier = Config.rfPowerMultiplier

  def receiveEnergy( from : ForgeDirection, maxReceive : Int, simulate : Boolean ) : Int = {
    val maxReceivable = (maxReceive / rfPowerMultiplier).toInt
    val capacity = chargeCapacity - chargeStored
    val energyExtracted = Math.min( capacity, Math.min( maxReceivable, maxChargeInput ) )

    if ( !simulate ) {
      addCharge( energyExtracted )
    }

    if ( maxReceive == 1 && simulate ) 1 else (energyExtracted * rfPowerMultiplier).toInt
  }

  def extractEnergy( from : ForgeDirection, maxExtract : Int, simulate : Boolean ) : Int = 0

  def canInterface( from : ForgeDirection ) = true

  def getEnergyStored( from : ForgeDirection ) = ( chargeStored * rfPowerMultiplier ).toInt
  def getMaxEnergyStored( from : ForgeDirection) = ( chargeCapacity * rfPowerMultiplier ).toInt
}