package net.justsunnit.arson;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.justsunnit.arson.data.ClientStaticData;
import net.justsunnit.arson.event.EventRegistries;

import java.util.Objects;

public class ArsonClient implements ClientModInitializer {

    public static boolean keyed = false;

    @Override
    public void onInitializeClient() {
        FabricLoader.getInstance().getAllMods().forEach(item -> {
            if(Objects.equals(item.getMetadata().getId(), "arsonkey")) keyed = true;
            else ClientStaticData.loadedModIDs.add(item.getMetadata().getId());
        });

        EventRegistries.registerEvents_Client();


    }
}
