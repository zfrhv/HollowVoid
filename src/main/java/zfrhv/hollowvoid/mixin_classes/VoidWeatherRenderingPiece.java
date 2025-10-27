package zfrhv.hollowvoid.mixin_classes;

import net.minecraft.client.render.WeatherRendering;
import zfrhv.hollowvoid.mixin.weather.VoidstormWeatherMixin;

import java.util.List;

public interface VoidWeatherRenderingPiece {
    public List<WeatherRendering.Piece> getVoidPieces();
}
