package zfrhv.hollowvoid.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.GameModeSwitcherScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import zfrhv.hollowvoid.entity.DialogueRequestPayload;
import zfrhv.hollowvoid.entity.EntitySpeechOption;
import zfrhv.hollowvoid.item.ShadeCloak.DashRequestPayload;

@Environment(EnvType.CLIENT)
public class DialogueScreen extends Screen {
    private final int npcId;
    private final Text npcName;
    private final EntitySpeechOption[] options;

    public DialogueScreen(int npcId, Text npcName, EntitySpeechOption[] options) {
        super(Text.literal("Dialogue"));
        this.npcId = npcId;
        this.npcName = npcName;
        this.options = options;
    }

    @Override
    protected void init() {
        // Add buttons for each option
        int x = this.width / 2;
        int y = this.height / 2;
        for (int i = 0; i < options.length; i++) {
            final int index = i;

            this.addDrawableChild(ButtonWidget.builder(Text.literal(options[index].question), button -> {
                ClientPlayNetworking.send(new DialogueRequestPayload(npcId, options[index].index));
                MinecraftClient.getInstance().player.closeScreen();
            }).dimensions(x, y, 200, 20).build());

            y += 25;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        // TODO what does this do?
        context.drawCenteredTextWithShadow(this.textRenderer, npcName, this.width / 2, 20, 0xFFFF00);
        super.render(context, mouseX, mouseY, deltaTicks);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
