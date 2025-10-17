package zfrhv.hollowvoid.mixin.weather;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.WeatherRendering;
import net.minecraft.client.render.state.WeatherRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zfrhv.hollowvoid.mixin_intefaces.VoidWeatherRenderingPiece;

import java.util.ArrayList;
import java.util.List;

@Mixin(WeatherRenderState.class)
@Environment(EnvType.CLIENT)
public class WeatherRenderStateMixin implements VoidWeatherRenderingPiece {
    @Unique
    public final List<WeatherRendering.Piece> voidPieces = new ArrayList();

    @Override
    public List<WeatherRendering.Piece> getVoidPieces() {
        return voidPieces;
    }

    @Inject(
            method = "clear",
            at = @At("HEAD")
    )
    private void onClear(CallbackInfo ci) {
        this.voidPieces.clear();
    }
}

