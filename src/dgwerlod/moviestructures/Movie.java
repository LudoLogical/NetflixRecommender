package dgwerlod.moviestructures;

import java.util.ArrayList;

public class Movie {

    public static final int NO_YEAR = -1;

    private int id;
    private String title;
    private int year;
    private String[] genres;
    private ArrayList<Rating> ratings = new ArrayList<>();
    private ArrayList<Tag> tags = new ArrayList<>();
    private String imdbID;
    private String tmdbID;

    public Movie(int id, String title, int year, String[] genres) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genres = genres;
    }

    public int getID() {
        return id;
    }

    public void addLinks(String imdb, String tmdb) {
        imdbID = imdb;
        tmdbID = tmdb;
    }

    public void addRating(Rating r) {
        ratings.add(r);
    }

    public void addTag(Tag t) {
        tags.add(t);
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

}
