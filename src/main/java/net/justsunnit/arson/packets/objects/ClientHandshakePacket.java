package net.justsunnit.arson.packets.objects;

import java.lang.reflect.Array;
import java.util.ArrayList;

public record ClientHandshake(
ArrayList<String> loadedMods
){}