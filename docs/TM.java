  /* Nicholas King, CSC 131-03, Assignment 2
 * This program uses command line arguments to log start and stop times of particular task and saves the information in a text file title "tm.txt"
 * List of acceptable command line arguments can be found in TM.md provided
 */
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TM
{
	
	public static void main(String[] args) throws IOException
	{
		TaskLog log = new TaskLog("tm.txt");
		LinkedList<TaskLogEntry> entries;
		switch(args[0]) {
		case "start" : log.writeLine(LocalDateTime.now() + "\t" + args[1] + "\t" + "start");
			break;
		case "stop" :  log.writeLine(LocalDateTime.now() + "\t" + args[1] + "\t" + "stop");
			break;
		case "describe" : 
			String[] taskDescription = Arrays.copyOfRange(args, 2, args.length-1);
			log.writeLine(LocalDateTime.now() + "\t" + args[1] + "\t" + "describe" + "\t" + taskDescription);
			break;
		case "summary" :
			entries = log.read();
			if (args.length >1) {
				Task task = new Task(args[1], entries);
				System.out.print(task);
			}
			else
			{
				System.out.println(summary(entries));
			}
		}
		
		
		/*WriteFile file = new WriteFile();
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
		case "rename" : cmdRename(file, args);
			break;
		case "Rename" : cmdRename(file, args);
			break;
		case "delete" : cmdRename(file, args);
			break;
		case "Delete" : cmdRename(file, args);
			break;
		default : System.out.println("Unreadable Input");
			break;
		}	*/
	}
	/*static void run(String[] args) throws IOException {
		TaskLog log = new TaskLog("tm.txt");
		LinkedList<TaskLogEntry> entries;
		switch(args[0]) {
		case "start" : log.writeLine(LocalDateTime.now() + "\t" + args[1] + "\t" + "start");
			break;
		case "stop" :  log.writeLine(LocalDateTime.now() + "\t" + args[1] + "\t" + "stop");
			break;
		case "describe" : log.writeLine(LocalDateTime.now() + "\t" + args[1] + "describe" + "\t" + args[2]);
			break;
		case "summary" :
			entries = log.read();
			if (args.length >1) {
				Task task = new Task(args[1], entries);
				System.out.print(task.print());
			}
			else
			{
				System.out.println(summary(entries));
			}
		}
	}*/
	static StringBuilder summary(LinkedList<TaskLogEntry> entries) {
		TreeSet<String> taskNames = new TreeSet<String>();	
		long totalSecondsForAllTasks = 0;
		long totalSecondsOnTask = 0;
		StringBuilder summaryText = new StringBuilder();
		for (TaskLogEntry entry : entries) {
			taskNames.add(entry.name);
		}
		for (String taskName : taskNames) {
			Task task = new Task(taskName, entries);
			totalSecondsForAllTasks += task.elapsedSeconds;
			summaryText.append(task + "\n");
		}
		summaryText.append("Total time spent on all tasks = " + toHoursMinutesSeconds(totalSecondsForAllTasks));
		
		return summaryText;
	}
	
	static String toHoursMinutesSeconds(long totalSeconds) {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		long hours = TimeUnit.SECONDS.toHours(totalSeconds);
		long minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) - (TimeUnit.SECONDS.toHours(totalSeconds) * 60);
		long seconds = TimeUnit.SECONDS.toSeconds(totalSeconds) - (TimeUnit.SECONDS.toMinutes(totalSeconds) *60);
		String s = "" + hours + ":" + minutes + ":" + seconds;
		return s;
	}
	
	
/*	private static void cmdStart(WriteFile file, String[] args) throws IOException {
		/*String ts = LocalDateTime.now().toString();
		LocalDateTime start = LocalDateTime.parse(ts);
		saveData(ts, file, args);
		*/
		
		//TaskLogEntry entry = new TaskLogEntry();
		/*
	}
	private static void cmdStop(WriteFile file, String[] args) throws IOException {
		String ts = LocalDateTime.now().toString();
		LocalDateTime start = LocalDateTime.parse(ts);
		saveData(ts, file, args);
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
	*/
	//Method retrieved 2/1/2018 from https://stackoverflow.com/questions/16169418/write-a-program-find-that-searches-all-files-specified-on-the-command-line-and-p
	public static String cmdSummary(String[] args) throws FileNotFoundException {	
		File one = new File("tm.txt");
		String een = "";
	    Scanner in = new Scanner(one);
	    if (args.length > 1) {
		    String input = args[1];
		    while (in.hasNextLine()) {
		        een = in.nextLine();
		        if (een.contains(input)) {
		           System.out.println(een);
		        }
		    }
		    return een;
	    }
	    else if (args.length == 1) 
	    {
	    	while (in.hasNextLine()) {
		        een = in.nextLine();
		        System.out.println(een);
		    }
	    	return een;
	    }
	    in.close();
		return een;
	}
	private static void saveData(String time, WriteFile file, String[] args) throws IOException {
		file.openFile();
		file.addText(time, args);
		file.closeFile();
		System.out.println("Data has been saved to tm.txt");
	}
	
	private static void cmdRename(WriteFile file, String args[]) {
		Scanner sc = new Scanner(System.in);
		String oldText = "";
		if (args[0].equalsIgnoreCase("rename")) {
			oldText = args[1];
			String newText = args[2];;
			rename("tm.txt", oldText, newText);
			System.out.println("Task " + oldText + " now appears as " + newText + " in tm.txt");
		}
		else if (args[0].equalsIgnoreCase("delete")){
			oldText = args[1];
			String newText = "";
			rename("tm.txt", oldText, newText);
		}
		
		
	}
	//code adapted from http://javaconceptoftheday.com/modify-replace-specific-string-in-text-file-in-java/
	//on 2/22/18
	static void rename(String filePath, String oldString, String newString)
    {
        File fileToBeModified = new File("StringBuilder summery(LinkedList<TaskLogEntry> entries);tm.txt");
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;
        if (!newString.equals("")) {
        	 try
             {
                 reader = new BufferedReader(new FileReader(fileToBeModified));
                 //Reading all the lines of input text file into oldContent
                 String line = reader.readLine();
                 	while (line != null){
                 		oldContent = oldContent + line + System.lineSeparator();
                         line = reader.readLine();
                         }
                 //Replacing oldString with newString in the oldContent          
                 String newContent = oldContent.replaceAll(oldString, newString);             
                 //Rewriting the input text file with newContent             
                 writer = new FileWriter(fileToBeModified);             
                 writer.write(newContent);
             }
             catch (IOException e)
             {
                 e.printStackTrace();
             }
             finally
             {
                 try
                 {
                     //Closing the resources                 
                     reader.close();                 
                     writer.close();
                 } 
                 catch (IOException e) 
                 {
                     e.printStackTrace();
                 }
             }
        }
        else if (newString.equals("")) {
        	 try
             {
                 reader = new BufferedReader(new FileReader(fileToBeModified));
                 //Reading all the lines of input text file into oldContent
                 String line = reader.readLine();
                 	while (line != null){
                 		oldContent = oldContent + line + System.lineSeparator();
                         line = reader.readLine();
                         }
                 //Replacing oldString with newString in the oldContent       
                 String newContent = oldContent.replaceAll(oldString, "DELETED TASK");             
                 //Rewriting the input text file with newContent             
                 writer = new FileWriter(fileToBeModified);             
                 writer.write(newContent);
             }
             catch (IOException e)
             {
                 e.printStackTrace();
             }
             finally
             {
                 try
                 {
                     //Closing the resources                 
                     reader.close();                 
                     writer.close();
                 } 
                 catch (IOException e) 
                 {
                     e.printStackTrace();
                 }
             }
        	 System.out.println("Task has been flagged as \"DELETED TASK\" in tm.txt");
        }
       
    }
}


