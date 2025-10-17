package zfrhv.hollowvoid.mixin;

import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EnchantableComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.Settings.class)
public class ItemSettingsMixin {
    @Inject(method = "enchantable", at = @At("HEAD"), cancellable = true)
    private void onEnchantable(int enchantability, CallbackInfoReturnable cir) {
        Item.Settings self = (Item.Settings)(Object)this;

        if (enchantability == 0) {
            cir.setReturnValue(self);
        } else {
            cir.setReturnValue(self.component(DataComponentTypes.ENCHANTABLE, new EnchantableComponent(enchantability)));
        }
    }
}
