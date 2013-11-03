package com.castlebravostudios.rayguns.items.chambers

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.items.emitters.FortifiedSunlightEmitter
import com.castlebravostudios.rayguns.items.lenses.PreciseLens
import com.castlebravostudios.rayguns.items.lenses.WideLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.BoltUtils
import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.utils.RecipeRegisterer
import net.minecraft.item.Item
import com.castlebravostudios.rayguns.entities.effects.FortifiedSunlightBoltEntity
import com.castlebravostudios.rayguns.entities.effects.FortifiedSunlightBeamEntity
import com.castlebravostudios.rayguns.items.lenses.PreciseBeamLens
import com.castlebravostudios.rayguns.utils.BeamUtils

object FortifiedSunlightChamber extends Item( Config.chamberFortifiedSunlight ) with ItemChamber {

  val moduleKey = "FortifiedSunlightChamber"
  val powerModifier = 4.0
  register
  setUnlocalizedName("rayguns.FortifiedSunlightChamber")
  setTextureName("rayguns:chamber_fortified_sunlight")

  RecipeRegisterer.registerTier1Chamber(this, FortifiedSunlightEmitter)

  BeamRegistry.register({
    case GunComponents(_, FortifiedSunlightChamber, _, None, _) => { (world, player) =>
      BoltUtils.spawnNormal( world, new FortifiedSunlightBoltEntity(world), player )
    }
    case GunComponents(_, FortifiedSunlightChamber, _, Some(PreciseLens), _ ) => { (world, player) =>
      BoltUtils.spawnPrecise( world, new FortifiedSunlightBoltEntity( world ), player )
    }
    case GunComponents(_, FortifiedSunlightChamber, _, Some(WideLens), _ ) => { (world, player) =>
      BoltUtils.spawnScatter(world, player, 9, 5 ){ () =>
        new FortifiedSunlightBoltEntity(world)
      }
    }
    case GunComponents(_, FortifiedSunlightChamber, _, Some(PreciseBeamLens), _ ) => { (world, player) =>
      BeamUtils.spawnSingleShot( new FortifiedSunlightBeamEntity(world), world, player )
    }
  })
}