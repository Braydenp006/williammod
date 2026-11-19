package net.brayden.williammod.item;

import net.brayden.williammod.WilliamMod;
import net.brayden.williammod.entity.ModEntities;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, WilliamMod.MODID);

    public static void register(BusGroup eventBus){
        ITEMS.register(eventBus);
    }
    // Delayed: Forge registers items before entity types, so WILLIAM isn't available yet at construction.
    public static final RegistryObject<Item> WILLIAM_SPAWN_EGG = ITEMS.register("william_spawn_egg",
            ()-> new SpawnEggItem(new Item.Properties().setId(ITEMS.key("william_spawn_egg"))
                    .delayedComponent(DataComponents.ENTITY_DATA, ctx -> TypedEntityData.of(ModEntities.WILLIAM.get(), new CompoundTag()))));
}
