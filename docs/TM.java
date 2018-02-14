 /* Nicholas King, CSC 131-03, Assignment 2
 * This program uses command line arguments to log start and stop times of particular task and saves the information in a text file title "tm.txt"
 * List of acceptable command line arguments can be found in TM.md provided
 */
import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
		Timestamp time = tm.main();
		//saveData(time, file, args);
	}
	private static void cmdStop(WriteFile file, String[] args) throws IOException {
		TimeStamp tm = new TimeStamp();
		Timestamp time = tm.main();
		//saveData(time, file, args);
	}
	private static void cmdDescribe(WriteFile file, String[] args) throws IOException {	
		String[] description = new String[args.length-2];
		if (args[1].equalsIgnoreCase("size")) {
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

class TaskDuration{
	private LocalDateTime start, stop;
	public void TaskDuration(LocalDateTime start,LocalDateTime stop) {
		this.start = start;
		this.stop = stop;
	}
	
}

class Task{
	String name;
	String description;
	LinkedList<TaskDuration> durations;
	public void task(String name, LinkedList<TaskLogEntry>entries) {
		Timestamp lastStart = null;
		this.name = name;
		LinkedList<TaskDuration> duration = new LinkedList<TaskDuration>();
		for (TaskLogEntry entry : entries) {
			if (entry.name.equals(this.name)) {
				WriteFile file = new WriteFile();
				switch (entry.command){
				case "start" : 
					lastStart = TimeStamp.main();
				case "stop" :
					if (lastStart != null)
						duration = entry.timestamp - lastStart;
				}
			}
	 	
		}
	}
	public void addDuration(LocalDateTime start, LocalDateTime stop) {
		
	}
}

class TaskLogEntry{
	LocalDateTime start, stop;
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	String name;
	String command;
	String data;
	StringTokenizer stock = new StringTokenizer(command, "\n"); {
		if (stock.countTokens() > 3) 
			data = stock.nextToken();
		else
			data = "";
	}
	
	
}

class TimeStamp	
{
	public static Timestamp main() {
		//current date and time retrived using DateFormat and Calendar class
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		Calendar calobj = Calendar.getInstance();
		return (timestamp);
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
