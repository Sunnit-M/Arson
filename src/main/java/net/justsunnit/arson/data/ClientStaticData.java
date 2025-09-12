package net.justsunnit.arson.data;

import net.justsunnit.arson.objects.PlayerPlaytimeData;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientStaticData {
    public static ArrayList<String> loadedModIDs = new ArrayList<>();

    public static ArrayList<HashMap.Entry<String, Long>> allTimeRanked;
    public static ArrayList<HashMap.Entry<String, Long>> monthlyRanked;
    public static ArrayList<HashMap.Entry<String, Long>> weeklyRanked;
}
