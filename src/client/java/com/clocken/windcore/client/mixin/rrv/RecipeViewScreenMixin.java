package com.clocken.windcore.client.mixin.rrv;

import cc.cassian.rrv.common.config.Configs;
import cc.cassian.rrv.common.overlay.itemlist.view.ItemViewOverlay;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeViewScreen.class)
public abstract class RecipeViewScreenMixin extends AbstractContainerScreen<RecipeViewMenu> {
    public RecipeViewScreenMixin(RecipeViewMenu menu, Inventory inventory, Component title, int imageWidth, int imageHeight) {
        super(menu, inventory, title, imageWidth, imageHeight);
    }

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;init()V", shift = At.Shift.AFTER))
    protected void windcore$init(CallbackInfo ci) {
        if (Configs.CLIENT_SETTINGS.isCenterRecipeScreen()) {
            this.topPos = (Minecraft.getInstance().getWindow().getGuiScaledHeight() - this.imageHeight) / 2;
        }
        if (ItemViewOverlay.INSTANCE.isEnabled() && !Configs.CLIENT_SETTINGS.isRightIndex() && Configs.CLIENT_SETTINGS.isRecipeBookTheme() && Configs.CLIENT_SETTINGS.isRecipeBookButton()) {
            this.leftPos = 177 + (width - imageWidth - 200) / 2;
        }
    }

    @Inject(method = "checkGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button;setPosition(II)V"))
    protected void windcore$checkGui(CallbackInfo ci) {
        if (Configs.CLIENT_SETTINGS.isCenterRecipeScreen()) {
            this.topPos = (Minecraft.getInstance().getWindow().getGuiScaledHeight() - this.imageHeight) / 2;
        }
        if (ItemViewOverlay.INSTANCE.isEnabled() && !Configs.CLIENT_SETTINGS.isRightIndex() && Configs.CLIENT_SETTINGS.isRecipeBookTheme() && Configs.CLIENT_SETTINGS.isRecipeBookButton()) {
            this.leftPos = 177 + (width - imageWidth - 200) / 2;
        }
    }

    @ModifyVariable(method = "updateRecipeTypeButtons", at = @At("STORE"), name = "xPos")
    private int windcore$xPos(int value) {
        return value + this.leftPos + this.imageWidth / 2 - this.width / 2;
    }
}
