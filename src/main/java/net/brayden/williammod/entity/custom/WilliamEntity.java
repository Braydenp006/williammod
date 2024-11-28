package net.brayden.williammod.entity.custom;

import net.brayden.williammod.entity.ModEntities;
import net.brayden.williammod.entity.sounds.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import java.util.UUID;


public class WilliamEntity extends TamableAnimal implements NeutralMob {

    private static final EntityDataAccessor<Boolean> STANDING = SynchedEntityData.defineId(WilliamEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(WilliamEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(WilliamEntity.class, EntityDataSerializers.INT);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState deadAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState sitPoseAnimationState = new AnimationState();
    public final AnimationState standAnimationState = new AnimationState();
    public final AnimationState jerkAnimationState = new AnimationState();
    public final AnimationState danceAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public boolean startSittingAnimation;
    public boolean startSittingidle;
    public boolean startStandingAnimation;
    public boolean isJerking;
    public float standProgress;
    public float sitProgress;
    public int maxStandTime = 85;
    public int jerkcount = 0;
    private boolean partyWill;
    private boolean willDancing;
    @Nullable
    private BlockPos jukebox;
    public boolean forcedSit = false;

    public WilliamEntity(EntityType type, Level world) {
        super(type, world);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
        this.setTame(false);
    }

    protected float getWaterSlowDown() {
        return 0.98F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.WILLIAM_AMBIENT.get();
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.WILLIAM_HURT.get();
    }
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.WILLIAM_DEATH.get();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new net.brayden.williammod.entity.custom.TamableAIFollowOwner(this, 1.3D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.1D, Ingredient.of(Items.COAL), false));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.1D, Ingredient.of(Items.GOLD_NUGGET), false));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(Items.PORKCHOP);
    }

    public void setRecordPlayingNearby(BlockPos pPos, boolean pIsPartying) {
        this.jukebox = pPos;
        this.partyWill = pIsPartying;
        willDancing = true;
    }

    public boolean isPartyWill() {
        return this.partyWill;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        InteractionResult type = super.mobInteract(player, hand);
        boolean owner = this.isTame() && isOwnedBy(player);
        if (this.level().isClientSide) {
            boolean flag = this.isOwnedBy(player) || this.isTame() || itemstack.is(Items.COAL) && !this.isTame();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else if (this.isTame()) {
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                this.heal((float)itemstack.getFoodProperties(this).getNutrition());
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            } else {
                InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
                if (interactionresult != InteractionResult.SUCCESS && type != InteractionResult.SUCCESS && isTame() && isOwnedBy(player) && !isWilliamFood(itemstack)) {
                    if (!player.isShiftKeyDown()) {
                        this.setCommand(this.getCommand() + 1);
                        if (this.getCommand() == 3) {
                            this.setCommand(1);
                        }
                        //player.displayClientMessage(Component.translatable("entity.alexsmobs.all.command_" + this.getCommand(), this.getName()), true);
                        boolean sit = this.getCommand() == 2;
                        if (sit) {
                            player.displayClientMessage(Component.translatable("William Is Sitting", this.getName()),true);
                            this.forcedSit = true;
                            this.setOrderedToSit(true);
                            this.navigation.stop();
                            return InteractionResult.SUCCESS;
                        }else{
                            player.displayClientMessage(Component.translatable("William Is Standing", this.getName()),true);
                            this.forcedSit = false;
                            this.setOrderedToSit(false);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        } else if (itemstack.is(Items.COAL)) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                this.tame(player);
                this.navigation.stop();
                this.setTarget((LivingEntity)null);
                this.setOrderedToSit(true);
                this.level().broadcastEntityEvent(this, (byte)7);
            } else {
                this.level().broadcastEntityEvent(this, (byte)6);
            }

            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(player, hand);
        }
        return type;
    }


    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("WilliamSitting", this.isSitting());
        compound.putBoolean("ForcedToSit", this.forcedSit);
        compound.putInt("WilliamCommand", this.getCommand());
    }


    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setOrderedToSit(compound.getBoolean("WilliamSitting"));
        this.forcedSit = compound.getBoolean("ForcedToSit");
        this.setCommand(compound.getInt("WilliamCommand"));
    }

    public void setCommand(int command) {
        this.entityData.set(COMMAND, Integer.valueOf(command));
    }

    public int getCommand() {
        return this.entityData.get(COMMAND);
    }

    public void setOrderedToSit(boolean sit) {
        this.entityData.set(SITTING, Boolean.valueOf(sit));
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    public static boolean isWilliamFood(ItemStack stack) {
        return stack.isEdible() || stack.is(Items.COOKED_BEEF);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().
                add(Attributes.MOVEMENT_SPEED, (double) 0.3F).
                add(Attributes.MAX_HEALTH, 12.0D).
                add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getEntity();
            this.setOrderedToSit(false);
            if (entity != null && this.isTame() && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
                amount = (amount + 1.0F) / 4.0F;
            }
            return super.hurt(source, amount);
        }
    }

    @Override
    protected void updateControlFlags() {
        boolean flag = !(this.getControllingPassenger() instanceof Mob);
        boolean flag1 = !(this.getVehicle() instanceof Boat);
        this.goalSelector.setControlFlag(Goal.Flag.MOVE, flag);
        this.goalSelector.setControlFlag(Goal.Flag.JUMP, flag && flag1);
        this.goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
    }

    public void tick() {
        super.tick();

        if(this.isTame()) {
            if(isPartyWill()){
                if(willDancing) {
                    danceAnimationState.start(this.tickCount);
                    System.out.println("dance vro");
                    willDancing = false;
                }
            }else{
                danceAnimationState.stop();
                willDancing = true;
                System.out.println("no dance");
            }
            for (Entity entity : this.level().getEntities(this, this.getBoundingBox().inflate(5.0))) {  // Search nearby entities
                if (entity instanceof Cat) {  // If entity is a bat
                    Cat cat = (Cat) entity;
                    if (!cat.isTame()) {
                        cat.setTame(true);
                        cat.setOwnerUUID(this.getOwnerUUID());
                        jerkAnimationState.start(this.tickCount);
                        isJerking = true;
                        jerkcount = 0;

                    }
                }
            }
            if(isJerking){
                jerkcount++;
            }
            if(jerkcount >= 80){
                isJerking = false;
                jerkAnimationState.stop();
            }
        }
        // Find the closest WilliamEntity within a radius (e.g., 10 blocks)
        for (Entity entity : this.level().getEntities(this, this.getBoundingBox().inflate(10.0))) {
            if (entity instanceof WilliamEntity) {
                // Make the bat fly in circles around William's head
                WilliamEntity william = (WilliamEntity) entity;

                // Set up a radius and speed for the circle
                double radius = 2.0; // Radius of the circle around William (adjust as needed)
                double speed = 1.0; // Speed of the bat's movement (adjust as needed)

                // Calculate the angle of movement for the circle
                double angle = this.tickCount * 0.1; // This determines the speed of the rotation

                // Calculate X and Z positions based on the angle
                double xOffset = radius * Mth.cos((float) angle);
                double zOffset = radius * Mth.sin((float) angle);

                // Calculate the target position above William's head (e.g., 2 blocks above)
                double targetX = william.getX() + xOffset;
                double targetY = william.getY() + 2.0;  // Slightly above William's head
                double targetZ = william.getZ() + zOffset;

                // Move the bat towards the calculated target position
                this.getNavigation().moveTo(targetX, targetY, targetZ, speed);
            }
        }



        if (!this.isSitting() &&standProgress <= maxStandTime) {
            startSittingAnimation = false;
            sitPoseAnimationState.stop();
            sittingAnimationState.stop();
            standProgress++;
            if(!this.startStandingAnimation){
                this.startStandingAnimation = true;
                this.standAnimationState.start(this.tickCount);}
        }if(!this.isSitting() &&standProgress >= maxStandTime){
            this.setStanding(true);
            checkForGoldAndPickItUp();
            this.startStandingAnimation = false;
            standAnimationState.stop();
        }

        if (this.isSitting() &&sitProgress < 85) {
            standAnimationState.stop();
            startStandingAnimation = false;
            this.setStanding(false);
            standProgress = 0;
            if (!startSittingAnimation) {
                this.sittingAnimationState.start(this.tickCount);
                startSittingAnimation = true;
                startSittingidle = false;
                sitPoseAnimationState.stop();

            }
            sitProgress++;

        } else {
            if (this.isSitting()){
                this.sittingAnimationState.stop();
                if(!this.startSittingidle) {
                    this.sitPoseAnimationState.start(this.tickCount);
                    startSittingidle = true;
                }
                startSittingAnimation = false;

            }else{
                sitProgress = 0;
            }
        }

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    public void onEatItem() {
        this.heal(10);
        this.level().broadcastEntityEvent(this, (byte) 92);
        this.gameEvent(GameEvent.EAT);
        this.playSound(SoundEvents.GENERIC_EAT, this.getSoundVolume(), this.getVoicePitch());
    }


    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 99) { // Custom event ID for death animation
            this.deadAnimationState.start(this.tickCount); // Start animation on client
        } else {
            super.handleEntityEvent(id);
        }
    }


    public boolean isStanding() {
        return this.entityData.get(STANDING);
    }

    public void setStanding(boolean standing) {
        this.entityData.set(STANDING, Boolean.valueOf(standing));
    }



    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STANDING, false);
        this.entityData.define(SITTING, false);
        this.entityData.define(COMMAND, 0);
    }
    private void setupAnimationStates() {
        if (this.isAlive()) {
            if (this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = this.random.nextInt(40) + 80;
                this.idleAnimationState.start(this.tickCount);
            }

        } else {
            --this.idleAnimationTimeout;
        }
    }
    @Override
    protected void updateWalkAnimation ( float pPartialTick){
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1F);
        } else {
            f = 0;
        }
        this.walkAnimation.update(f, 0.2f);
    }
    @Override
    protected void dropCustomDeathLoot(DamageSource damageSource, int lootingMultiplier, boolean wasRecentlyHit) {
        super.dropCustomDeathLoot(damageSource, lootingMultiplier, wasRecentlyHit); // Call the parent method to keep default drops

        // Drop a specific item
        ItemStack namedGoldNugget = new ItemStack(Items.GOLD_NUGGET);
        namedGoldNugget.setHoverName(Component.literal("Coins"));
        this.spawnAtLocation(namedGoldNugget, 1 + lootingMultiplier); // Drops 2 Coal, +1 per looting level

        // Drop a custom item
        /*ItemStack customItem = new ItemStack(Items.DIAMOND); // Replace with your modded item if needed
        customItem.setCount(1);
        this.spawnAtLocation(customItem);*/
        if (this.random.nextFloat() < 0.1f + (0.1f * lootingMultiplier)) { // 10% chance, increased by looting
            ItemStack namedBone = new ItemStack(Items.BONE);
            namedBone.setHoverName(Component.literal("Blow My Back Out"));
            this.spawnAtLocation(namedBone);
        }
        // Conditional drop example
        if (this.random.nextFloat() < 0.03f + (0.1f * lootingMultiplier)) { // 3% chance, increased by looting
            ItemStack namedWetSponge = new ItemStack(Items.WET_SPONGE);
            namedWetSponge.setHoverName(Component.literal("Was I wet enough?"));
            this.spawnAtLocation(namedWetSponge);
        }
        if (this.random.nextFloat() < 0.05f + (0.1f * lootingMultiplier)) { // 3% chance, increased by looting
            ItemStack namedSlime = new ItemStack(Items.SLIME_BALL);
            namedSlime.setHoverName(Component.literal("Bounce on it vro"));
            this.spawnAtLocation(namedSlime);
        }
    }

    private void checkForGoldAndPickItUp() {
        // Check nearby items for gold
        double detectionRange = 0.5D; // You can adjust the range
        ItemEntity goldItem = findNearbyGoldItem(detectionRange);

        if (goldItem != null) {
            pickUpGoldAndThrowCoal(goldItem);
        }
    }

    private ItemEntity findNearbyGoldItem(double range) {
        // Look for gold items in range
        for (ItemEntity itemEntity : this.level().getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(range))) {
            if (itemEntity.getItem().getItem() == Items.GOLD_NUGGET) {
                return itemEntity;
            }
        }
        return null; // No gold item found in range
    }
    public void pickUpGoldAndThrowCoal(ItemEntity goldItem) {
        // First, simulate William picking up the gold (you can either add it to his inventory or just make him hold it temporarily)
        goldItem.discard();  // Remove the gold item from the world

        // Simulate throwing coal back
        throwCoalBack(goldItem);
    }
    private void throwCoalBack(ItemEntity goldItem) {
        Level world = this.getCommandSenderWorld();
        // Create a new ItemEntity for coal
        ItemEntity coalItem = new ItemEntity(world, this.getX(), this.getY() +1, this.getZ(), new ItemStack(Items.COAL));

        // Set some random velocity for the thrown coal (you can adjust the direction as needed)
        double velocityX = Math.random() * 0.3 - 0.25; // Random velocity around William's position
        double velocityY = 0.3;  // Throw it upwards
        double velocityZ = Math.random() * 0.3 - 0.25;

        coalItem.setDeltaMovement(velocityX, velocityY, velocityZ);

        // Spawn the coal item in the world
        this.getCommandSenderWorld().addFreshEntity(coalItem);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.WILLIAM.get().create(pLevel);
    }

    public void travel(Vec3 vec3d) {
        if (this.isSitting()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            vec3d = Vec3.ZERO;
        }
        super.travel(vec3d);
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int pRemainingPersistentAngerTime) {

    }

    @Override
    public @Nullable UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID pPersistentAngerTarget) {

    }

    @Override
    public void startPersistentAngerTimer() {

    }

    public boolean shouldFollow() {
        return getCommand() == 1;
    }

    class StrollGoal extends MoveThroughVillageGoal {
        public StrollGoal(int p_i50726_3_) {
            super(WilliamEntity.this, 1.0D, true, p_i50726_3_, () -> false);
        }

        public void start() {
            super.start();
        }

        public boolean canUse() {
            return super.canUse() && this.canFoxMove();
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.canFoxMove();
        }

        private boolean canFoxMove() {
            return !WilliamEntity.this.isSitting();
        }
    }

}

