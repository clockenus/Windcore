package com.clocken.windcore.block;

import com.clocken.windcore.registry.WCBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

public class PebbleBlock extends Block {

    public PebbleBlock(final BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(PEBBLES, 1));
    }

    public static final MapCodec<PebbleBlock> CODEC = simpleCodec(PebbleBlock::new);
    public static final IntegerProperty PEBBLES;
    public static final int MIN_PEBBLES = 1;
    public static final int MAX_PEBBLES = 3;
    private static final VoxelShape SHAPE_SINGLE;
    private static final VoxelShape SHAPE_MULTIPLE;

    public @NonNull MapCodec<PebbleBlock> codec() {
        return CODEC;
    }

    @Override
    protected @NonNull VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return switch (state.getValue(PEBBLES)) {
            case MIN_PEBBLES -> SHAPE_SINGLE;
            default -> SHAPE_MULTIPLE;
        };
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PEBBLES);
    }

    @Override
    protected @NonNull InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (itemStack.getItem() == WCBlocks.PEBBLE.asItem()) {
            int pebbles = state.getValue(PEBBLES);

            if (pebbles >= MAX_PEBBLES) {
                return InteractionResult.PASS;
            } else {
                level.setBlockAndUpdate(pos, state.setValue(PEBBLES, pebbles + 1));
                level.playSound(player, pos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected @NonNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!player.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else {
            int pebbles = state.getValue(PEBBLES);

            if (pebbles <= MIN_PEBBLES) {
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            } else {
                level.setBlockAndUpdate(pos, state.setValue(PEBBLES, pebbles - 1));
            }

            level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, 2.5F);
            player.addItem(WCBlocks.PEBBLE.asItem().getDefaultInstance());
            return InteractionResult.SUCCESS;
        }
    }

    public IntegerProperty getPebblesProperty() {
        return PEBBLES;
    }

    static {
        PEBBLES = IntegerProperty.create("pebbles", MIN_PEBBLES, MAX_PEBBLES);
        SHAPE_SINGLE = Block.box(4.0F, 0.0F, 4.0F, 12.0F, 4.0F, 12.0F);
        SHAPE_MULTIPLE = Block.column(16.0F, 0.0F, 5.0F);
    }
}
