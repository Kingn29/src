/* Nicholas King
 * CSC 131 Section 05
 * This program prints the nth prime where n is a number between 1 and 100
 */

import java.util.InputMismatchException;
import java.util.Scanner;
public class nthPrime 
{
	public static void main(String[] args)
	{	
		intro();
		boolean loop = true;
		inputDirectory(loop);
		System.out.println("Goodbye!");
	}

	public static void intro()													//Prints Intro message
	{	System.out.println();
		System.out.println("                   The Nth prime                   ");
		System.out.println("-------------------------------------------------------");
		System.out.println("   Enter \"123\" to learn how to operate the program.");
	}
	
	public static void inputDirectory(boolean loop)								
	{
		int input;
		Scanner scan = new Scanner(System.in);
	    while(loop)				
		{
			System.out.println("   Type a number between 1 and 100 and press enter."); 
			try 																//input validation 
			{
				input = scan.nextInt();
				choice(input);	
				
			}
			catch (InputMismatchException e) 
			{
				System.out.println("                   Invalid input");
				inputDirectory(true);	
			}
			
	    }
	}
	public static void choice(int input)										//uses user input to go to whichever method is needed
	{
		if (input == 123)
		{
			information(input);
		}
		else if (input == -1)
		{
			System.out.println("The Program has ended.");
			System.exit(0);
		}
		else if (input > 0 && input < 101 && input != -1)
		{
			
			System.out.println(print(input));
			inputDirectory(true);
		}
		else if (input < 0 || input > 100 && input != 123 && input != -1)		//any unacceptable int type input brings user back to input prompt
			inputDirectory(true);
		else if (input == 0)
			printAll();
	}
	
	public static void information(int input)											//prints instructions for the program
	{
		System.out.println("                    Information");
		System.out.println("--------------------------------------------------------");
		System.out.println("This program prints the Nth prime if any number N from");
		System.out.println("1 to 100 is provided. When \"0\" is entered, The first");
		System.out.println("100 primes are printed, separated by commas. Entering");
		System.out.println("\"-1\" will end the program and print a goodbye message.");
		System.out.println("--------------------------------------------------------");
		inputDirectory(true);
	}
	
	public static void printAll()												//Prints the first 100 prime numbers
	{
		System.out.println("             THE FIRST 100 PRIME NUMBERS");
		System.out.println("-------------------------------------------------------");
		for (int nthTerm = 2; nthTerm < 542; nthTerm++)
		{
			if (isPrime(nthTerm))
			{
				if (nthTerm == 541)
					System.out.println(nthTerm);
				else
					System.out.print(nthTerm + ", ");
			}
			if (nthTerm == 47 || nthTerm == 107 || nthTerm == 167 || nthTerm == 229 || nthTerm == 283 || nthTerm == 359 || nthTerm == 431 || nthTerm == 491)
					System.out.println();	
		}
		System.out.println("-------------------------------------------------------");
		inputDirectory(true);
	}
	
	public static int print(int a)					//finds and prints the nth prime number where n is a number supplied by the user
	{											
		int primeCount = 0;
		int nthTerm = 1;
		while (nthTerm < 542)
		{
			nthTerm++;
			if (isPrime(nthTerm))
			{
				primeCount++;
				if (primeCount == a)
				{
					System.out.print("      The prime number at that position is: ");
					return nthTerm;
				}
			}
			
		}
		System.out.print("The prime number at that position is: ");
		System.out.println();
		return nthTerm;
	}
	
	public static boolean isPrime(int nthTerm)				//this method finds out if a number is not prime using the idea that 
	{														//every non prime number is divisible by a combination of prime numbers.
		if (nthTerm % 2 == 0)								//This method works for the first 100 prime numbers.
		{
			if (nthTerm == 2 && nthTerm <= 2)
				return true;
			return false;
		}
		else if (nthTerm % 3 == 0)
		{
			if (nthTerm == 3 && nthTerm <= 3)
				return true;
			return false;
		}
		else if (nthTerm % 5 == 0)
		{
			if (nthTerm == 5 && nthTerm <= 5)
				return true;
			return false;
		}
		else if (nthTerm % 7 == 0)
		{
			if (nthTerm == 7 && nthTerm <= 7)
				return true;
			return false;
		}
		else if (nthTerm % 11 == 0)
		{
			if (nthTerm == 11 && nthTerm <= 11)
				return true;
			return false;
		}
		else if (nthTerm % 13 == 0)
		{
			if (nthTerm == 13 && nthTerm <= 13)
				return true;
			return false;
		}
		else if (nthTerm % 17 == 0)
		{
			if (nthTerm == 17 && nthTerm <= 17)
				return true;
			return false;
		}
		else if (nthTerm % 19 == 0)
		{
			if (nthTerm == 19 && nthTerm <= 19)
				return true;
			return false;
		}
		else if (nthTerm % 23 == 0)
		{
			if (nthTerm == 23 && nthTerm <= 23)
				return true;
			return false;
		}
			return true;
	}
	
}
