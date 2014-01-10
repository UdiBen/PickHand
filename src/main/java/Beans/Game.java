package Beans;

public class Game {
    public long id;
    public String videoLink;
    public String title;
    public GameFormat format;
    public boolean isLive;

    public Game(long id, String videoLink, String title, GameFormat format, boolean isLive) {
        this.id = id;
        this.videoLink = videoLink;
        this.title = title;
        this.format = format;
        this.isLive = isLive;
    }
}
