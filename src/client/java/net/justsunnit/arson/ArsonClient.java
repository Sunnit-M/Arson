package net.justsunnit.arson;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.justsunnit.arson.util.PackHandshake;
import net.minecraft.util.Identifier;

public class ArsonClient implements ClientModInitializer {
    public static final String MOD_ID = "arson";
    public static final Identifier QUERY_PACKET_ID = Identifier.of(MOD_ID, "query");

    @Override
    public void onInitializeClient() {
        System.out.println("Arson Client Initialized");

        ClientLoginNetworking.registerGlobalReceiver(QUERY_PACKET_ID, (client, handler, buf, responseSender) -> {
            return client.submit(() -> {
                return PackHandshake.pack().toPacket();
            });
        });
    }
}
