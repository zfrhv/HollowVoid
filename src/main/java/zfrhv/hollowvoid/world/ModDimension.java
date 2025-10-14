package zfrhv.hollowvoid.world;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import zfrhv.hollowvoid.effects.ModEffects;

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

                    player.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(10);
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.DARKNESS, 20*7, 1, false, true, false
                    ));
                    player.addStatusEffect(new StatusEffectInstance(
                            ModEffects.REDUCE_HEARTS, 20*5, 0, false, true, false
                    ));
                    // TODO add decaying hearts sound effects
                }
            }
        });
    }
}
