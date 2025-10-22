package zfrhv.hollowvoid.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class ShadeCloakEffect extends StatusEffect {
    protected ShadeCloakEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            player.getAttributeInstance(EntityAttributes.STEP_HEIGHT).setBaseValue(1.1);
//            player.getAttributeInstance(EntityAttributes.SCALE).setBaseValue(0.5);
//            player.getAttributeInstance(EntityAttributes.GRAVITY).setBaseValue(0.01);
        }
        super.onApplied(entity, amplifier);
    }

    @Override
    public void onRemoved(AttributeContainer attributeContainer) {
        attributeContainer.getCustomInstance(EntityAttributes.STEP_HEIGHT).setBaseValue(0.6);
//        attributeContainer.getCustomInstance(EntityAttributes.SCALE).setBaseValue(1);
//        attributeContainer.getCustomInstance(EntityAttributes.GRAVITY).setBaseValue(0.08);
        super.onRemoved(attributeContainer);
    }
}
