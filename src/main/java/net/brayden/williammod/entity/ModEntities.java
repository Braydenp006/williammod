package net.brayden.williammod.entity;

import net.brayden.williammod.WilliamMod;
import net.brayden.williammod.entity.custom.WilliamEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraftforge.registries.ForgeRegistries.Keys.ENTITY_TYPES;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, WilliamMod.MODID);

    public static final RegistryObject<EntityType<WilliamEntity>> WILLIAM =
            ENTITY_TYPES.register("william", () -> EntityType.Builder.of(WilliamEntity::new, MobCategory.CREATURE)
                    .sized(1.5f, 1.35f).build("william"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
