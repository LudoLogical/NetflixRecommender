package dgwerlod.movieanalysis;

import java.util.ArrayList;

public class Movie {

    public static final int NO_YEAR = -1;

    private int id;
    private String title;
    private int year;
    private String[] genres;
    private ArrayList<Rating> ratings = new ArrayList<>();
    private ArrayList<Rating> tags = new ArrayList<>();
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
        for (Rating r : ratings) {
            formattedRatings.append(r.toString()).append('\n');
        }

        return "Movie #" + id + ": " + title +
               "\nReleased in " + formattedYear + " under genres " + formattedGenres.toString() +
               "\nIMDB #" + imdbID + " and TMDB #" + tmdbID +
               "\nThis film has received the following ratings: \n" + formattedRatings;

    }

}
