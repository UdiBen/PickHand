package Domain;

public class RoundAction {
    private long playerId;
    private ActionType actionType;
    private int betAmount;

    public RoundAction(long playerId, ActionType actionType, int betAmount) {
        this.playerId = playerId;
        this.actionType = actionType;
        this.betAmount = betAmount;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }
}
