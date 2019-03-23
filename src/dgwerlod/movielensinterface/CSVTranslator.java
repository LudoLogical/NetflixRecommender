package dgwerlod.movielensinterface;

import dgwerlod.movieanalysis.Movie;

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

}
