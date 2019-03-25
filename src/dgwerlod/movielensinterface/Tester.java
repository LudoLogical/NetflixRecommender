package dgwerlod.movielensinterface;

import dgwerlod.movieanalysis.Movie;
import dgwerlod.movieanalysis.Rating;
import dgwerlod.movieanalysis.Tag;
import dgwerlod.movieanalysis.User;

import java.io.IOException;
import java.util.ArrayList;

public class Tester {

    private static final String DATASET = "ml-latest-small";

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        ArrayList<Movie> movies = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Rating> ratings = new ArrayList<>();
        ArrayList<Tag> tags = new ArrayList<>();

        try {

            // These two lists have identical movie ids for each line
            ArrayList<String> movieStrings = FileIO.readFile(DATASET + FileIO.FILE_SEPARATOR + "movies.csv");
            ArrayList<String> linkStrings = FileIO.readFile(DATASET + FileIO.FILE_SEPARATOR + "links.csv");
            // Begin with i=1 to avoid explanatory line 0
            for (int i = 1; i < movieStrings.size(); i++) {
                //System.out.println(movieStrings.get(i));
                //System.out.println(linkStrings.get(i));
                Movie now = CSVTranslator.translateMovie(movieStrings.get(i));
                String[] links = CSVTranslator.translateLinks(linkStrings.get(i));
                now.addLinks(links[0], links[1]);
                //System.out.println(now);
                movies.add(now);
            }

            ArrayList<String> ratingsStrings = FileIO.readFile(DATASET + FileIO.FILE_SEPARATOR + "ratings.csv");
            ratingsStrings.remove(0);
            for (String r : ratingsStrings) {

                //System.out.println(ratingsStrings.get(i));
                Rating now = CSVTranslator.translateRating(r);
                ratings.add(now);
                //System.out.println(now);

                // All users guaranteed to have left at least 20 reviews
                boolean userFound = false;
                for (User u : users) {
                    if (now.getUserID() == u.getID()) {
                        userFound = true;
                        u.addRating(now);
                        //System.out.println(u);
                    }
                }
                if (!userFound) {
                    User newUser = new User(now.getUserID());
                    newUser.addRating(now);
                    users.add(newUser);
                    //System.out.println(newUser);
                }

                for (Movie m : movies) {
                    if (now.getMovieID() == m.getID()) {
                        m.addRating(now);
                        //System.out.println(m);
                    }
                }

                //System.out.println();

            }

            ArrayList<String> tagsStrings = FileIO.readFile(DATASET + FileIO.FILE_SEPARATOR + "tags.csv");
            tagsStrings.remove(0);
            for (String t : tagsStrings) {

                //System.out.println(t);
                Tag now = CSVTranslator.translateTag(t);
                tags.add(now);
                //System.out.println(now);

                for (User u : users) {
                    if (now.getUserID() == u.getID()) {
                        u.addTag(now);
                        //System.out.println(u);
                    }
                }

                for (Movie m : movies) {
                    if (now.getMovieID() == m.getID()) {
                        m.addTag(now);
                        //System.out.println(m);
                    }
                }

                //System.out.println();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("All operations took a combined " + (end-start)/1000.0 + " seconds.");

    }

}
