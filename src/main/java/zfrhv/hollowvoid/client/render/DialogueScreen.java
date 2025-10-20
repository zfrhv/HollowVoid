package zfrhv.hollowvoid.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import zfrhv.hollowvoid.entity.DialogueRequestPayload;

import java.util.List;

@Environment(EnvType.CLIENT)
public class DialogueScreen extends Screen {
    private final int npcId;
    private final Text npcName;
    List<String> askableQuestions;
    List<Integer> questionsIndexes;

    public DialogueScreen(int npcId, Text npcName, List<String> askableQuestions, List<Integer> questionsIndexes) {
        super(Text.literal("Dialogue"));
        this.npcId = npcId;
        this.npcName = npcName;
        this.askableQuestions = askableQuestions;
        this.questionsIndexes = questionsIndexes;
    }

    @Override
    protected void init() {
        // Add buttons for each option
        int x = this.width / 2;
        int y = this.height / 2;
        for (int i = 0; i < askableQuestions.size(); i++) {
            final int index = i;

            this.addDrawableChild(new ClickableTextWidget(x, y, Text.literal(askableQuestions.get(index))) {
                @Override
                public void onRelease(Click click) {
                    if (isMouseOver(click.x(), click.y())) {
                        ClientPlayNetworking.send(new DialogueRequestPayload(npcId, questionsIndexes.get(index)));
                        MinecraftClient.getInstance().player.closeScreen();
                    }
                }

            });

            y += 13;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        int nameWidth = MinecraftClient.getInstance().textRenderer.getWidth(npcName);
        context.fill(this.width / 2 - nameWidth/2 -2, this.height/2 - 20 -2, this.width / 2 + nameWidth/2 +2, this.height/2 - 11 +2, 0xBB000000);
        context.drawText(this.textRenderer, npcName, this.width / 2 - nameWidth/2, this.height/2 - 20, 0xFFFFFFFF, false);
        super.render(context, mouseX, mouseY, deltaTicks);
        // TODO remove the cursor
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {}

    @Override
    public boolean shouldPause() {
        return false;
    }
}
