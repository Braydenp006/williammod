package net.brayden.williammod.event;

import net.brayden.williammod.WilliamMod;
import net.brayden.williammod.entity.client.ModModelLayers;
import net.brayden.williammod.entity.client.WilliamModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WilliamMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)

public class ModEventsBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.WILLIAM_LAYER, WilliamModel::createBodyLayer);
    }

}
