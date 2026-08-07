package com.clocken.windcore;

import com.clocken.windcore.registry.WCBlocks;
import com.clocken.windcore.registry.WCItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Windcore implements ModInitializer {
	public static final String MOD_ID = "windcore";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		WCItems.initialize();
		WCBlocks.initialize();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
