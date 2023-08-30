package com.bagel;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public class ClientHealthFixer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (client.player != null){
                if (Float.isNaN(client.player.getAbsorptionAmount())) {
                    client.player.setAbsorptionAmount(0);
                    HealthFixer.LOGGER.warn("Player {} had invalid Absorption Amount, fixing!", client.player);
                }
                if (Float.isNaN(client.player.getHealth())) {
                    client.player.setHealth(10);
                    HealthFixer.LOGGER.warn("Player {} had invalid health, fixing!", client.player);
                }
            }
        });
    }
}
