package zfrhv.hollowvoid.world;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.io.Console;

public class ModDimension {
    public static final RegistryKey<World> VOIDDIM = RegistryKey.of(RegistryKeys.WORLD, Identifier.of("hollowvoid", "voiddim"));

    // The actual dimension and biomes are generated manually from website and placed in resources directly

    public static void initialize() {
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {
            if (destination.getRegistryKey().equals(VOIDDIM)) {
                AdvancementEntry entered_void_advancement = player.getEntityWorld().getServer().getAdvancementLoader().get(Identifier.of("hollowvoid", "void/root"));
                AdvancementProgress progress = player.getAdvancementTracker().getProgress(entered_void_advancement);

                // If they have not completed the advancement yet, give the effect
                if (!progress.isDone()) {
                    player.getAdvancementTracker().grantCriterion(entered_void_advancement, "code_check");
                    // TODO add effects to reduce hearts, similar to affects when gaining new hearts
                    player.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(10.0);
                }
            }
        });
    }
}
