import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class MusicLibrary {

	private HashMap<String, HashMap<String, String>> lib;
	private Scanner scan;
	private LibraryFile file;
	private Timer timer;

	public MusicLibrary(String path) throws FileNotFoundException, UnsupportedEncodingException {
		timer = new Timer();
		timer.set();
		file = new LibraryFile(path);
		timer.end("read form file");
		lib = file.get();
		scan = new Scanner(System.in);
	}

	/*--Piece-Operations--*/
	public void addPiece () {
		String filename = getString ("Filename. ");
		if (pieceExists(filename)) { System.out.println ("Piece already exists. "); }
		else { createPiece(filename, true); }
	}

	/* Private Methods */
	private boolean pieceExists (String filename) {
		boolean isCreated;
		if (lib.containsKey(filename)) { return true; } 
		else { isCreated = createPrompt(); }
		return isCreated;
	}

	private boolean createPrompt() {
		if (getBoolean("Create new piece?")) {
			String filename = getString("Filename. ");
			createPiece(filename, true); 
		} else 
			return false;
		return true;
	}

	public boolean createPiece (String filename, boolean run) {
		timer.set();
		lib.put(filename, new HashMap<String, String>());
		boolean isCreated = lib.containsKey(filename);
		if (isCreated && run) {
			addAttribute(filename, "Title", getString("Title. "));
			createAttributePrompt(filename);
		}
		timer.end("create " + filename);
		return isCreated;
	}

	
	/*
  	 -Attributes-Operations-
	 */
	private void createAttributePrompt(String key) {
		while (getBoolean("Create a new attribute?")) addAttribute(key); 
	}

	public void addAttribute(String filename) {
		String attribute = getString("Attribute. ");
		String property = getString("Property. ");
		addAttribute(filename, attribute, property);
	}

	private void addAttribute(String filename, String attribute, String property) {
		if (pieceExists(filename)) {
			lib.get(filename).put(attribute, attribute + ": " + property);
			// Print existing attributes
		}
	}

	/*
  	 -Generic Getter Methods-
	 */
	private Boolean getBoolean (String prompt) {
		System.out.print (prompt);
		Boolean input = scan.nextLine().equalsIgnoreCase("yes")? true : false;
		return input;
	}

	private String getString (String prompt) {
		System.out.print (prompt);
		String input = scan.nextLine();
		return input;
	}

	public void findPiece (String filename) {
		if (pieceExists(filename))
			printPiece(filename, lib.get(filename));
	}

	public void filter (String key, String value) {
		timer.set();
		lib.forEach((k, v) -> filterAttribute(k, v, key, value));
		timer.end("filter for " + value);
	}

	private void filterAttribute (String k, HashMap<String, String> v, String key, String value) {
		if (v.containsKey(key))
			if (v.get(key).equals(value))
				System.out.println (k + " contains " + key + ", " + value);
	}
	
	public void saveToFile (String path){
		file.write(path, lib);
	}

	/*--Print-Hashmap--*/
	public void printLibrary () {
		lib.forEach((k, v) -> printPiece (k, v));
	}

	public void printPiece (String key, HashMap<String, String> table) {
		System.out.println ("\nFile. " + key);
		table.forEach((k, v) -> System.out.print (v+" | "));
	}

	public void removePiece (String key) {
		timer.set();
		if (lib.containsKey(key)) { lib.remove(key); }
		else { System.out.println ("Library does not contain " + key); }
		timer.end("remove " + key);
	}

	public void removeAttribute (String key, String attribute) {
		timer.set();
		if (lib.containsKey(key))
			if (lib.get(key).containsKey(attribute))
				lib.get(key).remove(attribute);
			else 
				System.out.println (key + " does not contain " + attribute + ".");
		else
			System.out.println ("Library does not contain " + key);
		timer.end("remove attribute " + key);
	}

	public void closeTimer() {
		timer.close();
	}
}



