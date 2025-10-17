package zfrhv.hollowvoid.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import zfrhv.hollowvoid.HollowVoid;
import zfrhv.hollowvoid.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
//    public static final TagKey<Item> SWEEP_ATTACK = TagKey.of(RegistryKeys.ITEM, Identifier.of(HollowVoid.MOD_ID, "sweep_attack_tag"));

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        valueLookupBuilder(ItemTags.SWORDS).add(ModItems.VOID_SCYTHE_1);
        valueLookupBuilder(ItemTags.SWORDS).add(ModItems.VOID_SCYTHE_2);
        valueLookupBuilder(ItemTags.SWORDS).add(ModItems.VOID_SCYTHE_3);
    }
}
