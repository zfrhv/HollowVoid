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
                System.out.println("client2: "+this.getEntityWorld().isClient());
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
        for (int i = 0; i < options.size(); i++) {
            System.out.println("client: "+this.getEntityWorld().isClient());
            System.out.println(options.get(i).status);
            view.putInt("speech_option_"+i+"_status", options.get(i).status.ordinal());
        }
    }

    @Override
    protected void readCustomData(ReadView view) {
        System.out.println("READINGGGGGGGGGGGGGGGGGGGGGGG");
        super.readCustomData(view);
        int speechCount = view.getInt("SpeechCount", 0);
        for (int i = 0; i < speechCount; i++) {
            options.get(i).status = EntitySpeechOption.Status.values()[view.getInt("speech_option_"+i+"_status", 0)];
            System.out.println(options.get(i).status);
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

    public EntitySpeechOption createBasicOption(String question, String answer) {
        return createBasicOption(question, answer, true);
    }

    public EntitySpeechOption createBasicOption(String question, String answer, Boolean unlocked) {
        return new EntitySpeechOption(question, answer, unlocked ? EntitySpeechOption.Status.UNLOCKED : EntitySpeechOption.Status.LOCKED) {
            @Override
            public void onChosen() {
                super.onChosen();
                answerQuestion(answer);
            }
        };
    }
}
