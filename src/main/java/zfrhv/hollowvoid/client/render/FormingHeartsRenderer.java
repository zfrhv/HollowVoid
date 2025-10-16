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
import net.minecraft.world.GameMode;
import zfrhv.hollowvoid.effects.ModEffects;

@Environment(EnvType.CLIENT)
public class FormingHeartsRenderer implements HudElement {
    private static final Identifier HEART_CONTAINER = Identifier.of("minecraft", "hud/heart/container");
    private static final Identifier NORMAL_HEART = Identifier.of("minecraft", "hud/heart/full");
    private static final Identifier[] FORMING_HEARTS = new Identifier[10];
    static {
        for (int i = 0; i < 10; i++) {
            FORMING_HEARTS[i] = Identifier.of("hollowvoid", "hud/heart/forming_heart_" + i);
        }
    }

    @Override
    public void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        if (client.player.getGameMode() != GameMode.SURVIVAL) return;

        StatusEffectInstance increaseHeartsEffect = client.player.getStatusEffect(ModEffects.INCREASE_HEARTS);

        if (increaseHeartsEffect == null) return;

        int duration = increaseHeartsEffect.getDuration();

        if (!(duration > 0)) return;
        if (duration >= 20) return;

        int scaledWidth = client.getWindow().getScaledWidth();
        int scaledHeight = client.getWindow().getScaledHeight();

        // Base health bar position
        int xBase = scaledWidth / 2 - 91 + ((int)client.player.getMaxHealth()/2 - 1) * 8;
        int yBase = scaledHeight - 39;

        int frame = duration/2;
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, FORMING_HEARTS[frame], xBase, yBase, 9, 9);
    }
}
