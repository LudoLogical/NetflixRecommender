package dgwerlod.movielensinterface;

import dgwerlod.movieanalysis.Movie;

import java.io.IOException;
import java.util.ArrayList;

public class Tester {

    public static void main(String[] args) {

        //ArrayList<Movie> movies = new ArrayList<>();

        try {
            // These two lists have identical movie ids for each line
            ArrayList<String> movieStrings = FileIO.readFile("ml-latest-small" + FileIO.FILE_SEPARATOR + "movies.csv");
            ArrayList<String> linkStrings = FileIO.readFile("ml-latest-small" + FileIO.FILE_SEPARATOR + "links.csv");
            // Begin with i=1 to avoid explanatory line 0
            for (int i = 1; i < movieStrings.size(); i++) {
                System.out.println(movieStrings.get(i));
                System.out.println(linkStrings.get(i));
                Movie now = CSVTranslator.translateMovie(movieStrings.get(i));
                String[] links = CSVTranslator.translateLinks(linkStrings.get(i));
                now.addLinks(links[0], links[1]);
                System.out.println(now);
                //movies.add(now);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
