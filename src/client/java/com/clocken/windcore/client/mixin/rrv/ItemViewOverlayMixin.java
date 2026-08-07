package com.clocken.windcore.client.mixin.rrv;

import cc.cassian.rrv.common.ReliableRecipeViewer;
import cc.cassian.rrv.common.config.Configs;
import cc.cassian.rrv.common.overlay.itemlist.AbstractRrvItemListOverlay;
import cc.cassian.rrv.common.overlay.itemlist.view.ItemViewOverlay;
import cc.cassian.rrv.common.overlay.itemlist.view.ReliableSpriteIconButton;
import cc.cassian.rrv.common.overlay.itemlist.view.SearchBar;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemViewOverlay.class)
public abstract class ItemViewOverlayMixin extends AbstractRrvItemListOverlay {
    protected ItemViewOverlayMixin(int defaultX, int defaultY, int defaultWidth, int defaultHeight) {
        super(defaultX, defaultY, defaultWidth, defaultHeight);
    }

    @Inject(method = "initForScreen", at = @At("TAIL"))
    private void windcore$initForScreen(AbstractContainerScreen<? extends AbstractContainerMenu> screen, InventoryPositionInfo invInfo, CallbackInfo ci) {
        if (Configs.CLIENT_SETTINGS.isRecipeBookTheme() && !Configs.CLIENT_SETTINGS.isRightIndex() && Configs.CLIENT_SETTINGS.isRecipeBookButton()) {
            this.width = 146;
            this.height = 166;

            this.x = (invInfo.screenWidth() - this.width) / 2 - 81;
            this.y = (invInfo.screenHeight() - this.height) / 2 - 20;

            this.itemStartX = this.x + 11;
            this.itemEndX = this.x + this.width - 13;
            this.itemStartY = this.y + 54;
            this.itemEndY = this.y + this.height + 18;
        }
    }

    @Inject(method = "extractBackground", at = @At("HEAD"), cancellable = true)
    protected void windcore$extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (this.fittingPerPage() != 0) {
            if (Configs.CLIENT_SETTINGS.isRecipeBookTheme()) {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ReliableRecipeViewer.of("recipe_book"), this.checkedX(), this.checkedY() + 20, this.checkedWidth() - 4, this.height, -1);
                ci.cancel();
            }
        }
    }

    @Redirect(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lcc/cassian/rrv/common/overlay/itemlist/view/ItemViewOverlay;fittingPerPage()I"))
    private int windcore$redirectFittingPerPage(ItemViewOverlay instance) {
        return 0;
    }

    @Shadow
    public ReliableSpriteIconButton back;
    @Shadow
    public ReliableSpriteIconButton next;

    @Inject(method = "createButtons", at = @At(value = "INVOKE", target = "Lcc/cassian/rrv/common/overlay/itemlist/view/ItemViewOverlay;updateButtons()V"))
    private void windcore$createButtons(InventoryPositionInfo info, CallbackInfo ci) {
        if (Configs.CLIENT_SETTINGS.isRecipeBookTheme() && !Configs.CLIENT_SETTINGS.isRightIndex() && Configs.CLIENT_SETTINGS.isRecipeBookButton()) {
            this.back.setPosition(this.itemEndX - 35, itemStartY - 21);
            this.next.setPosition(this.itemEndX - 18, itemStartY - 21);
        }
    }

    @Shadow
    private SearchBar searchbar;

    @Inject(method = "createSearchbarElement", at = @At("TAIL"))
    private void windcore$createSearchbarElement(InventoryPositionInfo info, CallbackInfo ci) {
        if (Configs.CLIENT_SETTINGS.isRecipeBookTheme() && !Configs.CLIENT_SETTINGS.isRightIndex()) {
            int x = itemStartX;
            int y = itemStartY - 21;
            this.searchbar.setRectangle(85, 16, x, y);
        }
    }

    // cancels creation of side panel & config buttons
    @Inject(method = "placeWidgets", at = @At(value = "INVOKE", target = "Lcc/cassian/rrv/common/overlay/OverlayManager;currentInfo()Lcc/cassian/rrv/common/overlay/AbstractRrvOverlay$InventoryPositionInfo;", shift = At.Shift.AFTER), cancellable = true)
    private void windcore$placeWidgets(CallbackInfo ci) {
        ci.cancel();
    }
}
