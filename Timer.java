import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Timer {
	private PrintWriter write;
	private long startTime;
	private long endTime;
	
	public Timer () throws FileNotFoundException, UnsupportedEncodingException {
		write = new PrintWriter("Log_"+System.nanoTime()/1000000+".txt", "UTF-8");
	}
	
	public void set() { startTime = System.nanoTime(); }
	
	public void end(String operation) { 
		endTime = System.nanoTime(); 
		log(operation);
	}
	
	public void log (String operation) {
		long duration = endTime - startTime;
		System.out.println(operation + "---" + startTime + ": " + endTime + ": " + duration);
	}
	
	public void close() {
		write.close();
	}

}
