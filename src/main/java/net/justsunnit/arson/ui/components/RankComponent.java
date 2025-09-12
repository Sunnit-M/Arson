package net.justsunnit.arson.ui.components;


import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.justsunnit.arson.objects.PlayerPlaytimeData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.realms.dto.PlayerInfo;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class RankComponent {
    public static Component create(String name, Long data, int rank) {
        MinecraftClient client = MinecraftClient.getInstance();
        Identifier rankTexture = Identifier.of("arson", "textures/ui/ranks/rank_" + rank + ".png");

        return Components.label(Text.literal(rank + " - " + name + " : " + PlayerPlaytimeData.formattedPlaytime(data)))
                .color(Objects.equals(name, client.getGameProfile().getName()) ? Color.RED : Color.WHITE);
    }
}
