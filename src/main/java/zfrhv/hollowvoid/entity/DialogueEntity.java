package zfrhv.hollowvoid.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import zfrhv.hollowvoid.client.render.DialogueScreen;

import java.util.ArrayList;
import java.util.List;

public class DialogueEntity extends MobEntity  {
    public static enum QuestionStatus {
        LOCKED,
        UNLOCKED,
        IMPORTANT_MISSION,
        COMPLETE
    }
    public Text name = Text.literal("[Unnamed]");
    private static final TrackedData<String> QUESTIONS_STATUSES = DataTracker.registerData(DialogueEntity.class, TrackedDataHandlerRegistry.STRING);
    public List<String> questions;
    public List<String> answers;

    protected DialogueEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        this.dataTracker.set(QUESTIONS_STATUSES, "");
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(QUESTIONS_STATUSES, "");
    }

    public QuestionStatus getQuestionStatus(int index) {
        String statuses = this.dataTracker.get(QUESTIONS_STATUSES);
        return QuestionStatus.values()[statuses.charAt(index)];
    }

    public void setQuestionStatus(int index, QuestionStatus questionStatus) {
        StringBuilder sb = new StringBuilder(this.dataTracker.get(QUESTIONS_STATUSES));
        sb.setCharAt(index, (char)questionStatus.ordinal());
        this.dataTracker.set(QUESTIONS_STATUSES, sb.toString());
    }

    public String getQuestionStatuses() {
        return this.dataTracker.get(QUESTIONS_STATUSES);
    }

    public void setQuestionStatuses(String questionStatuses) {
        this.dataTracker.set(QUESTIONS_STATUSES, questionStatuses);
    }

    public void addQuestionStatus(QuestionStatus questionStatus) {
        this.dataTracker.set(QUESTIONS_STATUSES, this.dataTracker.get(QUESTIONS_STATUSES) + (char) questionStatus.ordinal());
    }

    public void choseQuestion(int index) {
        if (getQuestionStatus(index) == QuestionStatus.UNLOCKED) {
            setQuestionStatus(index, QuestionStatus.COMPLETE);
        }
        answerQuestion(answers.get(index));
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (this.getEntityWorld().isClient()) {
            List<String> askableQuestions = new ArrayList<>();
            List<Integer> questionsIndexes = new ArrayList<>();
            for (int i = 0; i < questions.size(); i++) {
                QuestionStatus questionStatus = getQuestionStatus(i);
                if (questionStatus == QuestionStatus.UNLOCKED || questionStatus == QuestionStatus.IMPORTANT_MISSION) {
                    askableQuestions.add(questions.get(i));
                    questionsIndexes.add(i);
                }
            }
            MinecraftClient.getInstance().setScreen(new DialogueScreen(this.getId(), name, askableQuestions, questionsIndexes));


        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.putString("questions_statuses", this.dataTracker.get(QUESTIONS_STATUSES));
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        String current_statuses = this.dataTracker.get(QUESTIONS_STATUSES);
        String loaded_statuses = view.getString("questions_statuses", current_statuses);
        if (current_statuses.length() > loaded_statuses.length()) { // if added new dialogues add them to statuses "array"
            loaded_statuses = loaded_statuses + current_statuses.substring(loaded_statuses.length());
        }
        this.dataTracker.set(QUESTIONS_STATUSES, loaded_statuses);
    }

    public void sendNpcMessage(PlayerEntity player, String message) {
        MutableText text = Text.literal("[Void Fox]").formatted(Formatting.BLUE).append(Text.literal(": "+message).formatted(Formatting.WHITE));
        player.sendMessage(text, false);
    }

    public void answerQuestion(String answer) {
        World world = this.getEntityWorld();
        Vec3d pos = this.getEntityPos();

        for (PlayerEntity player : world.getPlayers()) {
            if (player.squaredDistanceTo(pos) <= 10 * 10) { // radius 10
                sendNpcMessage(player, answer);
            }
        }
    }
}
