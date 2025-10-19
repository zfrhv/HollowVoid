package zfrhv.hollowvoid.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
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
    public Text name = Text.literal("[Unnamed]");
    public List<EntitySpeechOption> options = new ArrayList<>();

    protected DialogueEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (this.getEntityWorld().isClient()) {
            List<EntitySpeechOption> unlockedSpeeches = new ArrayList<>();
            for (int i = 0; i < options.size(); i++) {
                EntitySpeechOption option = options.get(i);
                System.out.println(option.status);
                if (option.status == EntitySpeechOption.Status.UNLOCKED) {
                    option.index = i;
                    unlockedSpeeches.add(option);
                }
            }
            MinecraftClient.getInstance().setScreen(new DialogueScreen(this.getId(), name, unlockedSpeeches.toArray(new EntitySpeechOption[0])));


        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.putInt("SpeechCount", options.size());
        System.out.println("size iS: ");
        System.out.println(options.size());
        for (int i = 0; i < options.size(); i++) {
            EntitySpeechOption option = options.get(i);
            System.out.println(option.status);
            view.putString("speech_option_"+i+"_question", option.question);
            view.putString("speech_option_"+i+"_answer", option.answer);
            view.putInt("speech_option_"+i+"_status", option.status.ordinal());
        }
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        int speechCount = view.getInt("SpeechCount", 0);
        for (int i = 0; i < speechCount; i++) {
            options.add(new EntitySpeechOption(
                    view.getString("speech_option_"+i+"_question", ""),
                    view.getString("speech_option_"+i+"_answer", ""),
                    EntitySpeechOption.Status.values()[view.getInt("speech_option_"+i+"_status", 0)]
            ));
        }
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

    public void addBasicOption(String question, String answer) {
        addBasicOption(question, answer, true);
    }

    public void addBasicOption(String question, String answer, Boolean unlocked) {
        if (this.getEntityWorld().isClient()) {
            options.add(new EntitySpeechOption(question, answer, unlocked ? EntitySpeechOption.Status.UNLOCKED : EntitySpeechOption.Status.LOCKED) {
                @Override
                public void onChosen() {
                    System.out.println("marking client complete");
                    super.onChosen();
                }
            });
        } else {
            options.add(new EntitySpeechOption(question, answer, unlocked ? EntitySpeechOption.Status.UNLOCKED : EntitySpeechOption.Status.LOCKED) {
                @Override
                public void onChosen() {
                    System.out.println("marking server complete");
                    super.onChosen();
                    answerQuestion(answer);
                }
            });
        }
    }
}
