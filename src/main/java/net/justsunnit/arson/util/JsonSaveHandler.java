package net.justsunnit.arson.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import net.justsunnit.arson.TypeAdapters.LocalDateTimeAdapter;
import net.justsunnit.arson.config.ServerConfigModal;
import net.justsunnit.arson.objects.BannedPlayer;
import net.justsunnit.arson.objects.PlayerPlaytimeData;
import net.justsunnit.arson.objects.ServerTimeStampData;
import org.joml.Vector3L;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;

public class JsonSaveHandler {
    public static final Path DATA_FOLDER = new File(FabricLoader.getInstance().getConfigDir().toString()).toPath().
            resolve("ArsonData");
    public static final File DateTimestamp_FILE = new File(DATA_FOLDER.toString()).toPath()
            .resolve("Playtime").resolve("date_timestamp.json").toFile();
    public static final File Playtime_FILE = new File(DATA_FOLDER.toString()).toPath()
            .resolve("Playtime").resolve("player_playtime.json").toFile();
    public static final File BANNED_PLAYERS_FILE = new File(DATA_FOLDER.toString()).toPath().
            resolve("banned_players.json").toFile();
    public static final File ALL_PLAYERS_FILE = new File(DATA_FOLDER.toString()).toPath().
            resolve("all_players.json").toFile();
    public static final File SPECTATE_FILE = new File(DATA_FOLDER.toString()).toPath().
            resolve("spectate.json").toFile();

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();


    public static void initializeJsonData() {
        if (!DateTimestamp_FILE.exists()) {
            try {
                DateTimestamp_FILE.createNewFile();
                FileWriter writer = new FileWriter(DateTimestamp_FILE);
                ServerTimeStampData data = new ServerTimeStampData(LocalDateTime.now(), LocalDateTime.now());
                writer.write(GSON.toJson(data));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!Playtime_FILE.exists()) {
            try {
                Playtime_FILE.createNewFile();
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

        if(!ALL_PLAYERS_FILE.exists()) {
            try {
                ALL_PLAYERS_FILE.createNewFile();
                FileWriter writer = new FileWriter(ALL_PLAYERS_FILE);
                writer.write(GSON.toJson(new HashMap<String, String>()));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(!SPECTATE_FILE.exists()) {
            try {
                SPECTATE_FILE.createNewFile();
                FileWriter writer = new FileWriter(SPECTATE_FILE);
                writer.write(GSON.toJson(new HashMap<String, Vector3L>()));
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

    public static HashMap<String, Vector3L> GetSpectateData() {
        try {
            FileReader reader = new FileReader(SPECTATE_FILE);
            HashMap<String, Vector3L> data = GSON.fromJson(reader, new TypeToken<HashMap<String, Vector3L>>() {}.getType());
            reader.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void SaveSpectateData(HashMap<String, Vector3L> data) {
        try {
            FileWriter writer = new FileWriter(SPECTATE_FILE);
            writer.write(GSON.toJson(data));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> getAllPlayers() {
        try {
            FileReader reader = new FileReader(ALL_PLAYERS_FILE);
            HashMap<String, String> data = GSON.fromJson(reader, new TypeToken<HashMap<String, String>>() {}.getType());
            reader.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void AddPlayerToAllPlayers(String Player, String PlayerUUID) {
        try {
            HashMap<String, String> allPlayers = getAllPlayers();
            if (allPlayers == null) {
                allPlayers = new HashMap<>();
            }
            allPlayers.put(Player, PlayerUUID);
            FileWriter writer = new FileWriter(ALL_PLAYERS_FILE);
            writer.write(GSON.toJson(allPlayers));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}