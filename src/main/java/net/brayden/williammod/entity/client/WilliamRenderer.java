package net.brayden.williammod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.brayden.williammod.WilliamMod;
import net.brayden.williammod.entity.custom.WilliamEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WilliamRenderer extends MobRenderer<WilliamEntity, WilliamModel<WilliamEntity>>{
    public WilliamRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new WilliamModel<>(pContext.bakeLayer(ModModelLayers.WILLIAM_LAYER)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(WilliamEntity pEntity) {
        return new ResourceLocation(WilliamMod.MODID, "textures/entity/newwilliamtexture.png");

}

    @Override
    public void render(WilliamEntity pEntity, float pEntityYaw, float pParticleTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()){
            pMatrixStack.scale(0.5f,0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pParticleTicks, pMatrixStack, pBuffer, pPackedLight);
    }

}
