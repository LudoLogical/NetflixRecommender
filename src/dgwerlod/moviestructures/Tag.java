package dgwerlod.moviestructures;

public class Tag implements Comparable<Tag> {

    private String tag;
    private int userID;
    private User user;
    private int movieID;
    private Movie movie;
    private int timestamp;

    // Creates a dummy Tag for comparison
    public Tag(String tag) {
        this.tag = tag;
    }

    public Tag(String tag, int userID, int movieID, int timestamp) {
        this.tag = tag;
        this.userID = userID;
        this.movieID = movieID;
        this.timestamp = timestamp;
    }

    public String getTag() {
        return tag;
    }

    public int getUserID() {
        return userID;
    }

    public int getMovieID() {
        return movieID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String toString() {
        return "User #" + userID + " gave movie #" + movieID +
                " the tag \'" + tag + "\' at timestamp " + timestamp;
    }

    public int compareTo(Tag o) {
        return tag.compareTo(o.tag);
    }

}
