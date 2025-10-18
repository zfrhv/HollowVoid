package zfrhv.hollowvoid.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class StepHighEffect extends StatusEffect {
    private double originalStepHeight = 0.6;

    protected StepHighEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            originalStepHeight = player.getAttributeInstance(EntityAttributes.STEP_HEIGHT).getBaseValue();
            player.getAttributeInstance(EntityAttributes.STEP_HEIGHT).setBaseValue(originalStepHeight + (amplifier+1) * 0.5);
        }
        super.onApplied(entity, amplifier);
    }

    @Override
    public void onRemoved(AttributeContainer attributeContainer) {
        attributeContainer.getCustomInstance(EntityAttributes.STEP_HEIGHT).setBaseValue(originalStepHeight);
        super.onRemoved(attributeContainer);
    }
}
