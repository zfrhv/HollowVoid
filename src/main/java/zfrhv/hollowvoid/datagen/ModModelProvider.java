package zfrhv.hollowvoid.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import zfrhv.hollowvoid.block.ModBlocks;
import zfrhv.hollowvoid.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerRandomHorizontalRotations(TexturedModel.CUBE_ALL, ModBlocks.VOID_SOIL);
        blockStateModelGenerator.registerRandomHorizontalRotations(TexturedModel.CUBE_ALL, ModBlocks.VOID_DRIPSTONE);
        blockStateModelGenerator.registerRandomHorizontalRotations(TexturedModel.CUBE_ALL, ModBlocks.VOID_DEEPSLATE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SKULLS_BLOCK);
        blockStateModelGenerator.registerRandomHorizontalRotations(TexturedModel.CUBE_ALL, ModBlocks.VOID_GRANIT);
    }


    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.VOID_STICK, Models.GENERATED);
        itemModelGenerator.register(ModItems.SHADE_CLOAK, Models.GENERATED);
        itemModelGenerator.register(ModItems.VOID_SCYTHE_1, Models.HANDHELD);
        itemModelGenerator.register(ModItems.VOID_SCYTHE_2, Models.HANDHELD);
        itemModelGenerator.register(ModItems.VOID_SCYTHE_3, Models.HANDHELD);
    }

    @Override
    public String getName() {
        return "FabricDocsReference Model Provider";
    }
}
