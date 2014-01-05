package com.castlebravostudios.rayguns.items.bodies

import com.castlebravostudios.rayguns.api.ModuleRegistry

import com.castlebravostudios.rayguns.api.items.ItemBody
import com.castlebravostudios.rayguns.mod.Config

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object MantisBody extends Item( Config.mantisBody ) with ItemBody {
  val moduleKey = "MantisBody"
  val powerModifier = 1.0
  val nameSegmentKey = "rayguns.MantisBody.segment"

  setUnlocalizedName("rayguns.MantisBody")
  setTextureName("rayguns:body_mantis")

  ModuleRegistry.registerModule(this)
  GameRegistry.addRecipe( new ItemStack( this, 1 ),
    "G  ",
    "IGI",
    " II",
    'G' : Character, Item.ingotGold,
    'I' : Character, Item.ingotIron )
}