package Domain;

import java.util.List;

public class PlayerInHand {
    private long id;
    private int position;
    private StackType stack;
    private List<String> cards;

    public PlayerInHand(long id, int position, StackType stack, List<String> cards) {
        this.id = id;
        this.position = position;
        this.stack = stack;
        this.cards = cards;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public StackType getStack() {
        return stack;
    }

    public void setStack(StackType stack) {
        this.stack = stack;
    }

    public List<String> getCards() {
        return cards;
    }

    public void setCards(List<String> cards) {
        this.cards = cards;
    }
}
