package net.justsunnit.arson;

import io.wispforest.owo.network.OwoNetChannel;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.justsunnit.arson.packets.RegisterPackets;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Arson implements ModInitializer {
    public static final String MOD_ID = "arson";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Identifier CLIENT_HANDSHAKE_ID = Identifier.of(MOD_ID, "hasclient");
    public static final OwoNetChannel CLIENT_HANDSHAKE_CHANNEL = OwoNetChannel.create(CLIENT_HANDSHAKE_ID);


    @Override
    public void onInitialize() {
        RegisterPackets.registerPackets();
    }
}
