package zfrhv.hollowvoid.item;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
    public static final ToolMaterial VOID_TOOL_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            100,
            5.0F,
            1.5F,
            0,
            VoidArmorMaterials.REPAIRS_VOID_ARMOR_TAG
    );

    public static final Item VOID_STICK = register("void_stick", Item::new, new Item.Settings());
    public static final Item SHADE_CLOAK = register(
            "shade_cloak",
            Item::new,
            new Item.Settings()
                    .armor(VoidArmorMaterials.SHADE_CLOAK_MATERIAL, EquipmentType.CHESTPLATE)
                    .maxDamage(1)
    );
    public static final Item VOID_SCYTHE_1 = register(
            "void_scythe_1",
            ScytheItem::new,
            new Item.Settings().sword(VOID_TOOL_MATERIAL, 6f, -3f)
    );
    public static final Item VOID_SCYTHE_2 = register(
            "void_scythe_2",
            ScytheItem::new,
            new Item.Settings().sword(VOID_TOOL_MATERIAL, 8f, -2.8f)
    );
    public static final Item VOID_SCYTHE_3 = register(
            "void_scythe_3",
            ScytheItem::new,
            new Item.Settings().sword(VOID_TOOL_MATERIAL, 10f, -2.6f)
    );





    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("hollowvoid", path));
        return Items.register(registryKey, factory, settings);
    }

    public static void initialize() {}
}
