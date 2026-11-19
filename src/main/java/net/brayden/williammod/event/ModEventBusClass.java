package net.brayden.williammod.event;

import net.brayden.williammod.WilliamMod;
import net.brayden.williammod.entity.ModEntities;
import net.brayden.williammod.entity.custom.WilliamEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WilliamMod.MODID)

public class ModEventBusClass {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.WILLIAM.get(), WilliamEntity.createAttributes().build());
    }

}
