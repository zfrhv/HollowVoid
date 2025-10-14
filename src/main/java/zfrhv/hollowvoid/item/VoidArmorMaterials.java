package zfrhv.hollowvoid.item;

import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import zfrhv.hollowvoid.HollowVoid;

import java.util.Map;

public class VoidArmorMaterials {
    // if will add repairing items for void armor then move this variable there
    public static final TagKey<Item> REPAIRS_VOID_ARMOR_TAG = TagKey.of(Registries.ITEM.getKey(), Identifier.of(HollowVoid.MOD_ID, "repairs_guidite_armor"));
    public static final RegistryKey<EquipmentAsset> VOIDARMOR_VISUALS = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(HollowVoid.MOD_ID, "void"));

    public static final ArmorMaterial SHADE_CLOAK_MATERIAL = new ArmorMaterial(
            0, // durability
            Map.of(
                    EquipmentType.HELMET, 0,
                    EquipmentType.CHESTPLATE, 0,
                    EquipmentType.LEGGINGS, 0,
                    EquipmentType.BOOTS, 0
            ),
            1, // enchantability
            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, // equip sound
            0.0F, // toughness
            0.0F, // knockback resistance
            REPAIRS_VOID_ARMOR_TAG, // repair ingredient (none?)
            VOIDARMOR_VISUALS // visual model
    );
}