package zfrhv.hollowvoid.entity.void_fox;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import zfrhv.hollowvoid.entity.DialogueEntity;
import zfrhv.hollowvoid.entity.ModEntities;

public class VoidFoxEntity extends DialogueEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public VoidFoxEntity(EntityType<? extends VoidFoxEntity> entityType, World world) {
        super(entityType, world);
        super.name = Text.literal("[Void Fox]").formatted(Formatting.BLUE);

        // IMPORTANT: the dialogues are managed by their indexes, so if one wants to remove some dialogue, put it
        // QuestionStatus.LOCKED and never trigger it (may leave question + answer empty).
        // Because removing it from array will shift their statuses and break already stored data.
        // When fully removing one of them the dialogue may crash, thus only on big version updates when people will have to reset world.

        super.addQuestionStatus(QuestionStatus.UNLOCKED);
        super.questions.add("Hello?");
        super.answers.add("Welcome {player}, to my humble hut!");

        super.addQuestionStatus(QuestionStatus.LOCKED);
        super.questions.add("How did you know my name?");
        super.answers.add("Its written above your head.");

        super.addQuestionStatus(QuestionStatus.LOCKED);
        super.questions.add("How did you get wood in this dimension?");
        super.answers.add("My little friends brought it, stick by stick... The only thing that keeps us warm in here.");

        super.addQuestionStatus(QuestionStatus.LOCKED);
        super.questions.add("How are you alive?");
        super.answers.add("I can respawn just like you.. Why did you killed me? it wasn't nice.");



//        super.addQuestionStatus(QuestionStatus.UNLOCKED);
//        super.questions.add("What is this place?");
//        super.answers.add("The void dimension.. You can take my scythe in the attic, i dont need it anyways");
    }

    @Override
    public void choseQuestion(PlayerEntity player, int index) {
        super.choseQuestion(player, index);
        switch (index){
            case 0:
                this.setQuestionStatus(1, QuestionStatus.UNLOCKED);
                this.setQuestionStatus(2, QuestionStatus.UNLOCKED);
                break;
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
        this.goalSelector.add(2, new LookAroundGoal(this));

        // TODO
        // maybe sleep goal priority 0, and smithing goal?
        // for monsters i can steal zombie logic, they have attack goal
        // can also add attributes like max health

    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);

        if (!this.getEntityWorld().isClient()) {
            if (!damageSource.getName().equals("genericKill")) { // if not killed by command respawn
                ServerWorld serverWorld = (ServerWorld) this.getEntityWorld();
                // TODO:
                //  delay death respawn by 5 sec
                //  make him respawn near bed
                //  make cool animation and sounds
                VoidFoxEntity mob = ModEntities.VOID_FOX.create(serverWorld, SpawnReason.REINFORCEMENT);
                if (mob != null) {
                    mob.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                    mob.setQuestionStatuses(this.getQuestionStatuses());
                    if (mob.getQuestionStatus(3) == QuestionStatus.LOCKED) {
                        mob.setQuestionStatus(3, QuestionStatus.UNLOCKED);
                    }
                    serverWorld.spawnEntity(mob);
                }
            }
        }
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

    // for interaction like giving quest items or ore to upgrade:
//    private ActionResult interactWithItem(PlayerEntity player, Hand hand) {
}
