package net.justsunnit.arson.keybinds;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

public class OpenUtilUiKeybind {
    public static KeyBinding keyBinding = new KeyBinding(
            "key.arson.open_util_ui",
            InputUtil.Type.KEYSYM,
            InputUtil.GLFW_KEY_F7,
            "category.arson.main"
    );


    public static void tick(MinecraftClient minecraftClient) {
        if(keyBinding.wasPressed()) {
            minecraftClient.player.sendMessage(Text.of("pressed"), false);
        }
    }
}
