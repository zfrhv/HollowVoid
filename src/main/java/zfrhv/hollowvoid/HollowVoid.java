package zfrhv.hollowvoid;

import net.fabricmc.api.ModInitializer;

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
	}
}