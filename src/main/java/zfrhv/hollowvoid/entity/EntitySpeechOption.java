package zfrhv.hollowvoid.entity;

public class EntitySpeechOption {
    public enum Status {
        LOCKED,
        UNLOCKED,
        COMPLETE
    }

    public String question = "";
    public String answer = "";
    public Status status = Status.LOCKED;
    public int index;

    public EntitySpeechOption (String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public EntitySpeechOption (String question, String answer, Status status) {
        this.question = question;
        this.answer = answer;
        this.status = status;
    }

    public void onChosen() {
        this.status = Status.COMPLETE;
    }
}
