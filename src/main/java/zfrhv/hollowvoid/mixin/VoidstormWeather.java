package zfrhv.hollowvoid.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WeatherRendering;
import net.minecraft.client.render.state.WeatherRenderState;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import zfrhv.hollowvoid.world.ModDimension;

@Mixin(WeatherRendering.class)
@Environment(EnvType.CLIENT)
public class VoidstormWeather {
    @Inject(
            method = "buildPrecipitationPieces",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/biome/Biome$Precipitation;RAIN:Lnet/minecraft/world/biome/Biome$Precipitation;",
                    shift = At.Shift.BEFORE
            )
    )
    private void voidStorm(World world, int ticks, float tickProgress, Vec3d vec3d, WeatherRenderState weatherRenderState,
                           CallbackInfo ci,
                           @Local LocalRef<Biome.Precipitation> precipitationRef,
                           @Local Random random,
                           @Local(ordinal = 5) int m,
                           @Local(ordinal = 7) int o,
                           @Local(ordinal = 8) int p,
                           @Local(ordinal = 4) int l,
                           @Local(ordinal = 11) int s) {
        Biome.Precipitation precipitation = precipitationRef.get();
        if (world.getRegistryKey() == ModDimension.VOIDDIM) {
            if (precipitation == Biome.Precipitation.RAIN) {
                weatherRenderState.rainPieces.add(this.createVoidStormPiece(random, ticks, m, o, p, l, s, tickProgress));
            } else if (precipitation == Biome.Precipitation.SNOW) {
                weatherRenderState.snowPieces.add(this.createVoidStormPiece(random, ticks, m, o, p, l, s, tickProgress));
            }
            precipitationRef.set(Biome.Precipitation.NONE);
        }
    }

    // TODO remove rain particles from floor, and snow spawn from snowy
    private WeatherRendering.Piece createVoidStormPiece(Random random, int ticks, int x, int yMin, int yMax, int z, int light, float tickProgress) {
        float f = ticks + tickProgress;
        float g = (float)(random.nextDouble() + f * 0.01F * (float)random.nextGaussian());
        float h = (float)(random.nextDouble() + f * (float)random.nextGaussian() * 0.001F);
        float i = -((ticks & 511) + tickProgress) / 512.0F;
        return new WeatherRendering.Piece(x, z, yMin, yMax, g, i + h, light);
    }
}
