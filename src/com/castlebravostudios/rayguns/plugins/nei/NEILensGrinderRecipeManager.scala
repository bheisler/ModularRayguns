package com.castlebravostudios.rayguns.plugins.nei

import java.awt.Rectangle

import com.castlebravostudios.rayguns.api.LensGrinderRecipe
import com.castlebravostudios.rayguns.api.LensGrinderRecipeRegistry
import com.castlebravostudios.rayguns.blocks.lensgrinder.LensGrinderGui

import codechicken.nei.NEIServerUtils

import codechicken.nei.recipe.ShapedRecipeHandler
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect
import net.minecraft.item.ItemStack

class NEILensGrinderRecipeManager extends ShapedRecipeHandler {

  override def loadCraftingRecipes( result : ItemStack ) : Unit = {
    for {
      recipe <- LensGrinderRecipeRegistry.recipes
      if ( NEIServerUtils.areStacksSameTypeCrafting( recipe.recipe.getRecipeOutput(), result ) )
    } this.arecipes.add( getShape( recipe ) )
  }

  override def loadCraftingRecipes( outputId : String, results : Object* ) : Unit = {
    if ( outputId == "LensGrinder" ) {
      for {
        recipe <- LensGrinderRecipeRegistry.recipes
      } this.arecipes.add( getShape( recipe ))
    }
    else super.loadCraftingRecipes( outputId, results:_* )
  }

  override def loadUsageRecipes( ingredient : ItemStack ) : Unit = {
    for {
      recipe <- LensGrinderRecipeRegistry.recipes
      if ( recipe.recipe.recipeItems.exists( stack =>
        NEIServerUtils.areStacksSameTypeCrafting( stack, ingredient ) ) )
    } this.arecipes.add( getShape( recipe ) )
  }

  override def loadTransferRects() : Unit = {
    transferRects.add(new RecipeTransferRect(new Rectangle(84, 23, 24, 18),
        NEIModularRaygunsConfig.recipeKey));
  }

  override def drawExtras( recipeIndex: Int ) : Unit = {
    drawProgressBar(84, 24, 176, 0, 24, 16, 48, 0);
  }

  override def getGuiClass() = classOf[LensGrinderGui]
  override def getGuiTexture() = "rayguns:textures/gui/container/lens_grinder.png"
  override def getRecipeName = "Lens Grinder"
  override def getOverlayIdentifier() = NEIModularRaygunsConfig.recipeKey

  private def getShape(recipe: LensGrinderRecipe) : CachedShapedRecipe = {
    new CachedShapedRecipe( recipe.recipe )
  }
}