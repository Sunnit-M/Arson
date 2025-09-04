package net.justsunnit.arson.util;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class DirectoryManager {
    
    public static final File Main_PATH = FabricLoader.getInstance().getConfigDir().
    resolve("ArsonData").toFile();

    public static void checkDir(){
        if(!Main_PATH.exists()){
            Main_PATH.mkdir();
        }

        if(!new File(Main_PATH + "/Playtime").exists()){
            new File(Main_PATH + "/Playtime").mkdir();
        }

        if(!new File(Main_PATH + "/Logs").exists()){
            new File(Main_PATH + "/Logs").mkdir();
        }
    }
}
