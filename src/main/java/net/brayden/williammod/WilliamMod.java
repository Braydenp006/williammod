package net.brayden.williammod;

import com.mojang.logging.LogUtils;
import net.brayden.williammod.entity.ModEntities;
import net.brayden.williammod.entity.client.WilliamRenderer;
import net.brayden.williammod.entity.sounds.ModSounds;
import net.brayden.williammod.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WilliamMod.MODID)
public class WilliamMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "williammod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public WilliamMod(FMLJavaModLoadingContext context) {
        BusGroup modBusGroup = context.getModBusGroup();
        ModItems.register(modBusGroup);
        ModEntities.register(modBusGroup);
        ModSounds.register(modBusGroup);
        BuildCreativeModeTabContentsEvent.BUS.addListener(WilliamMod::addCreative);
    }

    private static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(ModItems.WILLIAM_SPAWN_EGG);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.WILLIAM.get(), WilliamRenderer::new);
        }
    }
}
