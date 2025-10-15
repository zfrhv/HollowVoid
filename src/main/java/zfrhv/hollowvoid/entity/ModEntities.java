package zfrhv.hollowvoid.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import zfrhv.hollowvoid.HollowVoid;
import zfrhv.hollowvoid.entity.client.VoidFoxEntity;

public class ModEntities {
    public static final Identifier VOID_FOX_ID = Identifier.of(HollowVoid.MOD_ID, "void_fox");
    public static final EntityType<VoidFoxEntity> VOID_FOX = Registry.register(
            Registries.ENTITY_TYPE,
            VOID_FOX_ID,
            EntityType.Builder.create(VoidFoxEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.5f, 1.99f).build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, VOID_FOX_ID)));


    public static void register() {
        FabricDefaultAttributeRegistry.register(ModEntities.VOID_FOX, VoidFoxEntity.createAttributes());
    }
}
