package net.brayden.williammod.item;

import net.brayden.williammod.WilliamMod;
import net.brayden.williammod.entity.ModEntities;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, WilliamMod.MODID);

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
    public static final RegistryObject<Item> WILLIAM_SPAWN_EGG = ITEMS.register("william_spawn_egg",
            ()-> new ForgeSpawnEggItem(ModEntities.WILLIAM, 0xF07857, 0x4FB06D,
                    new Item.Properties()));
}
