package Domain;

import java.util.List;

public class Round {
    private RoundType type;
    private List<String> cards;
    private int pot;
    private List<RoundAction> roundActions;

    public Round(RoundType type, List<String> cards, int pot, List<RoundAction> round) {
        this.type = type;
        this.cards = cards;
        this.pot = pot;
        this.roundActions = round;
    }

    public RoundType getType() {
        return type;
    }

    public void setType(RoundType type) {
        this.type = type;
    }

    public List<String> getCards() {
        return cards;
    }

    public void setCards(List<String> cards) {
        this.cards = cards;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public List<RoundAction> getRoundActions() {
        return roundActions;
    }

    public void setRoundActions(List<RoundAction> roundActions) {
        this.roundActions = roundActions;
    }
}
