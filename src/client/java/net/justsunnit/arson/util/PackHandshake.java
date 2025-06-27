package net.justsunnit.arson.util;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.justsunnit.arson.objects.HandshakePacketClient;
import net.minecraft.client.MinecraftClient;

import java.util.Collection;

public class PackHandshake {
    public static HandshakePacketClient pack(){
        FabricLoader fabricLoader = FabricLoader.getInstance();
        Collection<ModContainer> mods = fabricLoader.getAllMods();

        String UUID = MinecraftClient.getInstance().player.getUuidAsString();

        String[] modIDs = mods.stream()
                .map(mod -> mod.getMetadata().getId())
                .toArray(String[]::new);

        return new HandshakePacketClient(UUID, modIDs, MinecraftClient.getInstance().player.getName().getString());
    }
}
