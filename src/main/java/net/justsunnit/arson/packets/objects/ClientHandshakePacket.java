package net.justsunnit.arson.packets.objects;

import java.util.List;

public record ClientHandshakePacket(
List<String> loadedMods,
String uuid
){}