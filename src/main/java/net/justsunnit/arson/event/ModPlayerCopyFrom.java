package net.justsunnit.arson.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.justsunnit.arson.util.IEntityDataSaver;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModPlayerCopyFrom implements ServerPlayerEvents.CopyFrom {
    @Override
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        // Copy data from oldPlayer to newPlayer
        IEntityDataSaver originalData = (IEntityDataSaver) oldPlayer;
        IEntityDataSaver newData = (IEntityDataSaver) newPlayer;

        newData.getPersistentData().putIntArray("backpos", originalData.getPersistentData().getIntArray("backpos"));
    }
}
