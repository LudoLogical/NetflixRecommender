package dgwerlod.movieanalysis;

import dgwerlod.movielensinterface.CSVTranslator;
import dgwerlod.movielensinterface.FileIO;
import dgwerlod.moviestructures.Movie;
import dgwerlod.moviestructures.Rating;
import dgwerlod.moviestructures.Tag;
import dgwerlod.moviestructures.User;

import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings({"Duplicates", "WeakerAccess"})
public class NetflixPredictor {

	public static final double CENTER_RATING = (5.0-0.5) / 2;

	// Add fields to represent your database.
	private ArrayList<Movie> movies = new ArrayList<>();
	private ArrayList<User> users = new ArrayList<>();
	private ArrayList<Rating> ratings = new ArrayList<>();
	private ArrayList<Tag> tags = new ArrayList<>();

	/** Use the file names to read all data into some local structures.
	 * @param movieFilePath The full path to the movies database.
	 * @param ratingFilePath The full path to the ratings database.
	 * @param tagFilePath The full path to the tags database.
	 * @param linkFilePath The full path to the links database.
	 */
	public NetflixPredictor (String movieFilePath, String ratingFilePath, String tagFilePath, String linkFilePath) {

		try {

			// These two lists have identical movie ids for each line
			ArrayList<String> movieStrings = FileIO.readFile(movieFilePath);
			ArrayList<String> linkStrings = FileIO.readFile(linkFilePath);
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

			ArrayList<String> ratingsStrings = FileIO.readFile(ratingFilePath);
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

			ArrayList<String> tagsStrings = FileIO.readFile(tagFilePath);
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

			// TODO set users and movies for both ratings and tags for indexing

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
		
	/** If userNumber has rated movieNumber, return the rating. Otherwise, return -1.
	 * @param userID The ID of the user.
	 * @param movieID The ID of the movie.
	 * @return The rating that userNumber gave movieNumber, or -1 if the user does not exist in the database, the
     * movie does not exist, or the movie has not been rated by this user.
	 */
	public double getRating(int userID, int movieID) {
	    for (User u : users) {
	        if (userID == u.getID()) {
	            return u.getRating(movieID);
            }
        }
		return -1;
	}
	
	/** If userNumber has rated movieNumber, return the rating. Otherwise, use other available data to guess what
     * this user would rate the movie.
	 * @param userID The ID of the user.
	 * @param movieID The ID of the movie.
	 * @return The rating that userNumber gave movieNumber, or the best guess if the movie has not been rated by
     * this user.
	 * @pre A user with id userID and a movie with id movieID exist in the database.
	 */
	public double guessRating(int userID, int movieID) {

		// If already rated, we done
		double previousRating = getRating(userID, movieID);
		if (previousRating > 0) {
			return previousRating;
		}

		// Retrieve the user and movie in question
		User subjectUser = null;
		Movie subjectMovie = null;
		for (User u : users) {
			if (userID == u.getID()) {
				subjectUser = u;
				break;
			}
		}
		for (Movie m : movies) {
			if (movieID == m.getID()) {
				subjectMovie = m;
				break;
			}
		}

		// Ensure that the user and movie are real
		if (subjectUser == null) {
			throw new IllegalArgumentException(userID + " does not exist!");
		} else if (subjectMovie == null) {
			throw new IllegalArgumentException(movieID + " does not exist!");
		}

		// Give a score based on ratings of similar genres and tags, if any
		double propertiesScore = 0;
		// TODO: Get movies, genres, and score

		// Handle average rating retrieval
		double popularRating = subjectMovie.getAverageRating();
		if (Double.isNaN(popularRating)) {
			return CENTER_RATING;
		} else {
			return popularRating;
		}

	}
	
	/** Recommend a movie that you think this user would enjoy (but they have not currently rated it).
	 * @param userID The ID of the user.
	 * @return The ID of a movie that data suggests this user would rate highly (but they haven't rated it currently).
	 * @pre A user with id userID exists in the database.
	 */
	public int recommendMovie(int userID) {
		return 0;
	}
	
}