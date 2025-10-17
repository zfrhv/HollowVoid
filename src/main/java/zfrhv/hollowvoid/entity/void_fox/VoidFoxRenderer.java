package zfrhv.hollowvoid.entity.void_fox;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import zfrhv.hollowvoid.HollowVoid;

@Environment(EnvType.CLIENT)
public class VoidFoxRenderer extends MobEntityRenderer<VoidFoxEntity, VoidFoxEntityRenderState, VoidFoxModel> {
    private static final Identifier TEXTURE = Identifier.of(HollowVoid.MOD_ID, "textures/entity/void_fox.png");

    public VoidFoxRenderer(EntityRendererFactory.Context context) {
        super(context, new VoidFoxModel(context.getPart(VoidFoxModel.VOID_FOX_LAYER)), 0.75f);
    }

    @Override
    public Identifier getTexture(VoidFoxEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public VoidFoxEntityRenderState createRenderState() {
        return new VoidFoxEntityRenderState();
    }

    public void updateRenderState(VoidFoxEntity voidFoxEntity, VoidFoxEntityRenderState voidFoxEntityRenderState, float f) {
        super.updateRenderState(voidFoxEntity, voidFoxEntityRenderState, f);
        voidFoxEntityRenderState.idleAnimationState.copyFrom(voidFoxEntity.idleAnimationState);
    }
}
