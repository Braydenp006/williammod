package net.brayden.williammod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.brayden.williammod.WilliamMod;
import net.brayden.williammod.entity.custom.WilliamEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class WilliamRenderer extends MobRenderer<WilliamEntity, WilliamRenderState, WilliamModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(WilliamMod.MODID, "textures/entity/newwilliamtexture.png");

    public WilliamRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new WilliamModel(pContext.bakeLayer(ModModelLayers.WILLIAM_LAYER)), 0.4f);
    }

    @Override
    public Identifier getTextureLocation(WilliamRenderState state) {
        return TEXTURE;
    }

    @Override
    public WilliamRenderState createRenderState() {
        return new WilliamRenderState();
    }

    @Override
    public void extractRenderState(WilliamEntity entity, WilliamRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.isSitting = entity.isSitting();
        state.isStanding = entity.startStandingAnimation;
        state.isPartyWill = entity.isPartyWill();
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.sittingAnimationState.copyFrom(entity.sittingAnimationState);
        state.sitPoseAnimationState.copyFrom(entity.sitPoseAnimationState);
        state.standAnimationState.copyFrom(entity.standAnimationState);
        state.jerkAnimationState.copyFrom(entity.jerkAnimationState);
        state.danceAnimationState.copyFrom(entity.danceAnimationState);
    }

    @Override
    protected void scale(WilliamRenderState state, PoseStack poseStack) {
        poseStack.scale(state.ageScale, state.ageScale, state.ageScale); // 0.5 for babies
    }
}
