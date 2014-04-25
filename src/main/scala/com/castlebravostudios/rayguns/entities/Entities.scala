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

package com.castlebravostudios.rayguns.entities

import com.castlebravostudios.rayguns.entities.effects.LightningBeamEntity
import com.castlebravostudios.rayguns.entities.effects.LightningBoltEntity
import com.castlebravostudios.rayguns.entities.effects.MatterTransporterBeamEntity
import com.castlebravostudios.rayguns.entities.effects.MatterTransporterBoltEntity

import com.castlebravostudios.rayguns.mod.ModularRayguns

import cpw.mods.fml.common.registry.EntityRegistry

object Entities {

  def registerEntities : Unit = {
    EntityRegistry.registerModEntity(classOf[BaseBoltEntity], "BoltEntity", 1, ModularRayguns, 40, 1, true)
    EntityRegistry.registerModEntity(classOf[BaseBeamEntity], "BeamEntity", 2, ModularRayguns, 40, 1, true)
    EntityRegistry.registerModEntity(classOf[LightningBoltEntity], "LightningBolt", 3, ModularRayguns, 40, 1, true)
    EntityRegistry.registerModEntity(classOf[LightningBeamEntity], "LightningBeam", 4, ModularRayguns, 40, 1, true)
    EntityRegistry.registerModEntity(classOf[MatterTransporterBoltEntity],
        "MatterTransporterBolt", 5, ModularRayguns, 40, 1, true)
    EntityRegistry.registerModEntity(classOf[MatterTransporterBeamEntity],
        "MatterTransporterBeam", 6, ModularRayguns, 40, 1, true)
  }
}