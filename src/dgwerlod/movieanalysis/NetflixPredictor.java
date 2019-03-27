package dgwerlod.movieanalysis;

import java.util.ArrayList;

public class NetflixPredictor {

	// Add fields to represent your database.

	/** Use the file names to read all data into some local structures.
	 * @param movieFilePath The full path to the movies database.
	 * @param ratingFilePath The full path to the ratings database.
	 * @param tagFilePath The full path to the tags database.
	 * @param linkFilePath The full path to the links database.
	 */
	public NetflixPredictor (String movieFilePath, String ratingFilePath, String tagFilePath, String linkFilePath) {

	}
		
	/** If userNumber has rated movieNumber, return the rating. Otherwise, return -1.
	 * @param userID The ID of the user.
	 * @param movieID The ID of the movie.
	 * @return The rating that userNumber gave movieNumber, or -1 if the user does not exist in the database, the movie does not exist, or the movie has not been rated by this user.
	 */
	public double getRating(int userID, int movieID) {
		return -1;
	}
	
	/** If userNumber has rated movieNumber, return the rating. Otherwise, use other available data to guess what this user would rate the movie.
	 * @param userID The ID of the user.
	 * @param movieID The ID of the movie.
	 * @return The rating that userNumber gave movieNumber, or the best guess if the movie has not been rated by this user.
	 * @pre A user with id userID and a movie with id movieID exist in the database.
	 */
	public double guessRating(int userID, int movieID) {
		return 0;
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