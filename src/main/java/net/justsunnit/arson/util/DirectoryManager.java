package net.justsunnit.arson.util;

import java.io.File;

public class DirectoryManager {
    
    public static final String Main_PATH = "Arson_Config";

    public static void checkDir(){
        if(!new File(Main_PATH).exists()){
            new File(Main_PATH).mkdir();
        }

        if(!new File(Main_PATH + "/Playtime").exists()){
            new File(Main_PATH + "/Playtime").mkdir();
        }

        if(!new File(Main_PATH + "/Logs").exists()){
            new File(Main_PATH + "/Logs").mkdir();
        }
    }
}
