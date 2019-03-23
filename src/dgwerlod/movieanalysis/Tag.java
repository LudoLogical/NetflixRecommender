package dgwerlod.movieanalysis;

public class Tag {

    private String tag;
    private int[] userIDs;
    private int[] movieIDs;
    private int timestamp;

    public Tag(String tag, int[] userIDs, int[] movieIDs, int timestamp) {
        this.tag = tag;
        this.userIDs = userIDs;
        this.movieIDs = movieIDs;
        this.timestamp = timestamp;
    }

    public String getTag() {
        return tag;
    }

    public int[] getUserIDs() {
        return userIDs;
    }

    public int[] getMovieIDs() {
        return movieIDs;
    }

    public int getTimestamp() {
        return timestamp;
    }

}
