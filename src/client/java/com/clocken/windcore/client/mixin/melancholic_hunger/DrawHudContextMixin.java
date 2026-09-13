package com.clocken.windcore.client.mixin.melancholic_hunger;

import antigers.melancholic_hunger.hud.DrawHudContext;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DrawHudContext.class)
public class DrawHudContextMixin {
    @ModifyReturnValue(method = "getArmorBarY", at = @At("RETURN"))
    private static int windwright$getArmorBarY(int original) {
        return original - 2;
    }

    @ModifyReturnValue(method = "getHealthBarY", at = @At("RETURN"))
    private static int windwright$getHealthBarY(int original) {
        return original - 2;
    }
}
