package zfrhv.hollowvoid.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public class ClickableTextWidget extends ClickableWidget {
    public ClickableTextWidget(int x, int y, Text text) {
        super(
                x - MinecraftClient.getInstance().textRenderer.getWidth(text)/2,
                y,
                MinecraftClient.getInstance().textRenderer.getWidth(text),
                9,
                text
        );
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int color = isHovered() ? 0xFFFFFF00 : 0xFFFFFFFF;
        context.fill(getX()-2, getY()-2, getX() + getWidth()+2, getY() + getHeight()+2, 0xBB000000);
        context.drawText(MinecraftClient.getInstance().textRenderer, getMessage(), getX(), getY(), color, false);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }
}
