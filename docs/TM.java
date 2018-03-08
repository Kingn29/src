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

public class TM
{
	
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
			log.writeLine(LocalDateTime.now() + "\t" + args[1] + "\t" + "describe" + "\t" + args[2]);
			break;
		case "size" :
			log.writeLine(LocalDateTime.now() + "\t" + args[1] + "\t" + "size" + "\t" + args[2]);
			break;
		case "summary" :
			entries = log.read(); 
			if (args.length > 1) {
				System.out.print(new Task(args[1], entries));
			}
			else
			{
				System.out.println(summary(entries));
			}
		}
	}


	static StringBuilder summary(LinkedList<TaskLogEntry> entries) {
		TreeSet<String> taskNames = new TreeSet<String>();	
		long totalSecondsForAllTasks = 0;
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
		long hours = TimeUnit.SECONDS.toHours(totalSeconds);
		long minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) - (TimeUnit.SECONDS.toHours(totalSeconds) * 60);
		long seconds = TimeUnit.SECONDS.toSeconds(totalSeconds) - (TimeUnit.SECONDS.toMinutes(totalSeconds) *60);
		String s = (Long.toString(hours) + ":" + Long.toString(minutes) + ":" + Long.toString(seconds));
		return s;
	}
	
	

}


class TaskLog{
	String fileName;
	
	//ArrayList<String> list = new ArrayList<String>();
	//Formatter form = new Formatter();
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
		if (command.equals(size)) {
			size = stock.nextToken();
		}
		else {
			data = stock.nextToken();
			if (stock.nextToken() != null)
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
	String description;
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
						description = entry.data;
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