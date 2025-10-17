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
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import zfrhv.hollowvoid.HollowVoid;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ScytheRenderer extends EntityRenderer<ScytheEntity, ScytheRenderState> {
    private static final Identifier TEXTURE = Identifier.of(HollowVoid.MOD_ID, "textures/entity/scythe_model.png");
    protected ScytheModel model;

    public ScytheRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new ScytheModel(context.getPart(ScytheModel.SCYTHE_LAYER));
    }

    public Identifier getTexture(ScytheRenderState state) {
        return TEXTURE;
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
        scytheRenderState.isGrounded = scytheEntity.isOnGround();
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

        // TODO add spin while flying + correction when ladded, pass entity infro through render state (need custom render state) video time 17:50

        matrixStack.pop();
        super.render(scytheRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
    }
}
