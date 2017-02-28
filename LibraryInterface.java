import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Scanner;

public class LibraryInterface {


	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
				
		MusicLibrary lib = new MusicLibrary("testsuite2.csv");
		
		//lib.removePiece("319605411960364735293");
		//lib.createPiece("785600858333646414316", false);
		//lib.removePiece("785600858333646414316");
		//lib.removeAttribute("200514314902268857046", "training");
		//lib.createPiece("785275154747142026654", false);
		//lib.removePiece("166036411607897040660");
		lib.filter("first", "feedback");
		
		lib.saveToFile("output.csv");
		
		lib.closeTimer();
	}

}


