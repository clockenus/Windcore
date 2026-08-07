package com.clocken.windcore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Blocks.class)
public class BlocksMixin {

    // logs require tool for drop
    @ModifyReturnValue(method = "logProperties", at = @At("RETURN"))
    private static BlockBehaviour.Properties windcore$logProperties(BlockBehaviour.Properties properties) {
        return properties.requiresCorrectToolForDrops();
    }
}
