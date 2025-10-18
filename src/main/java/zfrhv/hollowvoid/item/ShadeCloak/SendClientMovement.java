package zfrhv.hollowvoid.item.ShadeCloak;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import zfrhv.hollowvoid.item.ModItems;

import java.util.Map;
import java.util.WeakHashMap;

@Environment(EnvType.CLIENT)
public class SendClientMovement {
    public static final Map<ItemStack, Boolean> stillDashingMap = new WeakHashMap<>();

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            ItemStack cloak = client.player.getEquippedStack(EquipmentSlot.CHEST);
            if (cloak.getItem() == ModItems.SHADE_CLOAK) {
                // reset dash if not pressing sprint button anymore
                if (!client.player.input.playerInput.sprint() && stillDashingMap.getOrDefault(cloak, false)) {
                    stillDashingMap.put(cloak, false);
                }

                Vec2f movement = client.player.input.getMovementInput();
                if ((movement.x != 0 || movement.y != 0) && client.player.input.playerInput.sprint() && !stillDashingMap.getOrDefault(cloak, false)) {
                    if (client.player.getHungerManager().getSaturationLevel() >= 1) {
                        if (!client.player.getItemCooldownManager().isCoolingDown(cloak)) {
                            Vec3d movement3d = new Vec3d(movement.x, 0.2, movement.y);
                            float yaw = client.player.getYaw();
                            movement3d = movement3d.rotateY(- yaw * (float) (Math.PI / 180.0));
                            ClientPlayNetworking.send(new DashRequestPayload(movement3d));
                            stillDashingMap.put(cloak, true);
                        }
                    }
                }
            }
        });
    }
}
