package zfrhv.hollowvoid.item.ShadeCloak;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import zfrhv.hollowvoid.effects.ModEffects;
import zfrhv.hollowvoid.item.ModItems;
import zfrhv.hollowvoid.sound.ModSounds;

public class ShadeCloak {
    public static final Identifier SPRINT_SPEED_MODIFIER = Identifier.of("hollowvoid", "sprint_speed_modfier");

    public static void register() {
        PayloadTypeRegistry.playC2S().register(DashRequestPayload.ID, DashRequestPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(DashRequestPayload.ID, (payload, context) -> {
            Vec3d movement3d = payload.movement3d();
            if (0.1 < movement3d.length() && movement3d.length() < 2) {
                ServerPlayerEntity player = context.player();
                if (player.getHungerManager().getFoodLevel() >= 1) {
                    ItemStack cloak = player.getEquippedStack(EquipmentSlot.CHEST);
                    if (cloak.getItem() == ModItems.SHADE_CLOAK) {
                        if (!player.getItemCooldownManager().isCoolingDown(cloak)) {

                            movement3d = movement3d.normalize();

                            // TODO works for doors but also for blocks just like enderpearl glitches.
                            // TODO need to fix these blocks glitches and then can use it
                            // TODO apply slowness when player in block? and not just push outside
//                            Vec3d doorDash = movement3d.multiply(0.001);
//                            player.requestTeleportOffset(doorDash.x, doorDash.y, doorDash.z);
                            // TODO i guess my best option here is to check whethere where i tp is free space or not?
                            // TODO then then also i need to check if super close is not collidable, cuz i dont wanna tp when just running

                            player.addVelocity(movement3d);
                            player.velocityModified = true;

                            player.getItemCooldownManager().set(cloak, 15);
                            if (player.interactionManager.getGameMode() == GameMode.SURVIVAL) {
                                player.getHungerManager().addExhaustion(4);
                            }

                            player.addStatusEffect(new StatusEffectInstance(
                                    ModEffects.SHADE_CLOAK, 6, 0, false, false, false
                            ));

                            ServerWorld world = player.getEntityWorld();
                            float pitch = 1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F;
                            world.playSound(
                                    null,
                                    player.getX(),
                                    player.getY(),
                                    player.getZ(),
                                    ModSounds.DASH,
                                    SoundCategory.PLAYERS,
                                    2F,
                                    pitch
                            );
                            player.playSound(ModSounds.DASH);

                            // TODO make it work with horse as well via 1.21.11 update
                            // TODO add particles and make player invisible
                        }
                    }
                }
            }
        });
    }
}
