package dgwerlod.movieanalysis;

import java.util.ArrayList;

public class User {

    private int userID;
    private ArrayList<Rating> ratings = new ArrayList<>();
    private ArrayList<Tag> tags = new ArrayList<>();

    public User(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public void addRating(Rating r) {
        ratings.add(r);
    }

    public void addTag(Tag t) {
        tags.add(t);
    }

    public String toString() {

        StringBuilder output = new StringBuilder("User #" + userID + " has performed the following ratings: \n");
        for (Rating r : ratings) {
            output.append(r.toString()).append('\n');
        }
        output.append("-- They have also issued the following tags: [WILL IMPLEMENT]");

        return output.toString();
    }

}
