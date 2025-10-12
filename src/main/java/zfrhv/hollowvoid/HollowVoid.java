package zfrhv.hollowvoid;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zfrhv.hollowvoid.block.ModBedSleeping;
import zfrhv.hollowvoid.block.ModBlocks;
import zfrhv.hollowvoid.item.ModItems;
import zfrhv.hollowvoid.item.ModShadeCloak;

public class HollowVoid implements ModInitializer {
	public static final String MOD_ID = "hollowvoid";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItemGroups.registerItemGroups();

        ModItems.initialize();
        ModBlocks.initialize();

//        CustomPortalBuilder.beginPortal()
//                .frameBlock(Blocks.DIRT)
//                .lightWithItem(Items.FEATHER)
//                .destDimID(Identifier.of(HollowVoid.MOD_ID, "voiddim"))
//                .tintColor(0xc76efa)
//                .registerPortal();

        ModShadeCloak.register();
        ModBedSleeping.register();
    }
}