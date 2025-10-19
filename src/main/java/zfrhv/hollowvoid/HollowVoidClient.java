package zfrhv.hollowvoid;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.impl.resource.loader.BuiltinModResourcePackSource;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.resource.server.ServerResourcePackManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import zfrhv.hollowvoid.client.render.DecayingHeartsRenderer;
import zfrhv.hollowvoid.client.render.FormingHeartsRenderer;
import zfrhv.hollowvoid.entity.ModEntities;
import zfrhv.hollowvoid.entity.scythe.ScytheModel;
import zfrhv.hollowvoid.entity.scythe.ScytheRenderer;
import zfrhv.hollowvoid.entity.void_fox.VoidFoxModel;
import zfrhv.hollowvoid.entity.void_fox.VoidFoxRenderer;
import zfrhv.hollowvoid.item.ShadeCloak.SendClientMovement;

@Environment(EnvType.CLIENT)
public class HollowVoidClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HudElementRegistry.addLast(Identifier.of("hollowvoid", "decaying_hearts"), new DecayingHeartsRenderer());
        HudElementRegistry.addLast(Identifier.of("hollowvoid", "forming_hearts"), new FormingHeartsRenderer());

        EntityModelLayerRegistry.registerModelLayer(VoidFoxModel.VOID_FOX_LAYER, VoidFoxModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.VOID_FOX, VoidFoxRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ScytheModel.SCYTHE_LAYER, ScytheModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.SCYTHE, ScytheRenderer::new);

        SendClientMovement.register();

        ResourceManagerHelper.registerBuiltinResourcePack(
                Identifier.of("hollowvoid", "better_lanterns"),
                FabricLoader.getInstance().getModContainer("hollowvoid").get(),
                ResourcePackActivationType.DEFAULT_ENABLED
        );
    }
}
