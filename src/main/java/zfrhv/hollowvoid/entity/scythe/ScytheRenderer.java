package zfrhv.hollowvoid.entity.scythe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import net.minecraft.client.render.entity.state.TntEntityRenderState;
import net.minecraft.client.render.entity.state.TridentEntityRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.math.RotationAxis;
import zfrhv.hollowvoid.HollowVoid;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ScytheRenderer extends EntityRenderer<ScytheEntity, ProjectileEntityRenderState> {
    private static final Identifier TEXTURE = Identifier.of(HollowVoid.MOD_ID, "textures/entity/scythe_model.png");
    protected ScytheModel model;

    public ScytheRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new ScytheModel(context.getPart(ScytheModel.SCYTHE_LAYER));
    }

    public Identifier getTexture(ProjectileEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public ProjectileEntityRenderState createRenderState() {
        return new ProjectileEntityRenderState();
    }

    public void updateRenderState(ScytheEntity scytheEntity, ProjectileEntityRenderState projectileEntityRenderState, float f) {
        super.updateRenderState(scytheEntity, projectileEntityRenderState, f);
        // TODO add variables to render state
    }

    public void render(
            ProjectileEntityRenderState projectileEntityRenderState,
            MatrixStack matrixStack,
            OrderedRenderCommandQueue orderedRenderCommandQueue,
            CameraRenderState cameraRenderState
    ) {
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(projectileEntityRenderState.yaw - 90.0F));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(projectileEntityRenderState.pitch + 90.0F));
        List<RenderLayer> list = ItemRenderer.getGlintRenderLayers(this.model.getLayer(TEXTURE), false, true);

        for (int i = 0; i < list.size(); i++) {
            orderedRenderCommandQueue.getBatchingQueue(i)
                    .submitModel(
                            this.model,
                            projectileEntityRenderState,
                            matrixStack,
                            (RenderLayer)list.get(i),
                            projectileEntityRenderState.light,
                            OverlayTexture.DEFAULT_UV,
                            -1,
                            null,
                            projectileEntityRenderState.outlineColor,
                            null
                    );
        }

        // TODO add spin while flying + correction when ladded, pass entity infro through render state (need custom render state) video time 17:50

        matrixStack.pop();
        super.render(projectileEntityRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
    }
}
