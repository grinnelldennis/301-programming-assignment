import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Scanner;

public class LibraryFile {

	private HashMap<String, HashMap<String, String>> lib;

	public LibraryFile (String path) throws FileNotFoundException{
		lib = new HashMap<>();
		read(path);
	}
	public HashMap<String, HashMap<String, String>> get() {	return lib; }

	public void read (String path) throws FileNotFoundException {
		Scanner scanf = new Scanner(new File(path));
		String key = "";
		while (scanf.hasNextLine()) {
			String line = scanf.nextLine();
			if (line.startsWith("file_id,")) 
				key = createPiece (line.substring(8));
			else if (!line.isEmpty()  && line.contains(",") && line.length() > line.indexOf(",") + 1 && key != null) 
				populateAttribute(key, line.substring(0, line.indexOf(',')),	line.substring(line.indexOf(',') + 1));
			else
				key = null;
		}
	}

	private String createPiece (String filename) {
		HashMap<String, String> value = new HashMap<>(); 
		lib.put(filename, value);
		return filename;
	}

	private void populateAttribute (String filename, String field, String property) {
		if (lib.containsKey(filename)) {
			lib.get(filename).put(field, property);
		}
	}

	/**
	 * Writes data structure to file
	 * @param path, a relative or fully qualified path to an output file
	 */
	public void write (String path, HashMap<String, HashMap<String, String>> map) {
		try{
			PrintWriter writer = new PrintWriter(path, "UTF-8");
			map.forEach((k, v) -> writeAttributes (k, v, writer));
			writer.close();
		} catch (IOException e) { }
	}

	public void writeAttributes (String key, HashMap<String, String> table, PrintWriter write) {
		write.println ("\nfile_id," + key);
		table.forEach((k, v) -> write.println (v));
	}
	
	
	
}
