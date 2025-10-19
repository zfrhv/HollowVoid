package zfrhv.hollowvoid.entity.void_fox;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import zfrhv.hollowvoid.client.render.DialogueScreen;
import zfrhv.hollowvoid.entity.DialogueEntity;
import zfrhv.hollowvoid.entity.EntitySpeechOption;

public class VoidFoxEntity extends DialogueEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public VoidFoxEntity(EntityType<? extends VoidFoxEntity> entityType, World world) {
        super(entityType, world);

        super.name = Text.literal("[Void Fox]").formatted(Formatting.BLUE);
        addBasicOption("Hello1", "You can take my scythe in the attic, i dont need it anyways");
        addBasicOption("Hello2", "Who allowed you to steal peoples scythes? youre lucky i dont need it... you can keep it.");
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
        this.goalSelector.add(2, new LookAroundGoal(this));

        // TODO
        // maybe sleep goal priority 0, and smithing goal?
        // for monsters i can steal zombie logic, they have attack goal
        // can also add attributes like max health

    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 50.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 1)
                .add(EntityAttributes.ATTACK_DAMAGE, 4.0);
    }

    @Override
    public void tick() {
        super.tick();

        this.idleAnimationState.startIfNotRunning(this.age);
    }

//    private ActionResult interactWithItem(PlayerEntity player, Hand hand) {
}
