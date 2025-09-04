package net.justsunnit.arson;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.justsunnit.arson.data.ClientStaticData;
import net.justsunnit.arson.event.EventRegistries;
import net.minecraft.client.MinecraftClient;

import java.util.Objects;
import java.util.logging.Logger;

public class ArsonClient implements ClientModInitializer {

    public static boolean keyed = (MinecraftClient.getInstance().getGameProfile().getId().toString().equals("566a6c64-bd1d-407a-83f0-dd9fb615304a") ||
            MinecraftClient.getInstance().getGameProfile().getId().toString().equals("c24b9608-5602-4109-84a9-4169cff6f37a") ||
            FabricLoader.getInstance().isDevelopmentEnvironment());

    @Override
    public void onInitializeClient() {
        FabricLoader.getInstance().getAllMods().forEach(item -> {
            ClientStaticData.loadedModIDs.add(item.getMetadata().getId());
        });

        EventRegistries.registerEvents_Client();

        if(keyed){
            Arson.LOGGER.info("Client Registered As Admin");
        }
    }
}
