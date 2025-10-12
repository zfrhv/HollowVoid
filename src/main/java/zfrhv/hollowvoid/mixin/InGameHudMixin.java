package zfrhv.hollowvoid.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.profiler.Profilers;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow
    @Nullable
    private Text overlayMessage;

    @Shadow
    private int overlayRemaining;

    @Shadow
    private boolean overlayTinted;

    @Shadow
    public abstract TextRenderer getTextRenderer();

    @Inject(
            method = "renderOverlayMessage",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;overlayMessage:Lnet/minecraft/text/Text;",
                    opcode = Opcodes.GETFIELD
            ),
            cancellable = true
    )
    private void centerLeaveBedOverlay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {

//        if (overlayMessage != null && overlayMessage.getString().equals("Leave Bed")) {
//        if (overlayMessage != null && overlayMessage.getString().equals("multiplayer.stopSleeping")) {
//        if (overlayMessage != null && overlayMessage.getString().equals(Text.translatable("multiplayer.stopSleeping").getString())) {

        if (overlayMessage != null) {
            System.out.println("AAAAAAAAAA" + overlayMessage.getString());
        }

        if (overlayMessage != null && overlayMessage.getString().equals(Text.translatable("multiplayer.stopSleeping").getString())) {
            TextRenderer textRenderer = getTextRenderer();
            if (overlayRemaining > 0) {
                Profilers.get().push("overlayMessage");
                float f = overlayRemaining - tickCounter.getTickProgress(false);
                int i = (int)(f * 255.0F / 20.0F);
                if (i > 255) {
                    i = 255;
                }

                if (i > 0) {
                    context.createNewRootLayer();
                    context.getMatrices().pushMatrix();
                    context.getMatrices().translate(context.getScaledWindowWidth() / 2, context.getScaledWindowHeight() / 2);
                    int j;
                    if (overlayTinted) {
                        j = MathHelper.hsvToArgb(f / 50.0F, 0.7F, 0.6F, i);
                    } else {
                        j = ColorHelper.withAlpha(i, Colors.WHITE);
                    }

                    int k = textRenderer.getWidth(overlayMessage);
                    context.drawTextWithBackground(textRenderer, overlayMessage, -k / 2, -4, k, j);
                    context.getMatrices().popMatrix();
                }

                Profilers.get().pop();
            }
            ci.cancel();
        }
    }
}