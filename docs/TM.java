  /* Nicholas King, CSC 131-03, Assignment 2
 * This program uses command line arguments to log start and stop times of particular task and saves the information in a text file title "tm.txt"
 * List of acceptable command line arguments can be found in TM.md provided
 */
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.io.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.Line;

import java.time.*;

public class TM implements ITMModel{
	String name = "";
	String description = "";
	String size = "";
	String oldName = "";
	String newName = "";
	public boolean startTask(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stopTask(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean describeTask(String name, String description) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sizeTask(String name, String size) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteTask(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean renameTask(String oldName, String newName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String taskElapsedTime(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String taskSize(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String taskDescription(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String minTimeForSize(String size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String maxTimeForSize(String size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String avgTimeForSize(String size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> taskNamesForSize(String size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String elapsedTimeForAllTasks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> taskNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> taskSizes() {
		// TODO Auto-generated method stub
		return null;
	}
	public TM(String name, String description, String size, String oldName, String newName) {
		this.name = name;
		this.description = description;
		this.size = size;
		this.oldName = oldName;
		this.newName = newName;
	}
	
	public static void main(String[] args) throws IOException
	{
		String fileName = "tm.txt";
		TaskLog log = new TaskLog(fileName);
		
		LinkedList<TaskLogEntry> entries = new LinkedList<TaskLogEntry>();
		switch(args[0]) {
		case "start" : log.writeLine(LocalDateTime.now() + "\t" + args[1] + "\t" + "start");
			break;
		case "stop" :  log.writeLine(LocalDateTime.now() + "\t" + args[1] + "\t" + "stop");
			break;
		case "describe" : 
			String[] taskDescription = Arrays.copyOfRange(args, 2, args.length);
			if (args[3] == null)
				log.writeLine(LocalDateTime.now() + "\t" + args[1] + "\t" + "describe" + "\t" + args[2]);
				log.writeLine(LocalDateTime.now() + "\t" + args[1] + "\t" + "describe" + "\t" + args[2] + "\t" + args[3]);
			break;
		case "size" :
			log.writeLine(LocalDateTime.now() + "\t" + args[1] + "\t" + "size" + "\t" + args[2] + "\t");
			break;
		case "summary" :
			entries = log.read(); 
			if (args.length > 1) {
				Task task = new Task(args[1], entries);
				System.out.print(task);
			}
			else
			{
				System.out.println(summary(entries));
			}
		case "rename" : cmdRename(args);
			break;
		case "delete" : cmdRename(args);
			break;
		}
	}
	static StringBuilder summary(LinkedList<TaskLogEntry> entries) {
		TreeSet<String> taskNames = new TreeSet<String>();	
		TreeSet<String> smallTasks = new TreeSet<String>();	
		TreeSet<String> mediumTasks = new TreeSet<String>();	
		TreeSet<String> largeTasks = new TreeSet<String>();	
		long totalSecondsForAllTasks = 0;
		long totalSecondsOnTask = 0;
		int sCount = 0, mCount = 0, lCount = 0;
		long sMin, sMax, sAvg, mMin = 0, mMax, mAvg, lMin, lMax, lAvg;
		StringBuilder summaryText = new StringBuilder();
		for (TaskLogEntry entry : entries) {
			if (!entry.name.equals("DELETED")) {
				taskNames.add(entry.name);
			}
		} 
		for (String taskName : taskNames) {
			Task task = new Task(taskName, entries);
			totalSecondsForAllTasks += task.elapsedSeconds;
			if (task.size.equals("S")) {
				smallTasks.add(task.name);
				sCount++;
			}
			if (task.size.equals("M")) {
				mediumTasks.add(task.name);
				mCount++;
			}
			if (task.size.equals("L")) {
				largeTasks.add(task.name);
				lCount++;
			}
			summaryText.append(task + "\n");
		}
		summaryText.append("Total time spent on all tasks = " + toHoursMinutesSeconds(totalSecondsForAllTasks));
		if (sCount >= 2) {
			taskStats(smallTasks, "S", entries);
		}
		if (mCount >= 2) {
			taskStats(mediumTasks, "M", entries);
		}
		if (lCount >= 2) {
			taskStats(largeTasks, "L", entries);
		}
		
		return summaryText;
	}
	static String toHoursMinutesSeconds(long totalSeconds) {
		long hours = TimeUnit.SECONDS.toHours(totalSeconds);
		long minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) - (TimeUnit.SECONDS.toHours(totalSeconds) * 60);
		long seconds = TimeUnit.SECONDS.toSeconds(totalSeconds) - (TimeUnit.SECONDS.toMinutes(totalSeconds) *60);
		String s = (Long.toString(hours) + ":" + Long.toString(minutes) + ":" + Long.toString(seconds));
		return s;
	}

	static void taskStats(TreeSet<String> tasks, String size, LinkedList<TaskLogEntry> entries){
		TreeSet<String> taskNames = new TreeSet<String>();
		long totalTime;
		long min = 999999999;
		long max = 0;
		long avg = 0;
		Task minTask;
		Task maxTask;
		for (TaskLogEntry entry : entries)
			taskNames.add(entry.name);
		for (String taskName : tasks) {
			Task task = new Task(taskName, entries);
			if (task.elapsedSeconds < min)
				min = task.elapsedSeconds;
			if (task.elapsedSeconds > max) 
				max = task.elapsedSeconds;
		}
		avg = (min + max) /2;
		
		String s = "";
		s += "Minimum time on "+size+" task	: " + toHoursMinutesSeconds(min) + "\n";
		s += "Maximum time on "+size+" task	: " + toHoursMinutesSeconds(max) + "\n";
		s += "Average time on "+size+" task	: " + toHoursMinutesSeconds(avg) + "\n";
		s += "\n";
		
		System.out.print(s);
		
	}
	private static void cmdRename(String args[]) {
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
	    File fileToBeModified = new File("tm.txt");
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
	             String newContent = oldContent.replaceAll(oldString, "DELETED");             
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


class TaskStats{
	TaskStats(TreeSet<String> tasks, LinkedList<TaskLogEntry> entries){
		TreeSet<String> taskNames = new TreeSet<String>();
		long totalTime;
		long min = 999999999;
		long max = 0;
		long avg = 0;
		Task minTask;
		Task maxTask;
		for (TaskLogEntry entry : entries)
			taskNames.add(entry.name);
		for (String taskName : tasks) {
			Task task = new Task(taskName, entries);
			if (task.elapsedSeconds < min)
				min = task.elapsedSeconds;
			if (task.elapsedSeconds > max) 
				max = task.elapsedSeconds;
		}
		avg = (min + max) /2;
	}
}


class TaskLog{
	String fileName;
	TaskLog(String fileName){
		this.fileName = fileName;
	}
	void writeLine(String line) {
		try {
			PrintWriter outFile = new PrintWriter(new FileWriter(fileName, true));
			outFile.println(line);
			outFile.close();
		}
		catch(Exception e) {
			System.out.println("Error with tm.txt file.");
		}
	}
	/*void writeLine(String line, String[] description) {
		try {
			
			list.add(line);
			for (int i = 0; i < description.length; i++){
				list.add(description[i]);
			}
			List<String> list = new ArrayList<>(Arrays.asList(description));
			String formattedString = list.toString().replace(",", "").replace("[", "").replace("]", "").trim();          
			PrintWriter outFile = new PrintWriter(new FileWriter(fileName, true));
			outFile.println(form.format("%s \t %s", line, formattedString));
			outFile.close();
		}
		catch(Exception e) {
			System.out.println("Error with tm.txt file.");
		}
	}*/
	
	LinkedList<TaskLogEntry> read() throws IOException{
		LinkedList<TaskLogEntry> entries = new LinkedList<TaskLogEntry>();
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String line = in.readLine();
		while (line != null) {
			entries.add(new TaskLogEntry(line));
			line = in.readLine();
		}
		in.close();
		return entries;
		
	}
	
}

class TaskLogEntry{
	LocalDateTime timeStamp;
	String name;
	String command;
	String size;
	String data;
	TaskLogEntry(String line) {
	StringTokenizer stock = new StringTokenizer(line, "\t");
	timeStamp = LocalDateTime.parse(stock.nextToken());
	name = stock.nextToken();
	command = stock.nextToken();
	while (stock.hasMoreTokens()) { 
		if (command.equals("size")) {
			size = stock.nextToken();
			data = "";
		}
		else if (command.equals("describe")){
			data = stock.nextToken();
			if (stock.hasMoreTokens())
				size = stock.nextToken();
			}
		
		}
	}
}


class TaskDuration{
	LocalDateTime start, stop;
	long elapsedSeconds;
	TaskDuration(LocalDateTime start,LocalDateTime stop) {
		this.start = start;
		this.stop = stop;
		this.elapsedSeconds = elapsedSeconds(this.start, this.stop);
	}
	long elapsedSeconds(LocalDateTime start, LocalDateTime stop) {
		elapsedSeconds=ChronoUnit.SECONDS.between(start,stop);
		return elapsedSeconds;
	}
	
}
 
class Task{
	String name;
	String description = "";
	String size = "";
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
						break;
					case "stop" :
						if (lastStart != null) 
							addDuration(lastStart, entry.timeStamp);
						lastStart = null;
						break;
					case "describe" :
						if (entry.data != null)
							description += entry.data + " / ";
						size = entry.size;
						break;		
					case "size" :
						size = entry.size;
						break;
					}	
			}
		}	
	}
	void addDuration(LocalDateTime lastStart, LocalDateTime stopTime) {
		this.elapsedSeconds += ChronoUnit.SECONDS.between(lastStart, stopTime);
		durations.add(new TaskDuration (lastStart, stopTime));
	}
	
	public String toString() {
		String s = "";
		s = s + "Summary for task	: " + name + "\n";
		s = s + "Description 		: " + this.description + "\n";
		s = s + "Size of task 		: " + size + "\n";
		s = s + "Total Time on Task \t: " + TimeUtil.elapsedTime(elapsedSeconds) + "\n";
		return s;
	}
}

class TimeUtil{
	static long totalSeconds;
	TimeUtil(long totalSeconds){
		this.totalSeconds = totalSeconds;
	}
	static String elapsedTime(long totalSeconds) {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		long hours = TimeUnit.SECONDS.toHours(totalSeconds);
		long minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) - (TimeUnit.SECONDS.toHours(totalSeconds) * 60);
		long seconds = TimeUnit.SECONDS.toSeconds(totalSeconds) - (TimeUnit.SECONDS.toMinutes(totalSeconds) *60);
		String s = (Long.toString(hours) + ":" + Long.toString(minutes) + ":" + Long.toString(seconds));
		return s;
	}
}