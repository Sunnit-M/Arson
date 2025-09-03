package net.justsunnit.arson;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.justsunnit.arson.data.ClientStaticData;
import net.justsunnit.arson.event.EventRegistries;

public class ArsonClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricLoader.getInstance().getAllMods().forEach(item -> {
            ClientStaticData.loadedModIDs.add(item.getMetadata().getId());

            Arson.LOGGER.info("Found mod: " + item.getMetadata().getId());
        });

        EventRegistries.registerEvents_Client();
    }
}
