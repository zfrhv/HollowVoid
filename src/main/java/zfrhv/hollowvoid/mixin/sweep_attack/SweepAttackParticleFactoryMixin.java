package zfrhv.hollowvoid.mixin.sweep_attack;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.SweepAttackParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SweepAttackParticle.Factory.class)
@Environment(EnvType.CLIENT)
public class SweepAttackParticleFactoryMixin {
    @Shadow
    private SpriteProvider spriteProvider;

    @Inject(
            method = "createParticle",
            at = @At("RETURN"),
            cancellable = true
    )
    private void returnCreateParticle(
            SimpleParticleType type,
            ClientWorld clientWorld,
            double x, double y, double z,
            double velocityX, double velocityY, double velocityZ,
            Random random,
            CallbackInfoReturnable<Particle> cir
    ) {
        Particle sweepAttackParticle = cir.getReturnValue();
        sweepAttackParticle.scale(1.3f);
        sweepAttackParticle.setVelocity(velocityX, velocityY, velocityZ);
        cir.setReturnValue(sweepAttackParticle);
    }
}
