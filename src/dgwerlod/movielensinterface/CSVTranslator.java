package dgwerlod.movielensinterface;

import dgwerlod.movieanalysis.Movie;
import dgwerlod.movieanalysis.Rating;
import dgwerlod.movieanalysis.Tag;

public class CSVTranslator {

    public static Movie translateMovie(String data) {

        int id = Integer.parseInt(data.substring(0, data.indexOf(',')));

        String genresRaw = data.substring(data.lastIndexOf(',') + 1);

        String[] genresArray;
        if (genresRaw.equals(("(no genres listed)"))) {
            genresArray = new String[0];
        } else {
            genresArray = genresRaw.split("\\|");
        }

        String titleAndYear = data.substring(data.indexOf(',') + 1, data.lastIndexOf(','));
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
                year = Integer.parseInt(titleAndYear.substring(lastOpen + 1, lastClosed));
            // If year is absent, parseInt will fail
            } catch (NumberFormatException e) {
                title = titleAndYear.trim();
                year = Movie.NO_YEAR;
            }

        }

        return new Movie(id, title, year, genresArray);

    }

    public static String[] translateLinks(String s) {

        int firstComma = s.indexOf(',');
        int lastComma = s.lastIndexOf(',');
        return new String[] {s.substring(firstComma + 1, lastComma), s.substring(lastComma + 1)};

    }

    private static int[] ratingAndTagSetup(String s) {

        int firstComma = s.indexOf(',');
        int secondComma = s.indexOf(',', firstComma + 1);
        int thirdComma = s.indexOf(',', secondComma + 1);

        int userID = Integer.parseInt(s.substring(0, firstComma));
        int movieID = Integer.parseInt(s.substring(firstComma + 1, secondComma));

        return new int[] {userID, movieID, secondComma, thirdComma};

    }

    public static Rating translateRating(String s) {

        int[] setupData = ratingAndTagSetup(s);

        double rating = Double.parseDouble(s.substring(setupData[2] + 1, setupData[3]));
        int timestamp = Integer.parseInt(s.substring(setupData[3] + 1));

        return new Rating(rating, setupData[0], setupData[1], timestamp);

    }

    public static Tag translateTag(String s) {

        int[] setupData = ratingAndTagSetup(s);

        String tag = s.substring(setupData[2] + 1, setupData[3]);
        int timestamp = Integer.parseInt(s.substring(setupData[3] + 1));

        return new Tag(tag, setupData[0], setupData[1], timestamp);
    }
}
