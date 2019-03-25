import java.util.Arrays;

public class Movie {

	private int ID;
	private String title;
	private int year;
	private String[] genres;
	
	// Add other fields for handling ratings, links, and tags in some way
	
	
	
	// CONSTRUCTOR
	public Movie(int id, int year, String title, String[] genres) {
		this.ID = id;
		this.year = year;
		this.title = title;
		this.genres = genres;
	}
	
	public int getID() {
		return ID;
	}
	
	// TOSTRING
	public String toString() {
		String out = "ID: " + ID;
		out += "\nYEAR: " + year;
		out += "\nTITLE: " + title;
		out += "\nGENRES: " + Arrays.toString(genres);
		
		return out;
	}
	
	
}
