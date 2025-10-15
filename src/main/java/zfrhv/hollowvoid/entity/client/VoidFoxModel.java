package zfrhv.hollowvoid.entity.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.math.MathHelper;
import zfrhv.hollowvoid.entity.ModEntities;

@Environment(EnvType.CLIENT)
public class VoidFoxModel extends EntityModel<VoidFoxEntityRenderState> {
    public static final EntityModelLayer VOID_FOX_LAYER = new EntityModelLayer(ModEntities.VOID_FOX_ID, "main");

    private final ModelPart head;
    private final ModelPart right_ear;
    private final ModelPart left_ear;
    private final ModelPart nose;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart left_leg;
    private final ModelPart right_leg;
    private final Animation idlingAnimation;

    public VoidFoxModel(ModelPart modelPart) {
        super(modelPart);
        this.head = root.getChild("head");
        this.right_ear = this.head.getChild("right_ear");
        this.left_ear = this.head.getChild("left_ear");
        this.nose = this.head.getChild("nose");
        this.body = root.getChild("body");
        this.tail = this.body.getChild("tail");
        this.left_arm = root.getChild("left_arm");
        this.right_arm = root.getChild("right_arm");
        this.left_leg = root.getChild("left_leg");
        this.right_leg = root.getChild("right_leg");
        this.idlingAnimation = VoidFoxAnimations.IDLE.createAnimation(modelPart);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
                .uv(32, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.25F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

        ModelPartData right_ear = head.addChild("right_ear", ModelPartBuilder.create().uv(56, 0).cuboid(-4.0F, -35.0F, 1.0F, 3.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(58, 5).cuboid(-4.0F, -36.0F, 1.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData left_ear = head.addChild("left_ear", ModelPartBuilder.create().uv(64, 0).mirrored().cuboid(1.0F, -35.0F, 1.0F, 3.0F, 4.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(64, 5).mirrored().cuboid(2.0F, -36.0F, 1.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData nose = head.addChild("nose", ModelPartBuilder.create().uv(72, 0).cuboid(-2.0F, -27.0F, -7.0F, 4.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F))
                .uv(16, 32).cuboid(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.25F)), ModelTransform.origin(0.0F, 6.0F, 0.0F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 3.0F, 2.0F));

        ModelPartData cube_r1 = tail.addChild("cube_r1", ModelPartBuilder.create().uv(86, 0).cuboid(-2.0F, -1.0F, 0.0F, 4.0F, 4.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.9599F, 0.0F, 0.0F));

        ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(32, 48).cuboid(0.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F))
                .uv(46, 48).cuboid(0.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.25F)), ModelTransform.origin(4.0F, 2.0F, 0.0F));

        ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(40, 16).cuboid(-3.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F))
                .uv(40, 32).cuboid(-3.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.25F)), ModelTransform.origin(-4.0F, 2.0F, 0.0F));

        ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(16, 48).cuboid(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 48).cuboid(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.25F)), ModelTransform.origin(2.0F, 12.0F, 0.0F));

        ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(0, 16).cuboid(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 32).cuboid(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.25F)), ModelTransform.origin(-2.0F, 12.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 64);
    }

    @Override
    public void setAngles(VoidFoxEntityRenderState voidFoxEntityRenderState) {
        super.setAngles(voidFoxEntityRenderState); // reset transforms

        this.head.yaw = voidFoxEntityRenderState.relativeHeadYaw * (float) (Math.PI / 180.0);
        this.head.pitch = voidFoxEntityRenderState.pitch * (float) (Math.PI / 180.0);

        this.idlingAnimation.apply(voidFoxEntityRenderState.idleAnimationState, voidFoxEntityRenderState.age);

//        float t = voidFoxEntityRenderState.age / voidFoxEntityRenderState.ageScale;
//        this.tail.yaw = MathHelper.cos(t/4) * 0.3f;
    }
}
