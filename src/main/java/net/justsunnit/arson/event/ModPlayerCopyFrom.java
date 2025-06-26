package net.justsunnit.arson.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.justsunnit.arson.util.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.justsunnit.arson.*;

import java.util.Optional;

public class ModPlayerCopyFrom implements ServerPlayerEvents.CopyFrom {
    @Override
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        IEntityDataSaver originalData = (IEntityDataSaver) oldPlayer;
        IEntityDataSaver newData = (IEntityDataSaver) newPlayer;

        Optional<int[]> backpos = originalData.getPersistentData().getIntArray("backpos");
        backpos.ifPresent(arr -> newData.getPersistentData().putIntArray("backpos", arr));
    }
}
