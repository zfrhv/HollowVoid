package zfrhv.hollowvoid;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zfrhv.hollowvoid.block.ModBedSleeping;
import zfrhv.hollowvoid.block.ModBlocks;
import zfrhv.hollowvoid.effects.ModEffects;
import zfrhv.hollowvoid.entity.ModEntities;
import zfrhv.hollowvoid.entity.client.VoidFoxEntity;
import zfrhv.hollowvoid.item.ModItems;
import zfrhv.hollowvoid.item.ShadeCloak;
import zfrhv.hollowvoid.world.ModDimension;

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

        ShadeCloak.register();
        ModBedSleeping.register();

        ModDimension.initialize();

        ModEffects.register();

        ModEntities.register();
    }
}