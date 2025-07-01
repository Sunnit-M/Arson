package net.justsunnit.arson.event;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.networking.v1.LoginPacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;
import net.justsunnit.arson.Arson;
import net.justsunnit.arson.automod.BannedData;
import net.justsunnit.arson.objects.BannedPlayer;
import net.justsunnit.arson.util.TextFormatter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.lang.reflect.Field;

public class PlayerLoginEvents {

    public static void onQuerty(ServerLoginNetworkHandler handler, MinecraftServer minecraftServer, LoginPacketSender loginPacketSender, ServerLoginNetworking.LoginSynchronizer loginSynchronizer) {
        GameProfile profile = null;
        try {
            Field field = handler.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            profile = (GameProfile) field.get(handler);
        } catch (Exception e) {
            e.printStackTrace();
            handler.disconnect(Text.literal("[ArsonUtils] Internal error during login.").styled(style -> style.withBold(true).withColor(Formatting.RED)));
            return;
        }

        // Maintenance check
        if (Arson.config.isMaintenanceMode()) {
            if (!Arson.config.isAdmin(profile.getName())) {
                handler.disconnect(Text.literal("[ArsonUtils] Server is in maintenance mode. Please try again later.").styled(style -> style.withBold(true).withColor(Formatting.RED)));
                return;
            }
        }

        // Ban check
        String uuid = profile.getId().toString();
        if (BannedData.checkPlayer(uuid)) {
            BannedPlayer ban = BannedData.getBannedPlayer(uuid);
            handler.disconnect(Text.literal("You are banned for: " + (ban.timeless ? "inf" : TextFormatter.formatDuration(ban.BanSeconds))
                    + "\nReason/Reasons:\n" + ban.Reason).styled(style -> style.withBold(true).withColor(Formatting.RED)));
        }
    }
}