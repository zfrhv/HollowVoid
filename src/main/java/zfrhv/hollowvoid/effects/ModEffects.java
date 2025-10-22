package zfrhv.hollowvoid.effects;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import zfrhv.hollowvoid.HollowVoid;

public class ModEffects implements ModInitializer {
    public static final RegistryEntry<StatusEffect> REDUCE_HEARTS =
            Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(HollowVoid.MOD_ID, "reduce_hearts"), new ReduceHeartsEffect());
    public static final RegistryEntry<StatusEffect> INCREASE_HEARTS =
            Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(HollowVoid.MOD_ID, "increase_hearts"), new IncreaseHeartsEffect());

    public static final RegistryEntry<StatusEffect> SHADE_CLOAK =
            Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(HollowVoid.MOD_ID, "shade_cloak"), new ShadeCloakEffect());

    @Override
    public void onInitialize() {

    }

    public static void register() {}
}
