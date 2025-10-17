package zfrhv.hollowvoid.entity.scythe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.EntityRenderState;

@Environment(EnvType.CLIENT)
public class ScytheRenderState extends EntityRenderState {
    public float pitch;
    public float yaw;
    public float rotation;
    public boolean enchanted;
    public boolean isGrounded;
}
