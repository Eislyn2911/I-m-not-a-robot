package com.eislyn.utilcord.app;

/**
 * The main class of IAmNotARobot discord bot that starts the application.
 * @author Eislyn
 * 15/10/2022
 */
public class IAmNotARobot {
	/**
	 * Starts the application by setting up, shuts down jda with q, do not shut down using stop button in console.
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		JDASetup jdaSetup = JDASetup.getInstance();
		jdaSetup.setupJDA();
		
/**
 * Commented out for hosting to avoid NoSuchElementException error.
 */
//		JDA jda = jdaSetup.getJda();
//		Scanner scanner;
//		String input=null;
//		
//		do {
//			System.out.print("Type q to stop appplication: ");
//			scanner = new Scanner(System.in);
//			input = scanner.next();
//			
//			if(input.equals("q")) {
//				System.out.println("Application is quitting...");
//				jda.shutdownNow();
//			}
//		}while(input.equals("q")==false);
//		
//		scanner.close();
//		return;	
	}

}
