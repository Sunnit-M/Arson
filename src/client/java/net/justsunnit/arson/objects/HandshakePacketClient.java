package net.justsunnit.arson.objects;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;

public class HandshakePacketClient {
    private final String playerUUID;
    public final String playerName;
    private final String[] mods;

    public HandshakePacketClient(String playerUUID, String[] mods, String playerName) {
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.mods = mods;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }
    public String[] getMods() {
        return mods;
    }

    public PacketByteBuf toPacket() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeString(playerUUID);
        buf.writeVarInt(mods.length);
        for (String mod : mods) {
            buf.writeString(mod);
        }
        buf.writeString(playerName);
        return buf;
    }

    public static HandshakePacketClient fromPacket(PacketByteBuf buf) {
        String playerUUID = buf.readString(32767);
        int modCount = buf.readVarInt();
        String[] mods = new String[modCount];
        for (int i = 0; i < modCount; i++) {
            mods[i] = buf.readString(32767);
        }
        String playerName = buf.readString(32767);

        return new HandshakePacketClient(playerUUID, mods, playerName);
    }
}
