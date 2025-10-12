package zfrhv.hollowvoid.item;

import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;

import java.util.Map;

public class ModArmorMaterials {
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
            ItemTags.REPAIRS_LEATHER_ARMOR, // repair ingredient
            EquipmentAssetKeys.LEATHER // visual model
    );
}