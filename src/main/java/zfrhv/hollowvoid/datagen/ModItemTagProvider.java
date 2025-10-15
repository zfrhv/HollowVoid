package zfrhv.hollowvoid.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import zfrhv.hollowvoid.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
//    public static final TagKey<Item> SMELLY_ITEMS = TagKey.of(RegistryKeys.ITEM, Identifier.of(HollowVoid.MOD_ID, "smelly_items"));

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
//        valueLookupBuilder(SMELLY_ITEMS).add(ModItems.VOID_STICK);
        valueLookupBuilder(ItemTags.SWORDS).add(ModItems.VOID_SCYTHE_1);
        valueLookupBuilder(ItemTags.SWORDS).add(ModItems.VOID_SCYTHE_2);
        valueLookupBuilder(ItemTags.SWORDS).add(ModItems.VOID_SCYTHE_3);
    }
}
