package net.brayden.williammod.entity.custom;

import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;

public class TamableAIFollowOwner extends FollowOwnerGoal {
    private final WilliamEntity williamEntity;

    public TamableAIFollowOwner(WilliamEntity williamEntity, double speed, float minDist, float maxDist, boolean teleport) {
        super(williamEntity, speed, minDist, maxDist, teleport);
        this.williamEntity = williamEntity;
    }

    @Override
    public boolean canUse() {
        return !williamEntity.isSitting() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !williamEntity.isSitting() && super.canContinueToUse();
    }
}

