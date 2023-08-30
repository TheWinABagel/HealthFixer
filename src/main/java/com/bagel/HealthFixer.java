package com.bagel;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.commands.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Float.NaN;

public class HealthFixer implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("healthfixer");

	@Override
	public void onInitialize() {
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			if (handler.player != null){
				if (Float.isNaN(handler.player.getAbsorptionAmount())) {
					handler.player.setAbsorptionAmount(0);
					LOGGER.warn("Player {} had invalid Absorption Amount, fixing!", handler.player);
				}
				if (Float.isNaN(handler.player.getHealth())) {
					handler.player.setHealth(10);
					LOGGER.warn("Player {} had invalid health, fixing!", handler.player);
				}
			}
		});

		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
			if (handler.player != null){
				if (Float.isNaN(handler.player.getAbsorptionAmount())) {
					handler.player.setAbsorptionAmount(0);
					LOGGER.warn("Player {} had invalid Absorption Amount, fixing!", handler.player);
				}
				if (Float.isNaN(handler.player.getHealth())) {
					handler.player.setHealth(10);
					LOGGER.warn("Player {} had invalid health, fixing!", handler.player);
				}
			}
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(Commands.literal("healthfixer").then(Commands.literal("testHealth")
				.requires(c -> c.hasPermission(2))
				.executes(context -> {
					context.getSource().getPlayerOrException().setHealth(NaN);
					return 1;
				}))));
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(Commands.literal("healthfixer").then(Commands.literal("testAbsorption")
				.requires(c -> c.hasPermission(2))
				.executes(context -> {
					context.getSource().getPlayerOrException().setAbsorptionAmount(NaN);
					return 1;
				}))));
	}
}