package com.castlebravostudios.rayguns.items

import com.castlebravostudios.rayguns.items.accessories.ExtendedBattery
import com.castlebravostudios.rayguns.items.accessories.RefireCapacitor
import com.castlebravostudios.rayguns.items.accessories.SolarPanel
import com.castlebravostudios.rayguns.items.batteries.AdvancedBattery
import com.castlebravostudios.rayguns.items.batteries.BasicBattery
import com.castlebravostudios.rayguns.items.batteries.InfiniteBattery
import com.castlebravostudios.rayguns.items.batteries.UltimateBattery
import com.castlebravostudios.rayguns.items.bodies.FireflyBody
import com.castlebravostudios.rayguns.items.bodies.MantisBody
import com.castlebravostudios.rayguns.items.emitters.LaserEmitter
import com.castlebravostudios.rayguns.items.lenses.BeamLens
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.items.chambers.LaserChamber
import com.castlebravostudios.rayguns.items.emitters.HeatRayEmitter
import com.castlebravostudios.rayguns.items.chambers.HeatRayChamber

object Items {

  private var registeredItems = Map[Class[_], Item]()

  def apply[T <: Item]( c : Class[T] ) : Item = registeredItems( c )
  def apply[T <: Item]( implicit mf : Manifest[T] ) : Item = registeredItems( mf.runtimeClass )

  def registerItems : Unit = {
    registerItem( RayGun )
    registerItem( new BrokenGun( Config.brokenGun ) )

    registerItem( BasicBattery )
    registerItem( AdvancedBattery )
    registerItem( UltimateBattery )
    registerItem( InfiniteBattery )

    registerItem( PreciseLens )
    registerItem( WideLens )
    registerItem( BeamLens )

    registerItem( ExtendedBattery )
    registerItem( RefireCapacitor )
    registerItem( SolarPanel )

    registerItem( MantisBody )
    registerItem( FireflyBody )

    registerItem( LaserEmitter )
    registerItem( HeatRayEmitter )

    registerItem( LaserChamber )
    registerItem( HeatRayChamber )
  }

  private def registerItem( item : Item ) : Unit = {
    registeredItems += ( item.getClass() -> item )
  }
}