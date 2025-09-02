package net.justsunnit.arson;

import io.wispforest.owo.network.OwoNetChannel;
import net.fabricmc.api.ModInitializer;
import net.justsunnit.arson.packets.RegisterPackets;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Arson implements ModInitializer {
    public static final String MOD_ID = "arson";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Identifier HAS_CLIENT_ID = Identifier.of(MOD_ID, "hasclient");
    public static final OwoNetChannel HAS_CLIENT_CHANNEL = OwoNetChannel.create(HAS_CLIENT_ID);


    @Override
    public void onInitialize() {
        RegisterPackets.registerPackets();
    }
}
