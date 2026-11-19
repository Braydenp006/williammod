package net.brayden.williammod.entity.client;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

// Snapshot of WilliamEntity that the model animates from (26.x models no longer see the entity).
public class WilliamRenderState extends LivingEntityRenderState {
    public boolean isSitting;
    public boolean isStanding;
    public boolean isPartyWill;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState sitPoseAnimationState = new AnimationState();
    public final AnimationState standAnimationState = new AnimationState();
    public final AnimationState jerkAnimationState = new AnimationState();
    public final AnimationState danceAnimationState = new AnimationState();
}
