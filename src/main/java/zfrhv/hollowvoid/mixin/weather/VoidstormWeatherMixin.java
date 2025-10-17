package zfrhv.hollowvoid.mixin.weather;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WeatherRendering;
import net.minecraft.client.render.state.WeatherRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.math.random.Random;
import zfrhv.hollowvoid.world.ModDimension;
import zfrhv.hollowvoid.mixin_intefaces.VoidWeatherRenderingPiece;

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
                           @Local(ordinal = 5) int m,
                           @Local(ordinal = 7) int o,
                           @Local(ordinal = 8) int p,
                           @Local(ordinal = 4) int l,
                           @Local(ordinal = 11) int s) {
        Biome.Precipitation precipitation = precipitationRef.get();
        if (world.getRegistryKey() == ModDimension.VOIDDIM) {
            if (weatherRenderState instanceof VoidWeatherRenderingPiece voidWeatherRenderState) {
                // just keep biomes warm at rain weather, cuz snow keep spawning on ground
                voidWeatherRenderState.getVoidPieces().add(this.createVoidStormPiece(random, ticks, m, o, p, l, s, tickProgress));
                precipitationRef.set(Biome.Precipitation.NONE);
            }
        }
    }

    private WeatherRendering.Piece createVoidStormPiece(Random random, int ticks, int x, int yMin, int yMax, int z, int light, float tickProgress) {
        float f = ticks + tickProgress;
        float g = (float)(random.nextDouble() + f * 0.01F * (float)random.nextGaussian());
        float h = (float)(random.nextDouble() + f * (float)random.nextGaussian() * 0.001F);
        float i = -((ticks & 511) + tickProgress) / 512.0F;
        return new WeatherRendering.Piece(x, z, yMin, yMax, g, i + h, light);
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


    @Redirect(
            method = "addParticlesAndSound",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/WeatherRendering;getPrecipitationAt(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/world/biome/Biome$Precipitation;"
            )
    )
    private Biome.Precipitation removeFloorRain(WeatherRendering weatherRendering, World world, BlockPos blockPos3) {
        if (world.getRegistryKey() == ModDimension.VOIDDIM) {
            return Biome.Precipitation.NONE;
        } else {
            return getPrecipitationAt(world, blockPos3);
        }
    }

    @Shadow
    private Biome.Precipitation getPrecipitationAt(World world, BlockPos pos) {
        throw new AssertionError(); // never called, just required by @Shadow
    }
}
