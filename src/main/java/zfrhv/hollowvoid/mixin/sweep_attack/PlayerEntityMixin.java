package zfrhv.hollowvoid.mixin.sweep_attack;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.PlayerLikeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin extends PlayerLikeEntity {
    @Unique
    public double realLastX = Double.NaN;
    @Unique
    public double realLastY = Double.NaN;
    @Unique
    public double realLastZ = Double.NaN;
    @Unique
    public double realForwardPlayerVelocity;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    public void onTick(CallbackInfo ci) {
        if (!Double.isNaN(this.realLastX)) {
            double dx = this.getX() - this.realLastX;
            double dy = this.getY() - this.realLastY;
            double dz = this.getZ() - this.realLastZ;
            Vec3d lookDirection = this.getRotationVector();
            Vec3d velocity = new Vec3d(dx, dy, dz);
            this.realForwardPlayerVelocity = velocity.dotProduct(lookDirection);
        }
        this.realLastX = this.getX();
        this.realLastY = this.getY();
        this.realLastZ = this.getZ();
    }

    @Inject(
            method = "spawnSweepAttackParticles",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onSpawnSweepAttackParticles(CallbackInfo ci) {
        // i wish i could allow him to sweep when sprinting, but player.isSprinting() is broken thus they just cancelled it.
        double d = -MathHelper.sin(this.getYaw() * (float) (Math.PI / 180.0));
        double e = MathHelper.cos(this.getYaw() * (float) (Math.PI / 180.0));
        if (this.getEntityWorld() instanceof ServerWorld) {
            ((ServerWorld)this.getEntityWorld()).spawnParticles(ParticleTypes.SWEEP_ATTACK, this.getX() + d, this.getBodyY(0.5), this.getZ() + e, 0, d, 0.0, e, this.realForwardPlayerVelocity);
        }
        ci.cancel();
    }
}
