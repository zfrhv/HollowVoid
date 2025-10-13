package zfrhv.hollowvoid.mixin;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.SleepingChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SleepingChatScreen.class)
public class SleepingChatScreenMixin extends ChatScreen {
    @Shadow
    private ButtonWidget stopSleepingButton;

    public SleepingChatScreenMixin(String string, boolean bl) {
        super(string, bl);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void moveLeaveBedButton(CallbackInfo ci) {
        this.stopSleepingButton.setY(this.height / 2 - this.stopSleepingButton.getHeight()/2);
    }
}
