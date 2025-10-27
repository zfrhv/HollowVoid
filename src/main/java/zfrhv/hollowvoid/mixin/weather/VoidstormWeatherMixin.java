package zfrhv.hollowvoid.mixin.weather;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.state.WeatherRenderState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticlesMode;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.math.random.Random;
import zfrhv.hollowvoid.sound.ModSounds;
import zfrhv.hollowvoid.world.ModDimension;
import zfrhv.hollowvoid.mixin_classes.VoidWeatherRenderingPiece;

import java.util.List;

@Mixin(WeatherRendering.class)
@Environment(EnvType.CLIENT)
public class VoidstormWeatherMixin {
    @Inject(
            method = "buildPrecipitationPieces",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/biome/Biome$Precipitation;RAIN:Lnet/minecraft/world/biome/Biome$Precipitation;",
                    shift = At.Shift.BEFORE
            )
    )
    private void checkDimension(World world, int ticks, float tickProgress, Vec3d vec3d, WeatherRenderState weatherRenderState,
                           CallbackInfo ci,
                           @Local LocalRef<Biome.Precipitation> precipitationRef,
                           @Local Random random,
                           @Local(ordinal = 1) int playerX,
                           @Local(ordinal = 3) int playerZ,
                           @Local(ordinal = 5) int x,
                           @Local(ordinal = 7) int yMin,
                           @Local(ordinal = 8) int yMax,
                           @Local(ordinal = 4) int z,
                           @Local(ordinal = 11) int lightCoords) {
        Biome.Precipitation precipitation = precipitationRef.get();
        if (world.getRegistryKey() == ModDimension.VOIDDIM) {
            if (weatherRenderState instanceof VoidWeatherRenderingPiece voidWeatherRenderState) {
                // just keep biomes warm at rain weather, cuz snow keep spawning on ground
                voidWeatherRenderState.getVoidPieces().add(this.createVoidStormPiece(random, ticks, x, yMin, yMax, z, lightCoords, tickProgress, x - playerX, z - playerZ));
                precipitationRef.set(Biome.Precipitation.NONE);
            }
        }
    }

    private WeatherRendering.Piece createVoidStormPiece(Random random, int ticks, int x, int yMin, int yMax, int z, int lightCoords, float tickProgress, int relativeX, int relativeZ) {
        float time = ticks + tickProgress;
        double degree = Math.atan2(relativeZ, relativeX);
        final float minUSpeed = 0.15F;

        float uSpeed = (float)random.nextGaussian() * 0.1F + 0.4F * (float) Math.cos(degree);
        if (0 <= uSpeed && uSpeed < minUSpeed) {uSpeed = minUSpeed;}
        if (-minUSpeed < uSpeed && uSpeed < 0) {uSpeed = -minUSpeed;}
        float vSpeed = (float)random.nextGaussian() * 0.002F - 0.02F;

        float uOffset = random.nextFloat() + time * uSpeed;
        float vOffset = random.nextFloat() + time * vSpeed;

        return new WeatherRendering.Piece(x, z, yMin, yMax, uOffset, vOffset, lightCoords);
    }

    @Inject(
            method = "renderPrecipitation",
            at = @At("HEAD")
    )
    private void onRenderPrecipitation(VertexConsumerProvider vertexConsumers, Vec3d pos, WeatherRenderState weatherRenderState, CallbackInfo ci) {
        if (weatherRenderState instanceof VoidWeatherRenderingPiece voidWeatherRenderState) {
            if (!voidWeatherRenderState.getVoidPieces().isEmpty()) {
                RenderLayer renderLayer = RenderLayer.getWeather(Identifier.of("hollowvoid", "textures/environment/ashes.png"), MinecraftClient.isFabulousGraphicsOrBetter());
                renderPieces(vertexConsumers.getBuffer(renderLayer), voidWeatherRenderState.getVoidPieces(), pos, 1.0F, weatherRenderState.radius, weatherRenderState.intensity);
            }
        }
    }

    @Shadow
    private void renderPieces(VertexConsumer vertexConsumer, List<WeatherRendering.Piece> pieces, Vec3d pos, float intensity, int range, float gradient) {
        throw new AssertionError(); // never called, just required by @Shadow
    }


    @ModifyVariable(
            method = "addParticlesAndSound",
            at = @At("HEAD")
    )
    private ParticlesMode removeFloorRain(ParticlesMode particlesMode, @Local ClientWorld world) {
        if (world.getRegistryKey() == ModDimension.VOIDDIM) {
            return ParticlesMode.MINIMAL;
        } else {
            return particlesMode;
        }
    }

    @Redirect(
            method = "addParticlesAndSound",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/sound/SoundEvents;WEATHER_RAIN:Lnet/minecraft/sound/SoundEvent;"
            )
    )
    private SoundEvent addWindSound(@Local ClientWorld world) {
        if (world.getRegistryKey() == ModDimension.VOIDDIM) {
            return ModSounds.WIND_OUTSIDE;
        } else {
            return SoundEvents.WEATHER_RAIN;
        }
    }

    @Redirect(
            method = "addParticlesAndSound",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/sound/SoundEvents;WEATHER_RAIN_ABOVE:Lnet/minecraft/sound/SoundEvent;"
            )
    )
    private SoundEvent addWindSound2(@Local ClientWorld world) {
        if (world.getRegistryKey() == ModDimension.VOIDDIM) {
            return ModSounds.WIND_INSIDE;
        } else {
            return SoundEvents.WEATHER_RAIN_ABOVE;
        }
    }
}