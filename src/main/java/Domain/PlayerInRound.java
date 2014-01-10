package Domain;

import java.util.List;

public class PlayerInRound {
    private long id;
    private int position;
    private StackType stack;
    private List<String> cards;

    public PlayerInRound(long id, int position, Integer stack, List<String> cards) {
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

    public Integer getStack() {
        return stack;
    }

    public void setStack(Integer stack) {
        this.stack = stack;
    }

    public List<String> getCards() {
        return cards;
    }

    public void setCards(List<String> cards) {
        this.cards = cards;
    }
}
