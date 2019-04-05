package dgwerlod.moviegui;

import dgwerlod.moviestructures.Movie;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

@SuppressWarnings("WeakerAccess")
public class DrawingMovie {

	private Movie movie;
	private PImage coverArt;
	
	public DrawingMovie(Movie m) {
		this.movie = m;
		coverArt = null;
	}
	
	public void draw(PApplet drawer, float x, float y, float width, float height) {

		if (movie != null) {
			if (coverArt != null) {
				drawer.image(coverArt, x, y,width,height);
			}
			drawer.text(movie.getTitle(), x, y);
		}

		drawer.stroke(0);
		drawer.noFill();
		drawer.rect(x, y, width, height);

	}
	

	public void downloadArt(PApplet drawer) {
		
		Thread downloader = new Thread(new Runnable() {

			public void run() {

				// Find the cover art using IMDB links
				// Initialize coverArt

				Scanner scan = null;
				String urlFromID = "/title/tt" + movie.getImdbID() + "/";

				try {

					StringBuilder output = new StringBuilder();

					URL reader = new URL("https://www.imdb.com" + urlFromID);
					scan = new Scanner(reader.openStream());

					while(scan.hasNextLine()) {
						String line = scan.nextLine();
						output.append(line).append("\n");
					}

					int anchorIndex = output.indexOf("<a href=\"" + urlFromID + "mediaviewer/");
					int urlIndex = output.indexOf("src=\"", anchorIndex) + 5;

					coverArt = drawer.loadImage(output.substring(urlIndex, output.toString().indexOf('\"', urlIndex)));

				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (scan != null) {
						scan.close();
					}
				}

			}

		});
		
		downloader.start();

	}

	
}
