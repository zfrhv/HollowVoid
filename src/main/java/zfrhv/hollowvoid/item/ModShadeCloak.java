package zfrhv.hollowvoid.item;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModShadeCloak {
    public static final Identifier SPRINT_SPEED_MODIFIER = Identifier.of("hollowvoid", "sprint_speed_modfier");

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (player.isSprinting()) {
                    // Only add modifier if not already applied
                    if (!player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED)
                            .hasModifier(SPRINT_SPEED_MODIFIER)) {
                        player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED)
                                .addTemporaryModifier(new EntityAttributeModifier(
                                        SPRINT_SPEED_MODIFIER,
                                        2.0, // add speed
                                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                ));
                        player.getAttributeInstance(EntityAttributes.STEP_HEIGHT)
                                .setBaseValue(1.1F);

                        player.addStatusEffect(new StatusEffectInstance(
                                StatusEffects.JUMP_BOOST, Integer.MAX_VALUE, 1, false, false, false
                        ));
                        player.getHungerManager().setSaturationLevel(0);
                        player.addStatusEffect(new StatusEffectInstance(
                                StatusEffects.HUNGER, Integer.MAX_VALUE, 50, false, false, false
                        ));
                        player.addStatusEffect(new StatusEffectInstance(
                                StatusEffects.STRENGTH, Integer.MAX_VALUE, 1, false, false, false
                        ));
                    }
                } else if (!player.getPlayerInput().forward() && player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED)
                        .hasModifier(SPRINT_SPEED_MODIFIER)) {
                    // player.isSprinting() breaks after hitting enemy while running. so i also check for player.getPlayerInput().forward()

                    player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED)
                            .removeModifier(SPRINT_SPEED_MODIFIER);
                    player.getAttributeInstance(EntityAttributes.STEP_HEIGHT)
                            .setBaseValue(0.6F);

                    player.removeStatusEffect(StatusEffects.JUMP_BOOST);
                    player.removeStatusEffect(StatusEffects.HUNGER);
                    player.removeStatusEffect(StatusEffects.STRENGTH);
                }
            }
        });

//        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            ClientPlayerEntity player = MinecraftClient.getInstance().player;
//            if (player == null) return;
//
//            if (player.isSprinting() || player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED)
//                        .hasModifier(SPRINT_SPEED_MODIFIER)) {
//
//            } else {
//
//            }
//        });
    }
}
