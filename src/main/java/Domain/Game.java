package Domain;

import java.util.List;

public class Game {
    private long id;
    private List<Hand> hands;
    private String videoLink;
    private String title;
    private GameFormat format;
    private boolean isLive;

    public Game(long id, List<Hand> hands, String videoLink, String title, GameFormat format, boolean live) {
        this.id = id;
        this.hands = hands;
        this.videoLink = videoLink;
        this.title = title;
        this.format = format;
        isLive = live;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Hand> getHands() {
        return hands;
    }

    public void setHands(List<Hand> hands) {
        this.hands = hands;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GameFormat getFormat() {
        return format;
    }

    public void setFormat(GameFormat format) {
        this.format = format;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
