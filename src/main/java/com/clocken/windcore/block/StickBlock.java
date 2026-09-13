package com.clocken.windcore.block;

import com.clocken.windcore.registry.WCBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

public class StickBlock extends Block {

    public StickBlock(final Properties properties) {
        super(properties);
    }

    public static final MapCodec<StickBlock> CODEC = simpleCodec(StickBlock::new);
    public @NonNull MapCodec<StickBlock> codec() {
        return CODEC;
    }

    @Override
    protected @NonNull VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return Block.column(16.0F, 0.0F, 3.0F);
    }

    @Override
    protected @NonNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!player.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, 2.5F);

            player.addItem(WCBlocks.PEBBLE.asItem().getDefaultInstance());
            return InteractionResult.SUCCESS;
        }
    }
}
