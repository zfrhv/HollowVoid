package zfrhv.hollowvoid.item.ShadeCloak;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public record DashRequestPayload(Vec3d movement3d) implements CustomPayload {
    public static final Identifier DASH_REQUEST = Identifier.of("hollowvoid", "dash_request");
    public static final CustomPayload.Id<DashRequestPayload> ID = new CustomPayload.Id<>(DASH_REQUEST);

    public static final PacketCodec<RegistryByteBuf, DashRequestPayload> CODEC = PacketCodec.tuple(
            Vec3d.PACKET_CODEC, DashRequestPayload::movement3d,
            DashRequestPayload::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
