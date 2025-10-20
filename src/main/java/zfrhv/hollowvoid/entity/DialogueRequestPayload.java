package zfrhv.hollowvoid.entity;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record DialogueRequestPayload(int mobId, int question_index) implements CustomPayload {
    public static final Identifier DIALOGUE_REQUEST = Identifier.of("hollowvoid", "dialogue_request");
    public static final CustomPayload.Id<DialogueRequestPayload> ID = new CustomPayload.Id<>(DIALOGUE_REQUEST);

     public static final PacketCodec<RegistryByteBuf, DialogueRequestPayload> CODEC = PacketCodec.tuple(
             PacketCodecs.INTEGER, DialogueRequestPayload::mobId,
             PacketCodecs.INTEGER, DialogueRequestPayload::question_index,
             DialogueRequestPayload::new
     );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
