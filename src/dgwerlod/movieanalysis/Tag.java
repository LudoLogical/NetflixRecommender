package dgwerlod.movieanalysis;

import java.util.ArrayList;

public class Tag {

    private String tag;
    private ArrayList<Integer> userIDs; // all users who have applied this tag
    private ArrayList<Integer> movieIDs;
    private int timestamp;

    public Tag(String tag, ArrayList<Integer> userIDs, ArrayList<Integer> movieIDs, int timestamp) {
        this.tag = tag;
        this.userIDs = userIDs;
        this.movieIDs = movieIDs;
        this.timestamp = timestamp;
    }

    public void addUserID(int id) {
        userIDs.add(id);
    }

    public void addMovieID(int id) {
        movieIDs.add(id);
    }

    public String getTag() {
        return tag;
    }

}
