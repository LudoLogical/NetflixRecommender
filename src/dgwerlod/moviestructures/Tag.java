package dgwerlod.moviestructures;

public class Tag {

    private String tag;
    private int userID;
    private User user;
    private int movieID;
    private Movie movie;
    private int timestamp;

    public Tag(String tag, int userID, int movieID, int timestamp) {
        this.tag = tag;
        this.userID = userID;
        this.movieID = movieID;
        this.timestamp = timestamp;
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

}
