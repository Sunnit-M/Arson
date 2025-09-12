package net.justsunnit.arson.keybinds;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class KeybindRegistry {
    public static void registerKeybinds() {
        KeyBindingHelper.registerKeyBinding(OpenPlaytimeUiKeybind.keyBinding);
        ClientTickEvents.END_CLIENT_TICK.register(OpenPlaytimeUiKeybind::tick);
    }
}
