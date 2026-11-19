package net.brayden.williammod.entity;

import net.brayden.williammod.WilliamMod;
import net.brayden.williammod.entity.custom.WilliamEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, WilliamMod.MODID);

    public static final RegistryObject<EntityType<WilliamEntity>> WILLIAM =
            ENTITY_TYPES.register("william", () -> EntityType.Builder.of(WilliamEntity::new, MobCategory.CREATURE)
                    .sized(1.5f, 1.35f).build(ENTITY_TYPES.key("william")));

    public static void register(BusGroup eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
