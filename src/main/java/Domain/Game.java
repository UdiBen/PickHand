package Domain;

public class Game {
    private long id;
    private String videoLink;
    private String title;
    private GameFormat format;
    private boolean isLive;

    public Game(long id, String videoLink, String title, GameFormat format, boolean isLive) {
        this.id = id;
        this.videoLink = videoLink;
        this.title = title;
        this.format = format;
        this.isLive = isLive;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
