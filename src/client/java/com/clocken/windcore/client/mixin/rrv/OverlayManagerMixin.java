package com.clocken.windcore.client.mixin.rrv;

import cc.cassian.rrv.common.overlay.OverlayManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OverlayManager.class)
public abstract class OverlayManagerMixin {
    @Inject(method = "toggleOverlays", at = @At("TAIL"))
    private static void windcore$afterToggle(CallbackInfo ci) {
        Screen screen = Minecraft.getInstance().screen;
        assert screen != null;
        ((ScreenInvoker) screen).windcore$rebuildWidgets();
    }
}
