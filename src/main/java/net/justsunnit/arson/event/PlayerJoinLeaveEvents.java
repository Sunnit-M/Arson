package net.justsunnit.arson.event;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.justsunnit.arson.Arson;
import net.justsunnit.arson.ArsonServer;
import net.justsunnit.arson.automod.BannedData;
import net.justsunnit.arson.objects.BannedPlayer;
import net.justsunnit.arson.objects.PlayerPlaytimeData;
import net.justsunnit.arson.util.JsonSaveHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.time.Duration;
import java.time.LocalDateTime;

import static net.justsunnit.arson.util.PlaytimeLogger.*;


public class PlayerJoinLeaveEvents {
    public static void playerJoin(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server){
        if(!JsonSaveHandler.getAllPlayers().containsKey(handler.getPlayer().getName().getLiteralString())){
            JsonSaveHandler.AddPlayerToAllPlayers(handler.player.getName().toString(), handler.getPlayer().getUuidAsString());
            Arson.LOGGER.info("[ArsonUtils] New player detected: " + handler.getPlayer().getName().getLiteralString() + ". Added to all players list.");
        }

        playerLoginTimeStamp.put(handler.getPlayer().getName().getLiteralString(), LocalDateTime.now());
        handler.getPlayer().sendMessage(Text.literal("[ArsonUtils] Logged Login Time: " + LocalDateTime.now().toString()).styled(style -> style.withBold(true).withColor(Formatting.RED)), false);
    }

    public static void playerLeave(ServerPlayNetworkHandler handler, MinecraftServer server){
        Duration timeSpent = Duration.between(playerLoginTimeStamp.getOrDefault(handler.getPlayer().getName().getLiteralString(),LocalDateTime.now()), LocalDateTime.now());
        PlayerPlaytimeData data = (PlayerPlaytimeData) JsonSaveHandler.GetPlayerPlaytimeData().getOrDefault(handler.getPlayer().getName().getLiteralString(), new PlayerPlaytimeData());
        data.AddPlaytime(timeSpent.toSeconds());
        JsonSaveHandler.SavePlayerPlaytimeData(data, handler.getPlayer().getName().getLiteralString());
        Arson.LOGGER.info("[ArsonUtils] Playtime for " + handler.getPlayer().getName().getLiteralString() + " has been saved.");

        playerLoginTimeStamp.remove(handler.getPlayer().getName().getLiteralString());

        if(BannedData.checkPlayer(handler.getPlayer().getUuidAsString())) return;

        BannedPlayer bannedPlayer = new BannedPlayer(
                handler.getPlayer().getName().getLiteralString(),
                "[Automod] Join/Leave Buffer",
                LocalDateTime.now(),
                15L,
                false
        );

        BannedData.bufferPlayer(bannedPlayer, handler.getPlayer().getUuidAsString());
    }
}
