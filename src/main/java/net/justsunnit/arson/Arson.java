package net.justsunnit.arson;

import io.wispforest.owo.network.OwoNetChannel;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Arson implements ModInitializer {
    public static final String MOD_ID = "arson";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier INVENTORY_ID = Identifier.of(MOD_ID, "inventory");
    public static final OwoNetChannel INVENTORY_CHANNEL = OwoNetChannel.create(INVENTORY_ID);

    public static Identifier SENDINV_ID = Identifier.of(MOD_ID, "sendinv");
    public static final OwoNetChannel SENDINV_CHANNEL = OwoNetChannel.create(SENDINV_ID);


    @Override
    public void onInitialize() {

    }
}
