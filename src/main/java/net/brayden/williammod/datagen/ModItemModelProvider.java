package net.brayden.williammod.datagen;

import net.brayden.williammod.WilliamMod;
import net.brayden.williammod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper){
        super(output, WilliamMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels(){
    withExistingParent(ModItems.WILLIAM_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }
}
