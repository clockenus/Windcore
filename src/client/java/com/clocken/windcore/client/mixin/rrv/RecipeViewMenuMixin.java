package com.clocken.windcore.client.mixin.rrv;

import cc.cassian.rrv.common.config.Configs;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RecipeViewMenu.class)
public abstract class RecipeViewMenuMixin {

    @ModifyReturnValue(method = "getHeight", at = @At("RETURN"))
    private int windcore$getHeight(int original) {
        if (Configs.CLIENT_SETTINGS.isCenterRecipeScreen()) {
            return Math.max(166, original);
        }
        return original;
    }
}
