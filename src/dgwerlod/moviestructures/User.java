package dgwerlod.moviestructures;

import java.util.ArrayList;

public class User {

    private int id;
    private ArrayList<Rating> ratings = new ArrayList<>();
    private ArrayList<Tag> tags = new ArrayList<>();

    public User(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public void addRating(Rating r) {
        ratings.add(r);
    }

    public double getRating(int movieID) {
        for (Rating r : ratings) {
            if (movieID == r.getMovieID()) {
                return r.getRating();
            }
        }
        return -1; // No such movieID
    }

    public void addTag(Tag t) {
        tags.add(t);
    }

    public String toString() {

        StringBuilder output = new StringBuilder("User #" + id + " has performed the following ratings: \n");
        for (Rating r : ratings) {
            output.append(r.toString()).append('\n');
        }
        output.append("-- They have also issued the following tags: \n");
        for (Tag t : tags) {
            output.append(t.toString()).append('\n');
        }

        return output.toString();
    }

}
