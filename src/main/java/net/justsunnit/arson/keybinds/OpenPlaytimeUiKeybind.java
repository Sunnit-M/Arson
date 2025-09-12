package net.justsunnit.arson.keybinds;

import net.justsunnit.arson.ui.PlaytimeUi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

public class OpenPlaytimeUiKeybind {
    public static KeyBinding keyBinding = new KeyBinding(
            "key.arson.open_playtime_ui",
            InputUtil.Type.KEYSYM,
            InputUtil.GLFW_KEY_F7,
            "category.arson.main"
    );


    public static void tick(MinecraftClient minecraftClient) {
        if(keyBinding.wasPressed()) {
            minecraftClient.setScreen(new PlaytimeUi());
        }
    }
}
