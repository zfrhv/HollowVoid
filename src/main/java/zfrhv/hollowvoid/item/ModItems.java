package zfrhv.hollowvoid.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.item.equipment.ArmorMaterial;

import java.util.function.Function;

public class ModItems {
    public static final Item VOID_STICK = register("void_stick", Item::new, new Item.Settings());
    public static final Item SHADE_CLOAK = register(
            "shade_cloak",
            Item::new,
            new Item.Settings()
                    .armor(ModArmorMaterials.SHADE_CLOAK_MATERIAL, EquipmentType.CHESTPLATE)
                    .maxDamage(0)
    );

    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("hollowvoid", path));
        return Items.register(registryKey, factory, settings);
    }

    public static void initialize() {}
}
