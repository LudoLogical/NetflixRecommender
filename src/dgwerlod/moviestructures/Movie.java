package dgwerlod.moviestructures;

import java.util.ArrayList;

public class Movie implements Comparable<Movie> {

    public static final int NO_YEAR = -1;

    private int id;
    private String title;
    private int year;
    private String[] genres;
    private ArrayList<Rating> ratings = new ArrayList<>();
    private ArrayList<Tag> tags = new ArrayList<>();
    private String imdbID;
    private String tmdbID;

    // Creates a dummy Movie for comparison
    public Movie(int id) {
        this.id = id;
    }

    public Movie(int id, String title, int year, String[] genres) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genres = genres;
    }

    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void addLinks(String imdb, String tmdb) {
        imdbID = imdb;
        tmdbID = tmdb;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void addRating(Rating r) {
        ratings.add(r);
    }

    public double getAverageRating() {
        double total = 0;
        for (Rating r : ratings) {
            total += r.getRating();
        }
        return total / ratings.size();
    }

    public void addTag(Tag t) {
        tags.add(t);
    }

    public String[] getGenres() {
        return genres;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    @SuppressWarnings("Duplicates")
    public String toString() {

        String formattedYear = "";
        if (year == NO_YEAR) {
            formattedYear = "[No year given]";
        } else {
            formattedYear += year;
        }

        StringBuilder formattedGenres = new StringBuilder();
        if (genres.length == 0) {
            formattedGenres.append("[No genres listed]");
        } else {
            for (int i = 0; i < genres.length; i++) {
                formattedGenres.append(genres[i]);
                if (i != genres.length-1) {
                    formattedGenres.append(", ");
                }
            }
        }

        StringBuilder formattedRatings = new StringBuilder();
        if (ratings.size() == 0) {
            formattedRatings.append("[No ratings yet]");
        } else {
            for (int i = 0; i < ratings.size(); i++) {
                formattedRatings.append(ratings.get(i).toString());
                if (i != ratings.size()-1) {
                    formattedRatings.append('\n');
                }
            }
        }

        StringBuilder formattedTags = new StringBuilder();
        if (tags.size() == 0) {
            formattedTags.append("[No tags yet]");
        } else {
            for (int i = 0; i < tags.size(); i++) {
                formattedTags.append(tags.get(i).toString());
                if (i != tags.size()-1) {
                    formattedTags.append('\n');
                }
            }
        }

        return "Movie #" + id + " is " + title +
               "\nReleased in " + formattedYear + " under genres " + formattedGenres.toString() +
               "\nIMDB #" + imdbID + " and TMDB #" + tmdbID +
               "\nThis film has received the following ratings: \n" + formattedRatings +
               "\nThis film has received the following tags: \n" + formattedTags;

    }

    public int compareTo(Movie o) {
        return id - o.id;
    }

}
