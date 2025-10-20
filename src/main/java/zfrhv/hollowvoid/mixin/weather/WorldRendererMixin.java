package zfrhv.hollowvoid.mixin.weather;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.SkyRendering;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import zfrhv.hollowvoid.world.ModDimension;

@Mixin(WorldRenderer.class)
@Environment(EnvType.CLIENT)
public class WorldRendererMixin {
    @Shadow
    private SkyRendering skyRendering;

    @Shadow
    @Nullable
    private ClientWorld world;

    @ModifyArg(method = "renderSky", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/FramePass;setRenderer(Ljava/lang/Runnable;)V"
    ), index = 0)
    private Runnable onRenderSky(Runnable runnable, @Local GpuBufferSlice fogBuffer) {
        if (world.getRegistryKey() == ModDimension.VOIDDIM) {
            return () -> {
                RenderSystem.setShaderFog(fogBuffer);
                skyRendering.renderSkyDark();
            };
        } else {
            return runnable;
        }
    }
}
