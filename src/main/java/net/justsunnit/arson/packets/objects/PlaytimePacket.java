package net.justsunnit.arson.packets.objects;

import net.justsunnit.arson.objects.PlayerPlaytimeData;

import java.util.HashMap;
import java.util.Map;

public record PlaytimePacket(Map<String, Long> week, Map<String,Long> monthly, Map<String,Long> allTime) { }