class TaskLog{
	String fileName;
	String description;
	TaskLog(String fileName){
		this.fileName = fileName;
	}
	TaskLog(String fileName, String[] description){
		this.fileName = fileName;
		this.description = description.toString();
	}
	void writeLine(String line) {
		try {
			PrintWriter outFile = new PrintWriter(new FileWriter("tm.txt", true));
			outFile.println(line);
			outFile.close();
		}
		catch(Exception e) {
			System.out.println("Error with tm.txt file.");
		}
	}
	void writeLine(String line, String description) {
		try {
			PrintWriter outFile = new PrintWriter(new FileWriter("tm.txt", true));
			outFile.print(line);
			outFile.println(description);
			outFile.close();
		}
		catch(Exception e) {
			System.out.println("Error with tm.txt file.");
		}
	}
	LinkedList<TaskLogEntry> read() throws IOException{
		LinkedList<TaskLogEntry> entries = new LinkedList<>();
		BufferedReader Linein = new BufferedReader(new FileReader("tm.txt"));
		//String line;
		while (Linein.readLine() != null) {
			//line = in.readLine();
			entries.add(new TaskLogEntry(Linein.readLine()));
		}
        
		
		return entries;
		
	}
	
}

class TaskLogEntry{
	LocalDateTime timeStamp;
	String name;
	String command; 
	String data = "";
	TaskLogEntry(String line) {
	StringTokenizer stock = new StringTokenizer(line, "\t");
	timeStamp = LocalDateTime.parse(stock.nextToken());
	name = stock.nextToken();
	command = stock.nextToken();
	if (stock.countTokens() > 3) 
		data = stock.nextToken();
	}
	
}


class TaskDuration{
	private LocalDateTime start, stop;
	long elapsedSeconds;
	TaskDuration(LocalDateTime start,LocalDateTime stop) {
		this.start = start;
		this.stop = stop;
		//this.elapsedSeconds = elapsedSeconds(this.start, this.stop);
	}
	long elapsedSeconds(LocalDateTime start, LocalDateTime stop) {
		elapsedSeconds=ChronoUnit.SECONDS.between(start,stop);
		return elapsedSeconds;
	}
	
}
 
class Task{
	String name;
	String description;
	long elapsedSeconds;
	LinkedList<TaskDuration> durations;
	Task(String name, LinkedList<TaskLogEntry> entries) {
		LocalDateTime lastStart = null;
		this.name = name;
		durations = new LinkedList<TaskDuration>();
		for (TaskLogEntry entry : entries) {
			if (entry.name.equals(this.name)) {
					switch (entry.command){
					case "start" : 
						lastStart = entry.timeStamp;
					case "stop" :
						if (lastStart != null) {
							elapsedSeconds=ChronoUnit.SECONDS.between(lastStart, entry.timeStamp);
							lastStart = null;
						}
					case "describe" :
						this.description = entry.data;
						break;					
					}	
			}
		}
		
		
	}
	long addDuration(LocalDateTime lastStart, LocalDateTime stopTime) {
		elapsedSeconds=ChronoUnit.SECONDS.between(lastStart, stopTime);
		return elapsedSeconds;
	}
	
	public String toString() {
		String s = "";
		s = s + "Summary for task	: " + name + "\n";
		s = s + "Description 		: " + description + "\n";
		s = s + "Total Time on Task \t: " + elapsedSeconds + "\n";
		return s;
	}
}



class TimeStamp	
{
	public static LocalDateTime main() {
		//current date and time retrived using DateFormat and Calendar class
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		Calendar calobj = Calendar.getInstance();
		
		String ts = LocalDateTime.now().toString();
		LocalDateTime time = LocalDateTime.parse(ts);
		return (time);
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