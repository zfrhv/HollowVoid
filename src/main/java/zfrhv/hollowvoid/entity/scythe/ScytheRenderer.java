package zfrhv.hollowvoid.entity.scythe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import zfrhv.hollowvoid.HollowVoid;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ScytheRenderer extends EntityRenderer<ScytheEntity, ScytheRenderState> {
    public Identifier TEXTURE = Identifier.of(HollowVoid.MOD_ID, "textures/entity/void_scythe_model.png");

    protected ScytheModel model;

    public ScytheRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new ScytheModel(context.getPart(ScytheModel.SCYTHE_LAYER));
    }

    @Override
    public ScytheRenderState createRenderState() {
        return new ScytheRenderState();
    }

    public void updateRenderState(ScytheEntity scytheEntity, ScytheRenderState scytheRenderState, float f) {
        super.updateRenderState(scytheEntity, scytheRenderState, f);
        scytheRenderState.yaw = scytheEntity.getLerpedYaw(f);
        scytheRenderState.pitch = scytheEntity.getLerpedPitch(f);
        scytheRenderState.rotation = scytheEntity.getRenderingRotation();
        scytheRenderState.enchanted = scytheEntity.isEnchanted();
        scytheRenderState.isGrounded = scytheEntity.isInGround();
    }

    public void render(
            ScytheRenderState scytheRenderState,
            MatrixStack matrixStack,
            OrderedRenderCommandQueue orderedRenderCommandQueue,
            CameraRenderState cameraRenderState
    ) {
        matrixStack.push();

        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(scytheRenderState.yaw - 90.0F));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(scytheRenderState.pitch + 90.0F));
        if (!scytheRenderState.isGrounded) {
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(scytheRenderState.rotation * 20f + 270));
        } else {
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(300));
        }

        List<RenderLayer> list = ItemRenderer.getGlintRenderLayers(this.model.getLayer(TEXTURE), false, scytheRenderState.enchanted);

        for (int i = 0; i < list.size(); i++) {
            orderedRenderCommandQueue.getBatchingQueue(i)
                    .submitModel(
                            this.model,
                            scytheRenderState,
                            matrixStack,
                            (RenderLayer)list.get(i),
                            scytheRenderState.light,
                            OverlayTexture.DEFAULT_UV,
                            -1,
                            null,
                            scytheRenderState.outlineColor,
                            null
                    );
        }

        matrixStack.pop();
        super.render(scytheRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
    }
}
