package zfrhv.hollowvoid.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import zfrhv.hollowvoid.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        valueLookupBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.VOID_SOIL);
        valueLookupBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.VOID_DEEPSLATE);
        valueLookupBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.SKULLS_BLOCK);
    }
}
