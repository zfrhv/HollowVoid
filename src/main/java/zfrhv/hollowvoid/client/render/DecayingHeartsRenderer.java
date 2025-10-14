package zfrhv.hollowvoid.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import zfrhv.hollowvoid.effects.ModEffects;

@Environment(EnvType.CLIENT)
public class DecayingHeartsRenderer implements HudElement {
    private static final Identifier HEART_CONTAINER = Identifier.of("minecraft", "hud/heart/container");
    private static final Identifier NORMAL_HEART = Identifier.of("minecraft", "hud/heart/full");
    private static final Identifier[] DECAYING_HEARTS = new Identifier[10];
    static {
        for (int i = 0; i < 10; i++) {
            DECAYING_HEARTS[i] = Identifier.of("hollowvoid", "hud/heart/decaying_heart_" + i);
        }
    }

    @Override
    public void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        StatusEffectInstance reduceHeartsEffect = client.player.getStatusEffect(ModEffects.REDUCE_HEARTS);

        if (reduceHeartsEffect == null) return;

        int duration = reduceHeartsEffect.getDuration();

        if (!(duration > 0)) return;

//        int heartsPerSecond = reduceHeartsEffect.getAmplifier() + 1;
//        float heartsPerTick = heartsPerSecond / 20f;
//        float ticksPerHeart = 1 / heartsPerTick;
//        int duration = duration;
//        int heartsToDraw = (int)(duration / ticksPerHeart);

        int heartsToDraw = duration * (reduceHeartsEffect.getAmplifier() + 1) / 20;

        int scaledWidth = client.getWindow().getScaledWidth();
        int scaledHeight = client.getWindow().getScaledHeight();

        // Base health bar position
        int xBase = scaledWidth / 2 - 91 + (int)client.player.getMaxHealth()/2 * 8; // center of screen minus 91 pixels
        int yBase = scaledHeight - 39;    // distance from bottom of screen


        for (int i = 0; i < heartsToDraw; i++) {
            int x = xBase + i * 8;
            int y = yBase;

            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, HEART_CONTAINER, x, y, 9, 9);
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, NORMAL_HEART, x, y, 9, 9);
        }
        int frame = (duration - heartsToDraw * 20)/2;
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, DECAYING_HEARTS[frame], xBase + heartsToDraw * 8, yBase, 9, 9);
    }
}
