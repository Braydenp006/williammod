package net.brayden.williammod.entity.client;

import net.brayden.williammod.WilliamMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ModModelLayers {
    public static final ModelLayerLocation WILLIAM_LAYER = new ModelLayerLocation(
        Identifier.fromNamespaceAndPath(WilliamMod.MODID, "william_layer"), "main");
}
