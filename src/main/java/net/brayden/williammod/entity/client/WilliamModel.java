package net.brayden.williammod.entity.client;
// Made with Blockbench 4.11.2, ported to the Minecraft 26.x render-state model API

import net.brayden.williammod.entity.animations.ModAnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class WilliamModel extends EntityModel<WilliamRenderState> {
	private final ModelPart h_head;
	private final KeyframeAnimation sit;
	private final KeyframeAnimation sitIdle;
	private final KeyframeAnimation standUp;
	private final KeyframeAnimation dance;
	private final KeyframeAnimation idle;
	private final KeyframeAnimation walk;
	private final KeyframeAnimation joinking;

	public WilliamModel(ModelPart root) {
		super(root);
		this.h_head = root.getChild("William").getChild("h_head");
		this.sit = ModAnimationDefinition.SIT.bake(root);
		this.sitIdle = ModAnimationDefinition.SITIDLE.bake(root);
		this.standUp = ModAnimationDefinition.STANDUP.bake(root);
		this.dance = ModAnimationDefinition.DANCE.bake(root);
		this.idle = ModAnimationDefinition.IDLE.bake(root);
		this.walk = ModAnimationDefinition.WALK.bake(root);
		this.joinking = ModAnimationDefinition.JOINKING.bake(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition William = partdefinition.addOrReplaceChild("William", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition h_head = William.addOrReplaceChild("h_head", CubeListBuilder.create().texOffs(0, 9).addBox(-2.9643F, -1.9643F, -3.25F, 6.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(22, 30).addBox(-3.9643F, -1.9643F, -3.25F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(40, 47).addBox(-3.9643F, 0.0357F, -3.25F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(28, 39).addBox(-3.9643F, 2.0357F, -3.25F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(32, 0).addBox(-3.9643F, -1.9643F, -4.25F, 7.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(26, 20).addBox(-2.9643F, 4.0357F, -4.25F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-3.9643F, -2.9643F, -4.25F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(22, 22).addBox(-2.9643F, -3.9643F, -3.25F, 6.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(26, 9).addBox(3.0357F, -2.9643F, -4.25F, 1.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(38, 30).addBox(3.0357F, 0.0357F, -4.25F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(48, 0).addBox(3.0357F, 2.0357F, -4.25F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(48, 24).addBox(1.0357F, -2.9643F, 3.75F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(48, 27).addBox(-3.9643F, -2.9643F, 3.75F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(32, 7).addBox(-0.9643F, -2.9643F, 3.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0357F, -26.0357F, 0.25F));

		PartDefinition body = William.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Phone = body.addOrReplaceChild("Phone", CubeListBuilder.create().texOffs(16, 51).addBox(-0.2F, -1.5F, -0.5F, 0.2F, 2.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -8.0F, 0.0F));

		PartDefinition LeftArm = body.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(16, 48).addBox(-1.0F, 11.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 37).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -19.0F, 0.0F));

		PartDefinition RightArm = body.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(8, 37).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(48, 21).addBox(-1.0F, 11.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -19.0F, 0.0F));

		PartDefinition LeftLeg = body.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(28, 47).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(24, 48).addBox(0.8F, -1.5F, -1.0F, 0.2F, 2.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -8.0F, 0.5F));

		PartDefinition RightLeg = body.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(38, 38).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -8.0F, 0.5F));

		PartDefinition Torso = body.addOrReplaceChild("Torso", CubeListBuilder.create().texOffs(0, 22).addBox(-4.0F, -2.5F, -2.5F, 8.0F, 12.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(44, 16).addBox(-2.0F, -4.5F, -2.5F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.5F, 1.5F));

		PartDefinition LowerLegRight = body.addOrReplaceChild("LowerLegRight", CubeListBuilder.create().texOffs(16, 39).addBox(1.0F, -3.0F, -1.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(50, 41).addBox(1.0F, 1.0F, 2.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition LowerLegLeft = body.addOrReplaceChild("LowerLegLeft", CubeListBuilder.create().texOffs(44, 7).addBox(-4.0F, -3.0F, -1.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(50, 38).addBox(-4.0F, 1.0F, 2.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(WilliamRenderState state) {
		super.setupAnim(state);
		this.applyHeadRotation(state.yRot, state.xRot);

		if (state.isSitting) {
			this.sit.apply(state.sittingAnimationState, state.ageInTicks);
			this.sitIdle.apply(state.sitPoseAnimationState, state.ageInTicks);
		} else if (state.isStanding) {
			this.standUp.apply(state.standAnimationState, state.ageInTicks);
		}
		if (state.isPartyWill) {
			this.dance.apply(state.danceAnimationState, state.ageInTicks);
		} else {
			this.idle.apply(state.idleAnimationState, state.ageInTicks);
			this.walk.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 2f, 2.5f);
			this.joinking.apply(state.jerkAnimationState, state.ageInTicks);
		}
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch){
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 25.0F);

		this.h_head.yRot = pNetHeadYaw * ((float) Math.PI / 180F);
		this.h_head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}
}
