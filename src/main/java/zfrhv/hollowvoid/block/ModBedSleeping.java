package zfrhv.hollowvoid.block;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import zfrhv.hollowvoid.world.ModDimension;

public class ModBedSleeping {
    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!world.isClient() && world.getRegistryKey() == ModDimension.VOIDDIM && world.getBlockState(hitResult.getBlockPos()).isIn(BlockTags.BEDS)) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 5));
            }
            return ActionResult.PASS;
        });
    }
}
