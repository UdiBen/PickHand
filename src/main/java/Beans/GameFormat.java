package Beans;

public enum GameFormat {
    CASH, TOURNAMENT;

    public static GameFormat fromString(String str) {
        if (str.toLowerCase().equals("tournament"))
            return TOURNAMENT;
        else
            return CASH;
    }
}
