package zfrhv.hollowvoid;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import zfrhv.hollowvoid.block.ModBlocks;
import zfrhv.hollowvoid.item.ModItems;

public class ModItemGroups {
    public static final ItemGroup VOID_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(HollowVoid.MOD_ID, "void_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.VOID_STICK))
                    .displayName(Text.translatable("itemgroup.hollowvoid.void_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.VOID_STICK);
                        entries.add(ModItems.SHADE_CLOAK);
                        entries.add(ModItems.VOID_SCYTHE_1);
                        entries.add(ModItems.VOID_SCYTHE_2);
                        entries.add(ModItems.VOID_SCYTHE_3);
                    })
                    .build());
    public static final ItemGroup VOID_BLOCKS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(HollowVoid.MOD_ID, "void_blocks"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.VOID_SOIL))
                    .displayName(Text.translatable("itemgroup.hollowvoid.void_blocks"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.VOID_SOIL);
                        entries.add(ModBlocks.VOID_DEEPSLATE);
                    })
                    .build());

    public static void registerItemGroups() {

    }
}
