package zfrhv.hollowvoid;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.util.Identifier;
import zfrhv.hollowvoid.client.render.DecayingHeartsRenderer;
import zfrhv.hollowvoid.client.render.FormingHeartsRenderer;
import zfrhv.hollowvoid.entity.ModEntities;
import zfrhv.hollowvoid.entity.client.VoidFoxModel;
import zfrhv.hollowvoid.entity.client.VoidFoxRenderer;

public class HollowVoidClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HudElementRegistry.addLast(Identifier.of("hollowvoid", "decaying_hearts"), new DecayingHeartsRenderer());
        HudElementRegistry.addLast(Identifier.of("hollowvoid", "forming_hearts"), new FormingHeartsRenderer());

        EntityModelLayerRegistry.registerModelLayer(VoidFoxModel.VOID_FOX_LAYER, VoidFoxModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.VOID_FOX, VoidFoxRenderer::new);
    }
}
