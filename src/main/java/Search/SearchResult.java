package Search;

/**
 * Created with IntelliJ IDEA.
 * User: amit
 * Date: 17/01/14
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public class SearchResult {
    private long HandId;

    public SearchResult(long handId) {
        HandId = handId;
    }

    public long getHandId() {
        return HandId;
    }

    public void setHandId(long handId) {
        HandId = handId;
    }
}
