package zfrhv.hollowvoid.entity.scythe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import zfrhv.hollowvoid.entity.ModEntities;

@Environment(EnvType.CLIENT)
public class ScytheModel extends EntityModel<ProjectileEntityRenderState> {
    public static final EntityModelLayer SCYTHE_LAYER = new EntityModelLayer(ModEntities.SCYTHE_ID, "main");

    private final ModelPart blade;
    private final ModelPart stick;
    private final ModelPart crystals;
    public ScytheModel(ModelPart root) {
        super(root);
        this.blade = root.getChild("blade");
        this.stick = root.getChild("stick");
        this.crystals = root.getChild("crystals");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData blade = modelPartData.addChild("blade", ModelPartBuilder.create().uv(0, 18).cuboid(0.0F, -3.0F, -10.0F, 1.0F, 2.0F, 12.0F, new Dilation(0.0F))
                .uv(12, 0).cuboid(0.0F, -4.0F, -7.0F, 1.0F, 1.0F, 5.0F, new Dilation(0.0F))
                .uv(17, 7).cuboid(0.0F, -2.0F, -12.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(1, 1).cuboid(0.0F, -1.0F, -10.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(1, 1).cuboid(0.0F, -1.0F, -13.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(0.0F, -1.0F, -3.0F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(0.0F, -2.0F, 2.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(-1.0F, 2.0F, 3.0F));

        ModelPartData stick = modelPartData.addChild("stick", ModelPartBuilder.create().uv(28, 11).cuboid(-1.0F, -22.0F, 3.0F, 1.0F, 20.0F, 1.0F, new Dilation(0.0F))
                .uv(8, 3).cuboid(-1.0F, -27.0F, 3.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData crystals = modelPartData.addChild("crystals", ModelPartBuilder.create().uv(24, 5).cuboid(-1.5F, -2.0F, 2.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(19, 0).cuboid(-1.5F, -17.0F, 2.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }
    @Override
    public void setAngles(ProjectileEntityRenderState projectileEntityRenderState) {

    }
}
