package com.clocken.windcore.client.mixin.rrv;

import cc.cassian.rrv.common.config.instances.ClientConfig;
import cc.cassian.rrv.common.config.options.OverlayDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConfig.class)
public abstract class ClientConfigMixin {
    @Shadow
    private OverlayDisplay showItemView;
    @Shadow
    private boolean showProgressBar;
    @Shadow
    private boolean rightIndex;
    @Shadow
    private boolean centerRecipeScreen;
    @Shadow
    private boolean recipeBookButton;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void windcore$init(CallbackInfo ci) {
        this.showItemView = OverlayDisplay.DISABLED;
        this.showProgressBar = false;
        this.rightIndex = false;
        this.centerRecipeScreen = true;
        this.recipeBookButton = true;
    }
}
