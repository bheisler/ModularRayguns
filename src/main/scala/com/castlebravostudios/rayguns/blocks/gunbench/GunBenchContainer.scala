/*
 * Copyright (c) 2014, Brook 'redattack34' Heisler
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the ModularRayguns team nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.castlebravostudios.rayguns.blocks.gunbench

import com.castlebravostudios.rayguns.api.items.RaygunAccessory
import com.castlebravostudios.rayguns.api.items.RaygunBattery
import com.castlebravostudios.rayguns.api.items.RaygunBody
import com.castlebravostudios.rayguns.api.items.RaygunChamber
import com.castlebravostudios.rayguns.api.items.RaygunLens
import com.castlebravostudios.rayguns.blocks.BaseContainer
import com.castlebravostudios.rayguns.blocks.GuiBlockSlot
import com.castlebravostudios.rayguns.items.misc.RayGun
import GunBenchTileEntity.ACC_SLOT
import GunBenchTileEntity.BATTERY_SLOT
import GunBenchTileEntity.BODY_SLOT
import GunBenchTileEntity.CHAMBER_SLOT
import GunBenchTileEntity.LENS_SLOT
import GunBenchTileEntity.OUTPUT_SLOT
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.api.items.ItemModule

class GunBenchContainer( inventoryPlayer : InventoryPlayer, entity : GunBenchTileEntity )
  extends BaseContainer( inventoryPlayer, entity ) {

  addSlotToContainer( new GuiBlockSlot( entity, BODY_SLOT,     37, 19 ) )
  addSlotToContainer( new GuiBlockSlot( entity, LENS_SLOT,     37, 37 ) )
  addSlotToContainer( new GuiBlockSlot( entity, CHAMBER_SLOT, 106, 19 ) )
  addSlotToContainer( new GuiBlockSlot( entity, BATTERY_SLOT, 106, 37 ) )
  addSlotToContainer( new GuiBlockSlot( entity, ACC_SLOT,      71, 55 ) )
  addSlotToContainer( new GuiBlockSlot( entity, OUTPUT_SLOT,  147, 57 ) )

  addPlayerInventory()

  val lastCustomIndex = OUTPUT_SLOT

  protected override def transferStackToCustomSlots( player : EntityPlayer, slot : Int, stackInSlot: ItemStack ) : Boolean = {
    val targetSlot = Item.itemsList(stackInSlot.itemID) match {
      case RayGun => OUTPUT_SLOT
      case item : ItemModule => item.module match {
        case _: RaygunBody => BODY_SLOT
        case _: RaygunLens => LENS_SLOT
        case _: RaygunChamber => CHAMBER_SLOT
        case _: RaygunBattery => BATTERY_SLOT
        case _: RaygunAccessory => ACC_SLOT
      }
      case _ => return false
    }

    if ( entity.isItemValidForSlot(targetSlot, stackInSlot ) ) {
      mergeItemStack( stackInSlot, targetSlot, targetSlot + 1, false );
    }
    else false
  }
}