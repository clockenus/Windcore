package com.clocken.windcore.client.mixin.rrv;

import cc.cassian.rrv.common.overlay.ItemSlot;
import com.clocken.windcore.Windcore;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemSlot.class)
public abstract class ItemSlotMixin {
    @Final
    @Shadow
    private int x;
    @Final
    @Shadow
    private int y;

    @Inject(method = "extractRenderState", at = @At("HEAD"))
    private void windcore$slotBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, Windcore.id("rrv_slot"), this.x, this.y, 24, 24);
    }

    @ModifyConstant(method = "isMouseOver", constant = @Constant(intValue = 19))
    private int windcore$isMouseOver19(int original) {
        return 24;
    }

    @ModifyConstant(method = "extractRenderState", constant = @Constant(intValue = 19))
    private int windcore$extractRenderState19_0(int original) {
        return 23;
    }

    @ModifyConstant(method = "extractRenderState", constant = @Constant(intValue = 32))
    private int windcore$extractRenderState32(int original) {
        return 64;
    }

    @ModifyConstant(method = "extractRenderState", constant = @Constant(intValue = 2))
    private int windcore$extractRenderState2(int original) {
        return 3;
    }
}
