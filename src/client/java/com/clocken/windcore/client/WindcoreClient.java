package com.clocken.windcore.client;

import cc.cassian.rrv.common.config.Configs;
import cc.cassian.rrv.common.config.options.OverlayDisplay;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;

public class WindcoreClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
			if (!(screen instanceof AbstractRecipeBookScreen || screen instanceof RecipeViewScreen)) {
				Configs.CLIENT_SETTINGS.setShowItemView(OverlayDisplay.DISABLED);
			}
		});
	}
}