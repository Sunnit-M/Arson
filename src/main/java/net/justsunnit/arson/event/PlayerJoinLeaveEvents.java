package net.justsunnit.arson.event;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.justsunnit.arson.playtime_logging.PlaytimeLogger;


public class PlayerJoinLeaveEvents {
    public static void registerEvents() {

        ServerPlayConnectionEvents.JOIN.register(PlaytimeLogger::playerJoin);

        ServerPlayConnectionEvents.DISCONNECT.register(PlaytimeLogger::playerLeave);
    }
}
