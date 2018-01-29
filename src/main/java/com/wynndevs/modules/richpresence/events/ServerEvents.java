package com.wynndevs.modules.richpresence.events;

import com.wynndevs.core.Reference;
import com.wynndevs.modules.richpresence.WynnRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by HeyZeer0 on 04/12/2017.
 * Copyright © HeyZeer0 - 2016
 */
public class ServerEvents {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onServerJoin(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        if(!WynnRichPresence.getRichPresence().isReady()) {
            return;
        }

        if(e.isLocal()) {
            return;
        }

        ServerData server = Minecraft.getMinecraft().getCurrentServerData();
        if(server == null || server.serverIP == null) {
            return;
        }
        if(!Reference.onServer()) {
            return;
        }

        WynnRichPresence.getRichPresence().updateRichPresence("At Lobby", null, null, null);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onServerLeave(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        if(Reference.onServer()) {
            WynnRichPresence.getRichPresence().stopRichPresence();

            if(ChatEvents.updateTimer != null && !ChatEvents.updateTimer.isCancelled()) {
                ChatEvents.updateTimer.cancel(true);
            }
        }
    }

}
