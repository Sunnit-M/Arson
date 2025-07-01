package net.justsunnit.arson.automod;

import net.justsunnit.arson.Arson;
import net.justsunnit.arson.objects.BannedPlayer;
import net.justsunnit.arson.util.JsonSaveHandler;
import net.justsunnit.arson.util.TextFormatter;
import net.minecraft.text.Text;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class BannedData { ;

    public static HashMap<String, BannedPlayer> loadBannedData() {
        HashMap<String, BannedPlayer> bannedPlayers = JsonSaveHandler.GetBannedPlayers();

        if (bannedPlayers != null) {
            Iterator<String> it = bannedPlayers.keySet().iterator();


            while(it.hasNext()) {
                String playerUUID = it.next();
                if(bannedPlayers.containsKey(playerUUID)){
                    BannedPlayer bannedPlayer = bannedPlayers.get(playerUUID);
                    LocalDateTime now = LocalDateTime.now();

                    Duration timeSinceBan = Duration.between(bannedPlayer.BanDate, now);

                    if(bannedPlayer.timeless || timeSinceBan.getSeconds() < bannedPlayer.BanSeconds) {
                        if(!bannedPlayer.timeless){
                            bannedPlayers.get(playerUUID).BanSeconds -= timeSinceBan.getSeconds();
                            bannedPlayers.get(playerUUID).BanDate = now;
                        }
                    } else {
                        it.remove();
                    }
                }
            }
        }
        JsonSaveHandler.SaveBannedPlayerData(bannedPlayers);
        return bannedPlayers;
    }

    public static boolean checkPlayer(String playerUUID) {
        HashMap<String, BannedPlayer> bannedPlayers = loadBannedData();
        return bannedPlayers.containsKey(playerUUID);
    }

    public static BannedPlayer getBannedPlayer(String playerUUID) {
        HashMap<String, BannedPlayer> bannedPlayers = loadBannedData();
        if(!bannedPlayers.containsKey(playerUUID)) {
            return null; // Player is not banned
        }
        return bannedPlayers.get(playerUUID);
    }

    public static void banPlayer(BannedPlayer player, String uuid) {
        HashMap<String, BannedPlayer> bannedPlayers = loadBannedData();
        if(bannedPlayers.containsKey(uuid)) {
            BannedPlayer data = bannedPlayers.get(uuid);
            data.BanSeconds += player.BanSeconds;
            data.Reason.append("\n").append(player.Reason.toString());
        }
        else{
            bannedPlayers.put(uuid, player);
        }
        JsonSaveHandler.SaveBannedPlayerData(bannedPlayers);

        Arson.server.getPlayerManager().getPlayer(UUID.fromString(uuid)).networkHandler.disconnect(Text.literal(
                "You are banned for: " + (player.timeless ? "inf" : TextFormatter.formatDuration(player.BanSeconds))
                + "\n Reason/Reasons:\n" + player.Reason.toString()).styled(style -> style.withBold(true)));

        Arson.LOGGER.info("[ArsonUtils] Banned player: " + player.Name);
    }

    public static boolean unbanPlayer(String playerUUID) {
        HashMap<String, BannedPlayer> bannedPlayers = loadBannedData();
        if (bannedPlayers.containsKey(playerUUID)) {
            String playerName = bannedPlayers.get(playerUUID).Name;
            bannedPlayers.remove(playerUUID);
            JsonSaveHandler.SaveBannedPlayerData(bannedPlayers);
            Arson.LOGGER.info("[ArsonUtils] Unbanned player: " + playerName);
            return true;
        }
        return false;
    }

    public static void bufferPlayer(BannedPlayer player, String uuid) {
        HashMap<String, BannedPlayer> bannedPlayers = loadBannedData();
        if(bannedPlayers.containsKey(uuid)) {
            BannedPlayer data = bannedPlayers.get(uuid);
            data.BanSeconds += player.BanSeconds;
            data.Reason.append("\n").append(player.Reason.toString());
        }
        else{
            bannedPlayers.put(uuid, player);
        }
        JsonSaveHandler.SaveBannedPlayerData(bannedPlayers);

        Arson.LOGGER.info("[ArsonUtils] Banned player: " + player.Name);
    }
}
