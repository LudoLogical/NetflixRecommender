package dgwerlod.movielensinterface;

import dgwerlod.movieanalysis.Movie;

import java.io.IOException;
import java.util.ArrayList;

public class Tester {

    public static void main(String[] args) {

        ArrayList<Movie> movies = new ArrayList<>();

        try {
            ArrayList<String> movieStrings = FileIO.readFile("ml-latest-small" + FileIO.FILE_SEPARATOR + "movies.csv");
            movieStrings.remove(0); // To do with contents of movieStrings, not content
            for (String s : movieStrings) {
                System.out.println(s);
                Movie now = CSVTranslator.translateMovie(s);
                System.out.println(now);
                movies.add(now);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
