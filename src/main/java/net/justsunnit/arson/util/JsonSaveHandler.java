package net.justsunnit.arson.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.justsunnit.arson.Arson;
import net.justsunnit.arson.TypeAdapters.LocalDateTimeAdapter;
import net.justsunnit.arson.automod.BannedData;
import net.justsunnit.arson.objects.BannedPlayer;
import net.justsunnit.arson.objects.PlayerPlaytimeData;
import net.justsunnit.arson.objects.ServerTimeStampData;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.HashMap;

public class JsonSaveHandler {
    public static final String DateTimestamp_FILE = "Arson_Config/Playtime/date_timestamp.json";
    public static final String Playtime_FILE = "Arson_Config/Playtime/player_playtime.json";
    public static final File BANNED_PLAYERS_FILE = new File(ConfigManger.CONFIG_FOLER_PATH.toString()).toPath().
            resolve("banned_players.json").toFile();

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();

    public static void LoadBannedPlayers() {
        if (!BANNED_PLAYERS_FILE.exists()) {
            try {
                Files.createDirectories(BANNED_PLAYERS_FILE.getParentFile().toPath());
                if(BANNED_PLAYERS_FILE.createNewFile()){
                    throw new IOException("[Arson] Failed to create banned players file");
                }
            } catch (IOException e) {
                Arson.LOGGER.error("[Arson] Failed to create banned players file", e);
            }
        }
    }

    public static void initializeJsonData() {
        if (!new File(DateTimestamp_FILE).exists()) {
            try {
                new File(DateTimestamp_FILE).createNewFile();
                FileWriter writer = new FileWriter(DateTimestamp_FILE);
                ServerTimeStampData data = new ServerTimeStampData(LocalDateTime.now(), LocalDateTime.now());
                writer.write(GSON.toJson(data));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!new File(Playtime_FILE).exists()) {
            try {
                new File(Playtime_FILE).createNewFile();
                FileWriter writer = new FileWriter(Playtime_FILE);
                writer.write(GSON.toJson(new HashMap<String, PlayerPlaytimeData>()));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!BANNED_PLAYERS_FILE.exists()) {
            try {
                BANNED_PLAYERS_FILE.createNewFile();
                FileWriter writer = new FileWriter(BANNED_PLAYERS_FILE);
                writer.write(GSON.toJson(new HashMap<String, BannedPlayer>()));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ServerTimeStampData GetServerTimeStampData() {
        try {
            FileReader reader = new FileReader(DateTimestamp_FILE);
            ServerTimeStampData data = GSON.fromJson(reader, ServerTimeStampData.class);
            reader.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HashMap<String, PlayerPlaytimeData> GetPlayerPlaytimeData() {
        try {
            FileReader reader = new FileReader(Playtime_FILE);
            HashMap<String, PlayerPlaytimeData> data = GSON.fromJson(reader, new TypeToken<HashMap<String, PlayerPlaytimeData>>() {}.getType());
            reader.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HashMap<String, BannedPlayer> GetBannedPlayers() {
        try {
            FileReader reader = new FileReader(BANNED_PLAYERS_FILE);
            HashMap<String, BannedPlayer> data = GSON.fromJson(reader, new TypeToken<HashMap<String, BannedPlayer>>() {}.getType());
            reader.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void SaveServerTimeStampData(ServerTimeStampData data) {
        try {
            FileWriter writer = new FileWriter(DateTimestamp_FILE);
            writer.write(GSON.toJson(data));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void SavePlayerPlaytimeData(PlayerPlaytimeData data, String player) {
        try {
            HashMap<String, PlayerPlaytimeData> hash = GetPlayerPlaytimeData();
            hash.put(player, data);
            FileWriter writer = new FileWriter(Playtime_FILE);
            writer.write(GSON.toJson(hash));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void SaveBannedPlayerData(HashMap<String, BannedPlayer> data) {
        try {
            FileWriter writer = new FileWriter(BANNED_PLAYERS_FILE);
            writer.write(GSON.toJson(data));
            writer.close();

            BannedData.loadBannedData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}