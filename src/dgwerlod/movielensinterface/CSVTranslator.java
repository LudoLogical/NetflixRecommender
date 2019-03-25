package dgwerlod.movielensinterface;

import dgwerlod.movieanalysis.Movie;
import dgwerlod.movieanalysis.Rating;
import dgwerlod.movieanalysis.Tag;

import java.util.ArrayList;

@SuppressWarnings("WeakerAccess")
public class CSVTranslator {

    @SuppressWarnings("Duplicates")
    private static ArrayList<String> getSegments(String line) {

        ArrayList<String> segments = new ArrayList<>();
        boolean inQuotes = false;
        int start = 0;

        for (int i = 0; i < line.length(); i++) {
            char thisChar = line.charAt(i);
            if (thisChar == '"') {
                inQuotes = !inQuotes;
            } else if (thisChar == ',' && !inQuotes) {
                segments.add(line.substring(start,i));
                start = i+1;
            }
        }

        segments.add(line.substring(start));
        return segments;

    }

    public static Movie translateMovie(String data) {

        ArrayList<String> segments = getSegments(data);

        int id = Integer.parseInt(segments.get(0));

        String[] genresArray;
        if (segments.get(2).equals(("(no genres listed)"))) {
            genresArray = new String[0];
        } else {
            genresArray = segments.get(2).split("\\|");
        }

        String titleAndYear = segments.get(1);
        if (titleAndYear.charAt(0) == '\"' && titleAndYear.charAt(titleAndYear.length()-1) == '\"') {
            titleAndYear = titleAndYear.substring(1, titleAndYear.length()-1);
        }

        String title;
        int year;
        int lastOpen = titleAndYear.lastIndexOf('(');
        int lastClosed = titleAndYear.lastIndexOf(')');

        // If no parentheses at all, no year and only title
        if (lastOpen == -1 && lastClosed == -1) {
            title = titleAndYear.trim();
            year = Movie.NO_YEAR;
        } else {
            // If year is present, it will be the last () item
            try {
                title = titleAndYear.substring(0, lastOpen - 1);
                year = Integer.parseInt(titleAndYear.substring(lastOpen + 1, lastOpen + 5)); // All years are 4 digits
            // If year is absent, parseInt will fail
            } catch (NumberFormatException e) {
                title = titleAndYear.trim();
                year = Movie.NO_YEAR;
            }

        }

        return new Movie(id, title, year, genresArray);

    }

    public static String[] translateLinks(String s) {
        ArrayList<String> output = getSegments(s);
        output.remove(0); // removes user ID
        return output.toArray(new String[] {});
    }

    public static Rating translateRating(String s) {

        ArrayList<String> segments = getSegments(s);

        int userID = Integer.parseInt(segments.get(0));
        int movieID = Integer.parseInt(segments.get(1));
        double rating = Double.parseDouble(segments.get(2));
        int timestamp = Integer.parseInt(segments.get(3));

        return new Rating(rating, userID, movieID, timestamp);

    }

    public static Tag translateTag(String s) {

        ArrayList<String> segments = getSegments(s);

        int userID = Integer.parseInt(segments.get(0));
        int movieID = Integer.parseInt(segments.get(1));
        String tag = segments.get(2);
        int timestamp = Integer.parseInt(segments.get(3));

        return new Tag(tag, userID, movieID, timestamp);
    }
}
