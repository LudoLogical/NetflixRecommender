package dgwerlod.movieanalysis;

import dgwerlod.movielensinterface.CSVTranslator;
import dgwerlod.movielensinterface.FileIO;
import dgwerlod.moviestructures.Movie;
import dgwerlod.moviestructures.Rating;
import dgwerlod.moviestructures.Tag;
import dgwerlod.moviestructures.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings({"Duplicates", "WeakerAccess"})
public class NetflixPredictor {

	/* Current Best Score:
	Runtime: 0:5:32
	Total difference between guessed and actual ratings: 6782.66324946808
	Average difference between guessed and actual ratings: 0.6782663249468079
	Root square mean difference between guessed and actual ratings: 0.8890619580767919
	 */

	public static final double POPULARITY_WEIGHT = 0.9;
	public static final double GENRES_WEIGHT = 6.25;
	public static final double TAGS_WEIGHT = 1.9;

	public static final double CENTER_RATING = (5.0-0.5) / 2 + 0.5;

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
	public NetflixPredictor(String movieFilePath, String ratingFilePath, String tagFilePath, String linkFilePath) {

		try {

			// Create Movie objects and assign links to them
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

			// Create Ratings and User objects; assign Ratings to User/Movie
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
						break;
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
						break;
					}
				}

				//System.out.println();

			}

			// Create Tag objects and assign to User/Movie
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
						break;
					}
				}

				for (Movie m : movies) {
					if (now.getMovieID() == m.getID()) {
						m.addTag(now);
						//System.out.println(m);
						break;
					}
				}

				//System.out.println();

			}

			// Assign User/Movie objects to Ratings/Tags
			for (Rating r : ratings) {
				r.setMovie(movies.get(Collections.binarySearch(movies, new Movie(r.getMovieID()))));
				r.setUser(users.get(Collections.binarySearch(users, new User(r.getUserID()))));
			}
			for (Tag t : tags) {
				t.setMovie(movies.get(Collections.binarySearch(movies, new Movie(t.getMovieID()))));
				t.setUser(users.get(Collections.binarySearch(users, new User(t.getUserID()))));
			}

			Collections.sort(movies);
			Collections.sort(users);
			Collections.sort(ratings);
			Collections.sort(tags);

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
		int uIndex = Collections.binarySearch(users, new User(userID));
		if (uIndex >= 0) {
			int rIndex = Collections.binarySearch(users.get(uIndex).getRatings(), new Rating(userID, movieID));
			if (rIndex >= 0) {
				return ratings.get(rIndex).getRating();
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
		User subjectUser = users.get(Collections.binarySearch(users, new User(userID)));
		Movie subjectMovie = movies.get(Collections.binarySearch(movies, new Movie(movieID)));

		// Give a score based on ratings of similar genres and tags, if any
		double genresScore = 0, tagsScore = 0;
        int totalGenreScores = 0, totalTagScores = 0;
		String[] subjectMovieGenres = subjectMovie.getGenres();
		ArrayList<Tag> subjectMovieTags = subjectMovie.getTags();
		ArrayList<Rating> subjectUserRatings = subjectUser.getRatings();
		for (Rating r : subjectUserRatings) {
			int matches = 0; // matching genres between r and subjectMovie
            for (String reviewedGenre : r.getMovie().getGenres()) {
                for (String nowGenre : subjectMovieGenres) {
                    if (nowGenre.equals(reviewedGenre)) {
						matches++;
                    	break;
                    }
                }
            }
			genresScore += matches * r.getRating();
            totalGenreScores += matches;

			matches = 0; // matching tags between r and subjectMovie
			for (Tag reviewedTag : r.getMovie().getTags()) {
				for (Tag nowTag : subjectMovieTags) {
					if (nowTag.getTag().equals(reviewedTag.getTag())) {
						matches++;
						break;
					}
				}
			}
			tagsScore += matches * r.getRating();
			totalTagScores += matches;
        }

		// Scale scores to expected ratings by averaging
		double genresExpectedRating = 0, tagsExpectedRating = 0;
		if (totalGenreScores != 0) {
			genresExpectedRating = genresScore / totalGenreScores;
		}
		if (totalTagScores != 0) {
			tagsExpectedRating = tagsScore / totalTagScores;
		}

		// Get the user's rating of an average movie
		double subjectUserAvgRating = subjectUser.getAverageRating();
		if (Double.isNaN(subjectUserAvgRating)) {
			subjectUserAvgRating = CENTER_RATING;
		}
		// Handle average rating retrieval
		double popularRating = subjectMovie.getAverageRating();
		if (Double.isNaN(popularRating)) {
			popularRating = 0;
		}
		// Scale popularRating to popularScore based on user average rating
		double popularExpectedRating = popularRating * (subjectUserAvgRating / CENTER_RATING);

		double divisor = 0;
		if (popularExpectedRating != 0) {
			divisor += POPULARITY_WEIGHT;
		}
		if (genresExpectedRating != 0) {
			divisor += GENRES_WEIGHT;
		}
		if (tagsExpectedRating != 0) {
			divisor += TAGS_WEIGHT;
		}

		double output;
		if (divisor == 0.0) {
			output = subjectUserAvgRating; // No data to predict with
		} else {
			output = (popularExpectedRating * POPULARITY_WEIGHT + genresExpectedRating *
					  GENRES_WEIGHT + tagsExpectedRating * TAGS_WEIGHT) / divisor;
		}

		// Reduce extra error by accounting for overconfidence
		if (output > 4.5) {
			return 4.5;
		} else if (output < 1) {
			return 1;
		} else {
			return output;
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