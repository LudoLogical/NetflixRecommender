package dgwerlod.movieanalysis;

public class Movie {

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


}
