package net.brayden.williammod.entity.sounds;

import net.brayden.williammod.WilliamMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WilliamMod.MODID);

    // Register custom sounds
    public static final RegistryObject<SoundEvent> WILLIAM_AMBIENT = registerSoundEvents("entity.william.ambient");
    public static final RegistryObject<SoundEvent> WILLIAM_HURT = registerSoundEvents("entity.william.hurt");
    public static final RegistryObject<SoundEvent> WILLIAM_DEATH = registerSoundEvents("entity.william.death");


    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name,
                () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WilliamMod.MODID, name)));
    }
    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}

