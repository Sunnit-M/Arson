package net.justsunnit.arson.automod;

import net.justsunnit.arson.Arson;
import net.justsunnit.arson.objects.BannedPlayer;
import net.justsunnit.arson.util.JsonSaveHandler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;

public class BannedData {
    public static HashMap<String, BannedPlayer> bannedPlayers = new HashMap<>();

    public static void loadBannedData() {
        bannedPlayers = JsonSaveHandler.GetBannedPlayers();
    }

    public static boolean checkPlayer(String playerUUID) {
        if(bannedPlayers.containsKey(playerUUID)){
            BannedPlayer bannedPlayer = bannedPlayers.get(playerUUID);
            LocalDateTime now = LocalDateTime.now();

            Duration timeSinceBan = Duration.between(bannedPlayer.BanDate, now);

            if(bannedPlayer.timeless || timeSinceBan.getSeconds() < bannedPlayer.BanSeconds) {
                return true;
            } else {
                bannedPlayers.remove(playerUUID);
                JsonSaveHandler.SaveBannedPlayerData(bannedPlayers);
                return false;
            }
        }
        return false;
    }

    public static BannedPlayer getBannedPlayer(String playerUUID) {
        if(!bannedPlayers.containsKey(playerUUID)) {
            return null; // Player is not banned
        }
        return bannedPlayers.get(playerUUID);
    }

    public static void banPlayer(BannedPlayer player) {
        String uuid = Arson.server.getPlayerManager().getPlayer(player.Name).getUuidAsString();
        if(bannedPlayers.containsKey(uuid)) {
            BannedPlayer data = bannedPlayers.get(uuid);
            data.BanSeconds += player.BanSeconds;
            data.Reason.append("\n").append(player.Reason.toString());
        }
        else{
            bannedPlayers.put(uuid, player);
        }
        JsonSaveHandler.SaveBannedPlayerData(bannedPlayers);
        Arson.LOGGER.info("[ArsonUtils] Banned player: " + player.Name + " for " + (player.timeless ? "timeless" : Duration.ofSeconds(player.BanSeconds).toString()));
    }

    public static void unbanPlayer(String playerUUID) {
        if(bannedPlayers.containsKey(playerUUID)) {
            String playerName = bannedPlayers.get(playerUUID).Name;
            bannedPlayers.remove(playerUUID);
            JsonSaveHandler.SaveBannedPlayerData(bannedPlayers);
            Arson.LOGGER.info("[ArsonUtils] Unbanned player: " + playerName);
        } else {
            Arson.LOGGER.warn("[ArsonUtils] Attempted to unban a player that is not banned: " + Arson.server.getPlayerManager().getPlayer(playerUUID).getName().toString());
        }
    }
}
