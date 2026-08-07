package com.clocken.windcore.client.mixin.melancholic_hunger;

import com.bawnorton.mixinsquared.TargetHandler;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = Gui.class, priority = 1500)
public abstract class GuiMixin {
    @TargetHandler(
            mixin = "antigers.melancholic_hunger.hud.mixin.GuiMixin",
            name = "melancholic_hunger$getDrawHudContext"
    )
    @ModifyConstant(
            method = "@MixinSquared:Handler",
            constant = @Constant(intValue = 7)
    )
    private int windcore$melancholic_hunger$getDrawHudContext(int original) {
        return 5;
    }

    @TargetHandler(
            mixin = "antigers.melancholic_hunger.hud.mixin.GuiMixin",
            name = "melancholic_hunger$moveMountHealthBar"
    )
    @ModifyConstant(
            method = "@MixinSquared:Handler",
            constant = @Constant(intValue = 7)
    )
    private int windcore$melancholic_hunger$moveMountHealthBar(int original) {
        return 5;
    }
}
