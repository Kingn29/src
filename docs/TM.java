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
		case "rename" : rename(file, args);
			break;
		default : System.out.println("Unreadable Input");
			break;
		}	
	}
	private static void cmdStart(WriteFile file, String[] args) throws IOException {
		String ts = LocalDateTime.now().toString();
		LocalDateTime start = LocalDateTime.parse(ts);
		saveData(ts, file, args);
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
	private static void rename(WriteFile file, String args[]) {
		Scanner sc = new Scanner(System.in);
		String oldText = "";
		System.out.print("Task name to be replaced: ");
		oldText = sc.nextLine();
		System.out.print("New name for the task: ");
		String newText = "";
		newText = sc.nextLine();
		cmdRename("tm.txt", oldText, newText);
		
	}
	//code adaoted from http://javaconceptoftheday.com/modify-replace-specific-string-in-text-file-in-java/
	//on 2/22/18
	static void cmdRename(String filePath, String oldString, String newString)
    {
        File fileToBeModified = new File("tm.txt");
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;
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
}

class TaskDuration{
	private TimeStamp start, stop;
	public void TaskDuration(TimeStamp start,TimeStamp stop) {
		this.start = start;
		this.stop = stop;
	}
	
}

class Task{
	String name;
	String description;
	LinkedList<TaskDuration> durations;
	java.util.Date startTime;
	Timestamp stopTime;
	Timestamp timeSpent;
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
				case "stop" :{
					if (lastStart != null)
						stopTime = TimeStamp.main();
				}
					
				}
			}
	 	
		}
	}
	public static Timestamp diff (java.util.Date t1, java.util.Date t2)
	{
	    // Make sure the result is always > 0
	    if (t1.compareTo (t2) < 0)
	    {
	        java.util.Date tmp = t1;
	        t1 = t2;
	        t2 = tmp;
	    }

	    // Timestamps mix milli and nanoseconds in the API, so we have to separate the two
	    long diffSeconds = (t1.getTime () / 1000) - (t2.getTime () / 1000);
	    // For normals dates, we have millisecond precision
	    int nano1 = ((int) t1.getTime () % 1000) * 1000000;
	    // If the parameter is a Timestamp, we have additional precision in nanoseconds
	    if (t1 instanceof Timestamp)
	        nano1 = ((Timestamp)t1).getNanos ();
	    int nano2 = ((int) t2.getTime () % 1000) * 1000000;
	    if (t2 instanceof Timestamp)
	        nano2 = ((Timestamp)t2).getNanos ();

	    int diffNanos = nano1 - nano2;
	    if (diffNanos < 0)
	    {
	        // Borrow one second
	        diffSeconds --;
	        diffNanos += 1000000000;
	    }

	    // mix nanos and millis again
	    Timestamp result = new Timestamp ((diffSeconds * 1000) + (diffNanos / 1000000));
	    // setNanos() with a value of in the millisecond range doesn't affect the value of the time field
	    // while milliseconds in the time field will modify nanos! Damn, this API is a *mess*
	    result.setNanos (diffNanos);
	    return result;
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