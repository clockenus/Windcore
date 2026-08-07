package com.clocken.windcore.client.datagen;

import com.clocken.windcore.block.PebbleBlock;
import com.clocken.windcore.registry.WCBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static com.clocken.windcore.block.PebbleBlock.PEBBLES;

public class WCBlockLootTableProvider extends FabricBlockLootSubProvider {
    protected WCBlockLootTableProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        add(WCBlocks.PEBBLE.defaultBlockState().setValue(PEBBLES, 1).getBlock(),
                block -> createPebbleTable(WCBlocks.PEBBLE));

    }

    protected LootTable.Builder createPebbleTable(Block block) {
        if (block instanceof PebbleBlock pebbleBlock) {
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(this.applyExplosionDecay(block, LootItem.lootTableItem(block).apply(IntStream.rangeClosed(1, 3).boxed().toList(), (count) -> SetItemCountFunction.setCount(ConstantValue.exactly((float) count)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(net.minecraft.advancements.criterion.StatePropertiesPredicate.Builder.properties().hasProperty(pebbleBlock.getPebblesProperty(), count)))))));
        } else {
            return noDrop();
        }
    }
}