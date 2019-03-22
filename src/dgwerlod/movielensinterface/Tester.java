package dgwerlod.movielensinterface;

import dgwerlod.movieanalysis.Movie;

import java.io.IOException;
import java.util.ArrayList;

public class Tester {

    public static void main(String[] args) {

        ArrayList<Movie> movies = new ArrayList<>();

        try {
            ArrayList<String> movieStrs = FileIO.readFile("ml-latest-small" + FileIO.FILE_SEPARATOR + "movies.csv");
            for (String s : movieStrs) {
                movies.add(CSVTranslator.translateMovie(s));
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Movie m : movies) {
            System.out.println(m);
        }

    }

}
