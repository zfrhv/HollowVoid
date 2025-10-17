package zfrhv.hollowvoid.mixin.sweep_attack;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.BillboardParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SweepAttackParticle;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SweepAttackParticle.class)
@Environment(EnvType.CLIENT)
public class SweepAttackParticleMixin extends Particle {
    protected SweepAttackParticleMixin(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    public ParticleTextureSheet textureSheet() {
        return ParticleTextureSheet.SINGLE_QUADS;
    }

    @Inject(method = "tick", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/particle/SweepAttackParticle;z:D",
            shift = At.Shift.AFTER
    ))
    private void moveParticle(CallbackInfo ci) {
        this.x += this.velocityX;
        this.y += this.velocityY;
        this.z += this.velocityZ;
    }
}
