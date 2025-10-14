package zfrhv.hollowvoid;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.util.Identifier;
import zfrhv.hollowvoid.client.render.DecayingHeartsRenderer;
import zfrhv.hollowvoid.client.render.FormingHeartsRenderer;

public class HollowVoidClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HudElementRegistry.addLast(Identifier.of("hollowvoid", "decaying_hearts"), new DecayingHeartsRenderer());
        HudElementRegistry.addLast(Identifier.of("hollowvoid", "forming_hearts"), new FormingHeartsRenderer());
    }
}
