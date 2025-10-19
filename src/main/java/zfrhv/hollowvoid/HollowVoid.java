package zfrhv.hollowvoid;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zfrhv.hollowvoid.block.ModBedSleeping;
import zfrhv.hollowvoid.block.ModBlocks;
import zfrhv.hollowvoid.effects.ModEffects;
import zfrhv.hollowvoid.entity.DialogueEntity;
import zfrhv.hollowvoid.entity.DialogueRequestPayload;
import zfrhv.hollowvoid.entity.ModEntities;
import zfrhv.hollowvoid.item.ModItems;
import zfrhv.hollowvoid.item.ShadeCloak.DashRequestPayload;
import zfrhv.hollowvoid.item.ShadeCloak.ShadeCloak;
import zfrhv.hollowvoid.sound.ModSounds;
import zfrhv.hollowvoid.world.ModDimension;

public class HollowVoid implements ModInitializer {
	public static final String MOD_ID = "hollowvoid";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItemGroups.registerItemGroups();

        ModItems.initialize();
        ModBlocks.initialize();
        ModSounds.register();

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

        PayloadTypeRegistry.playC2S().register(DialogueRequestPayload.ID, DialogueRequestPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(DialogueRequestPayload.ID, (payload, context) -> {
            Entity entity = context.player().getEntityWorld().getEntityById(payload.mobId());
            if (entity instanceof DialogueEntity dialogueEntity) {
                dialogueEntity.options.get(payload.option_index()).onChosen();
                // TODO specifying only server on displaying message doesnt work?
                // and its not saving my choices
            }
        });
    }
}