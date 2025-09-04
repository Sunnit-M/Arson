package net.justsunnit.arson.runnable;

import net.justsunnit.arson.ArsonServer;
import net.justsunnit.arson.automod.BannedData;
import net.justsunnit.arson.data.ServerStaticData;
import net.justsunnit.arson.objects.BannedPlayer;
import net.justsunnit.arson.packets.objects.ClientHandshakePacket;
import net.justsunnit.arson.util.JsonSaveHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CheckPlayer implements Runnable {
    public static ServerPlayerEntity player;

    @Override
    public void run() {
        if(player != null && !player.isDisconnected()){
            long timeout = 5000; // wait max 5 seconds
            long start = System.currentTimeMillis();
            ArrayList<String> illegalMods = new ArrayList<>();

            while (System.currentTimeMillis() - start < timeout) {
                ClientHandshakePacket found = ServerStaticData.handshakes.stream()
                        .filter(handshake -> handshake.uuid().equals(player.getUuidAsString()))
                        .findFirst()
                        .orElse(null);

                if (found != null) {
                    if(ArsonServer.config.modWhitelist().modWhitelistEnabled){
                       return;
                    }
                    for (String mod : found.loadedMods()) {
                        if(ArsonServer.config.modWhitelist().requiredMods.contains(mod)) break;
                        else if (ArsonServer.config.modWhitelist().optionalMods.contains(mod)) break;
                        else illegalMods.add(mod);
                    }

                    if(!illegalMods.isEmpty()){
                        StringBuilder reasons = new StringBuilder("Illegal mods detected: \n");
                        for (String mod : illegalMods) {
                            reasons.append(mod).append("-" + mod + " \n");
                        }

                        BannedData.banPlayer(new BannedPlayer(player.getName().getLiteralString(),reasons.toString() , LocalDateTime.now(),60L, false)
                                , player.getUuidAsString());
                    }
                    break;
                }
                try {
                    Thread.sleep(100); // small delay to prevent busy waiting
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.println("Timed out waiting for condition.");
        }
    }

    public CheckPlayer(ServerPlayerEntity plr){
        player = plr;
    }
}
