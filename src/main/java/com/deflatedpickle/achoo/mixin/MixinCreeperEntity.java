package com.deflatedpickle.achoo.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings({"UnusedMixin", "unused"})
@Mixin(CreeperEntity.class)
abstract public class MixinCreeperEntity extends HostileEntity {
    @Shadow private int currentFuseTime;
    @Shadow private int fuseTime;
    @Shadow private int lastFuseTime;
    @Shadow @Final private static TrackedData<Boolean> IGNITED;
    @Shadow @Final private static TrackedData<Integer> FUSE_SPEED;
    @Shadow protected abstract void spawnEffectsCloud();

    @Shadow public abstract void setTarget(@Nullable LivingEntity target);

    protected MixinCreeperEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * @author DeflatedPickle
     * @reason im lazy
     */
    @Overwrite
    public void explode() {
        if (!this.world.isClient) {
            this.spawnEffectsCloud();
        }

        this.world.playSound(
                this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENTITY_TNT_PRIMED,
                SoundCategory.HOSTILE,
                1.0f, this.random.nextFloat() * 0.4f + 0.8f,
                false
        );

        Vec3d vec3d = this.getVelocity();
        this.world.addParticle(
                ParticleTypes.SNEEZE,
                this.getX() - (double)(this.getWidth() + 1.0f) * 0.5 * (double) MathHelper.sin(this.bodyYaw * ((float)Math.PI / 180)),
                this.getEyeY() - (double)0.1f,
                this.getZ() + (double)(this.getWidth() + 1.0f) * 0.5 * (double)MathHelper.cos(this.bodyYaw * ((float)Math.PI / 180)),
                vec3d.x, 0.0, vec3d.z
        );

        this.lastFuseTime = 0;
        this.currentFuseTime = 0;
        this.dataTracker.set(IGNITED, false);
    }
}
