package zfrhv.hollowvoid;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.util.Identifier;
import zfrhv.hollowvoid.client.render.DecayingHeartsRenderer;
import zfrhv.hollowvoid.client.render.FormingHeartsRenderer;
import zfrhv.hollowvoid.entity.ModEntities;
import zfrhv.hollowvoid.entity.scythe.ScytheModel;
import zfrhv.hollowvoid.entity.scythe.ScytheRenderer;
import zfrhv.hollowvoid.entity.void_fox.VoidFoxModel;
import zfrhv.hollowvoid.entity.void_fox.VoidFoxRenderer;

public class HollowVoidClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HudElementRegistry.addLast(Identifier.of("hollowvoid", "decaying_hearts"), new DecayingHeartsRenderer());
        HudElementRegistry.addLast(Identifier.of("hollowvoid", "forming_hearts"), new FormingHeartsRenderer());

        EntityModelLayerRegistry.registerModelLayer(VoidFoxModel.VOID_FOX_LAYER, VoidFoxModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.VOID_FOX, VoidFoxRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ScytheModel.SCYTHE_LAYER, ScytheModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.SCYTHE, ScytheRenderer::new);
    }
}
