package com.clocken.windcore.client.mixin.rrv;

import cc.cassian.rrv.common.config.Configs;
import cc.cassian.rrv.common.overlay.AbstractRrvOverlay;
import cc.cassian.rrv.common.overlay.ItemSlot;
import cc.cassian.rrv.common.overlay.itemlist.AbstractRrvItemListOverlay;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(AbstractRrvItemListOverlay.class)
public abstract class AbstractRrvItemListOverlayMixin extends AbstractRrvOverlay {
    protected AbstractRrvItemListOverlayMixin(int defaultX, int defaultY, int defaultWidth, int defaultHeight) {
        super(defaultX, defaultY, defaultWidth, defaultHeight);
    }

    @Shadow
    protected int itemStartX;
    @Shadow
    protected int itemStartY;
    @Shadow
    protected int itemEndX;
    @Shadow
    protected int itemEndY;
    @Shadow
    protected int startIndex;
    @Shadow
    private int fittingPerPage;
    @Shadow
    protected List<ItemStack> availableItems;

    @Inject(method = "updateSlots", at = @At("HEAD"), cancellable = true)
    private void windcore$updateSlots(CallbackInfo ci) {
        if (!Configs.CLIENT_SETTINGS.isRecipeBookTheme() || Configs.CLIENT_SETTINGS.isRightIndex()) return;
        this.itemSlots().clear();
        int currentStackPos = this.startIndex;

        int slotSize = 24;
        for (int y = this.itemStartY; y <= this.itemEndY - slotSize; y += slotSize) {
            for (int x = this.itemStartX; x <= this.itemEndX - slotSize; x += slotSize) {
                if (currentStackPos < this.availableItems.size()) {
                    this.itemSlots().add(new ItemSlot(this.availableItems.get(currentStackPos), x, y));
                }
                currentStackPos++;
            }
        }

        this.fittingPerPage = currentStackPos - this.startIndex;
        ci.cancel();
    }

    @ModifyConstant(method = "updateSlots", constant = @Constant(intValue = 19))
    private int windcore$updateSlots19(int original) {
        return 24;
    }
}
