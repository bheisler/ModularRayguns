package com.castlebravostudios.rayguns.items.batteries

import net.minecraft.item.Item
import com.castlebravostudios.rayguns.items.ScalaItem
import com.castlebravostudios.rayguns.items.MoreInformation
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.api.items.ItemModule
import com.castlebravostudios.rayguns.plugins.te.RFItemPowerConnector
import com.castlebravostudios.rayguns.plugins.ic2.IC2ItemPowerConnector
import com.castlebravostudios.rayguns.items.ChargableItem

class ItemBattery( id : Int, val battery : RaygunBattery ) extends ItemModule( id, battery ) with MoreInformation
  with ChargableItem with RFItemPowerConnector with IC2ItemPowerConnector {

  override def getAdditionalInfo(item : ItemStack, player : EntityPlayer) : Iterable[String] =
    List( battery.getChargeString( item ) )

  override def getDamage( item : ItemStack ) : Int = 1
  override def getDisplayDamage( item : ItemStack ) : Int = battery.getChargeDepleted(item)
  override def isDamaged( item : ItemStack ) = getDisplayDamage( item ) > 0

  override def getMaxDamage( item: ItemStack ) : Int = battery.maxCapacity

  def getChargeCapacity( item : ItemStack ) : Int = battery.maxCapacity
  def getChargeDepleted( item : ItemStack ) : Int = battery.getChargeDepleted( item )
  def setChargeDepleted( item : ItemStack, depleted : Int ) : Unit = battery.setChargeDepleted( item, depleted )
  def addCharge( item : ItemStack, delta : Int ) : Unit = battery.setChargeDepleted( item, delta )
}