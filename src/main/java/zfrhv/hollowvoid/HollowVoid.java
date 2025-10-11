package zfrhv.hollowvoid;

import net.fabricmc.api.ModInitializer;

import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zfrhv.hollowvoid.block.ModBlocks;
import zfrhv.hollowvoid.item.ModItems;

public class HollowVoid implements ModInitializer {
	public static final String MOD_ID = "hollowvoid";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItemGroups.registerItemGroups();

        ModItems.initialize();
        ModBlocks.initialize();

        CustomPortalBuilder.beginPortal()
                .frameBlock(Blocks.DIRT)
                .lightWithItem(Items.FEATHER)
                .destDimID(Identifier.of(HollowVoid.MOD_ID, "voiddim"))
                .tintColor(0xc76efa)
                .registerPortal();
	}
}