package com.clocken.windcore.client.mixin.rrv;

import cc.cassian.rrv.common.recipe.inventory.RecipeViewScreen;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeViewScreen.class)
public abstract class RecipeViewScreenMixin extends Screen {

    protected RecipeViewScreenMixin(Component title) {
        super(title);
    }

    @Shadow
    protected int imageWidth;
    @Shadow
    protected int imageHeight;
    @Shadow
    protected int leftPos;
    @Shadow
    protected int topPos;
    @Final
    @Shadow
    private static Identifier RECIPE_SCREEN;
    @Shadow
    private Button prevRecipe, nextRecipe;
    @Shadow
    private Button prevTypePage, nextTypePage;

    @Inject(method = "init", at = @At(
            value = "INVOKE",
            target = "Lcc/cassian/rrv/common/recipe/inventory/RecipeViewScreen;checkGui()V")
    )
    protected void windcore$init(CallbackInfo ci) {
        this.topPos = (Minecraft.getInstance().getWindow().getGuiScaledHeight() - this.imageHeight) / 2;
        this.leftPos = 177 + (width - imageWidth - 200) / 2;
    }

    @Inject(method = "checkGui", at = @At(
            value = "INVOKE",
            target = "Lcc/cassian/rrv/common/recipe/inventory/RecipeViewMenu;getClientRecipeType()Lcc/cassian/rrv/api/recipe/ReliableClientRecipeType;")
    )
    protected void windcore$checkGui(CallbackInfo ci) {
        this.topPos = (Minecraft.getInstance().getWindow().getGuiScaledHeight() - this.imageHeight) / 2;
        this.leftPos = 177 + (width - imageWidth - 200) / 2;

        this.prevRecipe.setPosition(this.leftPos + 8, this.topPos + 6);
        this.nextRecipe.setPosition(this.leftPos + this.imageWidth - 8 - 12, this.topPos + 6);

        this.prevTypePage.setPosition(this.leftPos + 8, this.topPos - 18);
        this.nextTypePage.setPosition(this.leftPos + this.imageWidth - 8 - 8, this.topPos - 18);
    }

    @ModifyVariable(method = "updateRecipeTypeButtons", at = @At("STORE"), name = "xPos")
    private int windcore$xPos(int xPos) {
        return xPos + this.leftPos + this.imageWidth / 2 - this.width / 2;
    }

    @Redirect(method = "extractBackground", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIII)V")
    )
    private void windcore$blitSprite(GuiGraphicsExtractor guiGraphics, RenderPipeline renderPipeline, Identifier location, int x, int y, int width, int height, int color) {
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, RECIPE_SCREEN, leftPos, topPos, imageWidth, imageHeight, -1);
    }
}
