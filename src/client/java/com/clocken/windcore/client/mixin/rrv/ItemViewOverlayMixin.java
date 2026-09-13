package com.clocken.windcore.client.mixin.rrv;

import cc.cassian.rrv.common.ReliableRecipeViewer;
import cc.cassian.rrv.common.config.Configs;
import cc.cassian.rrv.common.overlay.itemlist.AbstractRrvItemListOverlay;
import cc.cassian.rrv.common.overlay.itemlist.view.ItemViewOverlay;
import cc.cassian.rrv.common.overlay.itemlist.view.ReliableSpriteIconButton;
import cc.cassian.rrv.common.overlay.itemlist.view.SearchBar;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
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
    private void windcore$initForScreen(Screen screen, InventoryPositionInfo invInfo, CallbackInfo ci) {
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

    @Override
    public void createButtons(Component title, int buttonStart, int buttonEnd, int classicButtonStart, int classicButtonEnd) {
        back = new ReliableSpriteIconButton(16, Component.translatable("rrv.previous_page"), 10, ReliableRecipeViewer.of("back"), ReliableRecipeViewer.of("back"), ReliableRecipeViewer.of("back_disabled"), this::prevPage);
        next = new ReliableSpriteIconButton(16, Component.translatable("rrv.next_page"), 10, ReliableRecipeViewer.of("next"), ReliableRecipeViewer.of("next"), ReliableRecipeViewer.of("next_disabled"), this::nextPage);

        int buttonY = this.itemStartY - 21;
        buttonStart = this.itemEndX - 35;
        buttonEnd = this.itemEndX - 18;

        back.setPosition(buttonStart, buttonY);
        next.setPosition(buttonEnd, buttonY);

        if (currentlyIndexing()) {
            back.visible = false;
            next.visible = false;
        }

        updateButtons(title);
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
