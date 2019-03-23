package dgwerlod.movieanalysis;

public class Movie {

    public static final int NO_YEAR = -1;

    private int id;
    private String title;
    private int year;
    private String[] genres;
    private Rating[] ratings;
    private Tag[] tags;
    private String imdbID;
    private String tmdbID;

    public Movie(int id, String title, int year, String[] genres) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genres = genres;
    }

    public void addLinks(String imdb, String tmdb) {
        imdbID = imdb;
        tmdbID = tmdb;
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

        return "ID: " + id + "\nTitle: " + title +
               "\nYear: " + formattedYear + "\nGenres: " + formattedGenres.toString() +
               "\nIMDB ID: " + imdbID + "\nTMDB ID: " + tmdbID;

    }

}
