package net.justsunnit.arson;

import net.fabricmc.api.ClientModInitializer;

public class ArsonClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        // Client-side initialization logic goes here
        // This is where you can register client-specific features, renderers, etc.
        System.out.println("Arson Client Initialized");
    }
}
