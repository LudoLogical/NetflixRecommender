import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO {
	
	
	public static final String fileSep = System.getProperty("file.separator");
	public static final String lineSep = System.getProperty("line.separator");
	

	public static ArrayList<String> readFile(String filename) throws IOException {
		
		Scanner scan = null;
		
		try {
			ArrayList<String> output = new ArrayList<String>();
		
			FileReader reader = new FileReader(filename);
			scan = new Scanner(reader);
			
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				output.add(line);
			}
			
			return output;
		
		} finally {
			if (scan != null)
				scan.close();
		}
		
	}
	
	
	public static void writeFile(String filename, ArrayList<String> fileData) throws IOException  {
		
		FileWriter writer = null;
		
		try {
			
			writer = new FileWriter(filename);
			
			for(String line : fileData) {
				writer.write(line);
				writer.write(lineSep);
			}
			
		} finally {
			if (writer != null)
				writer.close();
		}

	}
	
	
	
}
