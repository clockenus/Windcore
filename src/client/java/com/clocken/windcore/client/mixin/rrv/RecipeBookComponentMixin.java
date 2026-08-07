package com.clocken.windcore.client.mixin.rrv;

import cc.cassian.rrv.common.config.Configs;
import cc.cassian.rrv.common.overlay.itemlist.view.ItemViewOverlay;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = RecipeBookComponent.class)
public abstract class RecipeBookComponentMixin {

    @Inject(method = "updateScreenPosition(II)I", at = @At("HEAD"), cancellable = true)
    private void windcore$updateScreenPosition(int width, int imageWidth, CallbackInfoReturnable<Integer> cir) {
        if (ItemViewOverlay.INSTANCE.isEnabled() && Configs.CLIENT_SETTINGS.isRecipeBookTheme() && !Configs.CLIENT_SETTINGS.isRightIndex() && Configs.CLIENT_SETTINGS.isRecipeBookButton()) {
            int i = 177 + (width - imageWidth - 200) / 2;
            cir.setReturnValue(i);
        }
    }
}
