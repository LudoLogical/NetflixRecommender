package dgwerlod.moviestructures;

public class Rating {

    private double rating;
    private int userID;
    private int movieID;
    private int timestamp;

    public Rating(double rating, int userID, int movieID, int timestamp) {
        this.rating = rating;
        this.userID = userID;
        this.movieID = movieID;
        this.timestamp = timestamp;
    }

    public double getRating() {
        return rating;
    }

    public int getUserID() {
        return userID;
    }

    public int getMovieID() {
        return movieID;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String toString() {
        return "User #" + userID + " gave movie #" + movieID +
               " a rating of " + rating + " at timestamp " + timestamp;
    }

}
