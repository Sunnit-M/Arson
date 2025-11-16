package net.justsunnit.arson.ui;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import  io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.ScrollContainer;
import io.wispforest.owo.ui.core.*;
import net.justsunnit.arson.Arson;
import net.justsunnit.arson.data.ClientStaticData;
import net.justsunnit.arson.objects.PlayerPlaytimeData;
import net.justsunnit.arson.packets.objects.RequestPacket;
import net.justsunnit.arson.ui.components.RankComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import javax.naming.ldap.HasControls;
import java.util.HashMap;
import java.util.concurrent.Flow;

public class PlaytimeUi extends BaseOwoScreen<FlowLayout> {
    public int xFill = 45;
    public FlowLayout scrollContainer;
    public boolean set = false;

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        set = false;

        rootComponent.horizontalAlignment(HorizontalAlignment.CENTER)
                        .verticalAlignment(VerticalAlignment.CENTER);

        rootComponent.child(
                Containers.verticalFlow(Sizing.fill(xFill),Sizing.content()).child(
                        Components.label(Text.literal("Playtime Stats")).color(Color.WHITE).shadow(true)
                                .horizontalTextAlignment(HorizontalAlignment.CENTER).verticalTextAlignment(VerticalAlignment.CENTER)
                )
        );

        rootComponent.child(
                Containers.horizontalFlow(Sizing.fill(xFill), Sizing.content()).child(
                        Components.button(Text.literal("Weekly"), buttonComponent ->
                                weeklyButtonClicked())
                )
                        .child(Components.button(Text.literal("Monthly"), buttonComponent
                                -> monthlyButtonClicked()))
                        .child(Components.button(Text.literal("All Time"), buttonComponent
                                -> allTimeButtonClicked()))
                        .child(Components.button(Text.literal("Refresh"), buttonComponent
                                -> refresh()))
                        .surface(Surface.DARK_PANEL).padding(Insets.of(5))
        );

        scrollContainer = Containers.verticalFlow(Sizing.fill(xFill),Sizing.content(5));
        scrollContainer.child(Containers.horizontalFlow(Sizing.fill(93),Sizing.content(5)).
                child(Components.label(Text.literal("Loading Leaderboard")).color(Color.BLACK).
                        horizontalTextAlignment(HorizontalAlignment.LEFT).verticalTextAlignment(VerticalAlignment.CENTER)
                        .sizing(Sizing.fill(95),Sizing.content())).alignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER)
                        .surface(Surface.DARK_PANEL));

        scrollContainer.surface(Surface.DARK_PANEL).padding(Insets.of(5));
        rootComponent.child(scrollContainer);

        refresh();
    }

    private void weeklyButtonClicked() {
        for (Component c : scrollContainer.children()){
           c.remove();
        }

        for (int i = 0; i < ClientStaticData.weeklyRanked.size(); i++) {
            HashMap.Entry<String, Long> entry = ClientStaticData.weeklyRanked.get(i);
            scrollContainer.child(RankComponent.create(entry.getKey(),entry.getValue(),i+1));
        }
    }

    private void monthlyButtonClicked() {
        for (Component c : scrollContainer.children()){
            c.remove();
        }

        for (int i = 0; i < ClientStaticData.monthlyRanked.size(); i++) {
            HashMap.Entry<String, Long> entry = ClientStaticData.monthlyRanked.get(i);
            scrollContainer.child(RankComponent.create(entry.getKey(),entry.getValue(),i+1));
        }
    }

    private void allTimeButtonClicked(){
        for (Component c : scrollContainer.children()){
            c.remove();
        }

        for (int i = 0; i < ClientStaticData.allTimeRanked.size(); i++) {
            HashMap.Entry<String, Long> entry = ClientStaticData.allTimeRanked.get(i);
            scrollContainer.child(RankComponent.create(entry.getKey(),entry.getValue(),i+1));
        }
    }

    public void refresh(){
        Arson.REQUEST_PLAYTIME_CHANNEL.clientHandle().send(
                new RequestPacket(MinecraftClient.getInstance().getGameProfile().name()
                        ,MinecraftClient.getInstance().getGameProfile().id().toString(), 0));
    }
}
