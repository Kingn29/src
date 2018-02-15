 /* Nicholas King, CSC 131-03, Assignment 2
 * This program uses command line arguments to log start and stop times of particular task and saves the information in a text file title "tm.txt"
 * List of acceptable command line arguments can be found in TM.md provided, additional size function has been aded 2/12/18
 */ 
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TM
{
	public static void main(String[] args) throws IOException
	{
		WriteFile file = new WriteFile();
		String cmd = args[0];
		switch (cmd){
		case "start": cmdStart(file, args);
			break; 
		case "stop" : cmdStop(file, args);
			break;
		case "Start": cmdStart(file, args);
			break; 
		case "Stop" : cmdStop(file, args);
			break;
		case "Summary" : cmdSummary(args);
			break;
		case "summary" : cmdSummary(args);
			break;
		case "describe" : cmdDescribe(file, args);
			break;
		case "Describe" : cmdDescribe(file, args);
			break;
		case "Size" : cmdDescribe(file, args);
			break;
		case "size" : cmdDescribe(file,args);
			break;
		default : System.out.println("Unreadable Input");
			break;
		}	
	}
	private static void cmdStart(WriteFile file, String[] args) throws IOException {
		TimeStamp tm = new TimeStamp();
		String time = tm.main();
		saveData(time, file, args);
	}
	private static void cmdStop(WriteFile file, String[] args) throws IOException {
		TimeStamp tm = new TimeStamp();
		String time = tm.main();
		saveData(time, file, args); 
	}
	private static void cmdDescribe(WriteFile file, String[] args) throws IOException {	
		String[] description = new String[args.length-2];
		if (args[0].equalsIgnoreCase("size")) {
			for (int i = 2; i < args.length; i++) {
				description[i-2] = args[i];
			}
			saveData(args[1] + " Size:", file, description);
		}
		else {
			for (int i = 2; i < args.length; i++) {
				description[i-2] = args[i];
			}
			saveData(args[1] + " Description:", file, description);
			
		}
	}
	
	//Method retrieved 2/1/2018 from https://stackoverflow.com/questions/16169418/write-a-program-find-that-searches-all-files-specified-on-the-command-line-and-p
	public static void cmdSummary(String[] args) throws FileNotFoundException {	
		File one = new File("tm.txt");
	    Scanner in = new Scanner(one);
	    if (args.length > 1) {
		    String input = args[1];
		    while (in.hasNextLine()) {
		        String een = in.nextLine();
	
		        if (een.contains(input)) {
		            System.out.println(een);
		        }
		    }
	    }
	    else if (args.length == 1) 
	    {
	    	while (in.hasNextLine()) {
		        String een = in.nextLine();
		        System.out.println(een);
		    }
	    }
	    in.close();
	}
	
	private static void saveData(String time, WriteFile file, String[] args) throws IOException {
		file.openFile();
		file.addText(time, args);
		file.closeFile();
		System.out.println("Data has been saved to tm.txt");
	}
}
class TimeStamp	
{
	public static String main() {
		//current date and time retrived using DateFormat and Calendar class
		DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		Calendar calobj = Calendar.getInstance();
		return (df.format(calobj.getTime()));
	}
}
class WriteFile{
	private Formatter form = new Formatter();
	ArrayList<String> list = new ArrayList<String>();
	public void openFile(){
		try {
			PrintWriter outFile = new PrintWriter(new FileWriter("tm.txt", true));
			form = new Formatter();
		}
		catch(Exception e) {
			System.out.println("Error with creating file.");
		}
	}
	public void addText(String time, String[] args) throws IOException {
		list.add(time);
		for (int i = 0; i < args.length; i++){
			list.add(args[i]);
		}
		List<String> list = new ArrayList<>(Arrays.asList(args));
		String formattedString = list.toString().replace(",", "").replace("[", "").replace("]", "").trim();          
		PrintWriter outFile = new PrintWriter(new FileWriter("tm.txt", true));
		outFile.println(form.format("%s %s", time, formattedString));
		outFile.close();
	}
	public void closeFile() {
		form.close();
	}
}