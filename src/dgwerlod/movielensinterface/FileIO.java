package dgwerlod.movielensinterface;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO {

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static ArrayList<String> readFile(String filename) throws IOException {

        Scanner scan = null;
        ArrayList<String> output = new ArrayList<>();

        try {
            FileReader reader = new FileReader(filename);
            scan = new Scanner(reader);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                output.add(line);
            }
        } finally {
            if (scan != null) {
                scan.close();
            }
        }

        return output;

    }

    public static void writeFile(String filename, ArrayList<String> data) throws IOException  {

        FileWriter writer = null;

        try {
            writer = new FileWriter(filename);
            for (String s : data) {
                writer.write(s);
                writer.write(LINE_SEPARATOR);
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }

}
