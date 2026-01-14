/*
 * Author: Vincent Zhou
 * File Name: MainCodeBreaker.java
 * Main code to be run.
 */
package CodeBreaker;
import java.io.*;
import java.util.*;
public class MainCodeBreaker {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	/**
	 * Main method that the code runs on.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[])throws IOException{
		//initialize variables 
		String option="d",name, mode="b", friendName = "", code="";
		String playAgainOption;
		String mistakeClueCode="";
		boolean humanIsCodeCreator=false;
		Player players[] = new Player[2]; //stores the two players in a array of type Player to allow for easy access to the code creator and guesser without having to use many if statements
		String leaderboardFile=""; //stores the file name/location of the leaderboard
		int numGuesses=0;
		final int GUESSERINDEX=0; //index of the guesser stored in an array of type Player
		final int CODECREATORINDEX=1; //index of the code creator stored in an array of type Player
		boolean won=false, gameFinished=false, playAgain=true, valid=false, playerVsPlayer=true, playerVsAI=false;
		//outputs the rules of the game to the user at the start
		while (playAgain) { //while the user wants to play again.
			AIPlayer aIP = new AIPlayer(true,"hard",false); //arbitrary instantiation
			System.out.println("Welcome to CodeBreaker. What is your name?"); 
			name=br.readLine();
			System.out.println("Would you like to see the Leaderboard? \nType el to see the easy leaderboard, ml to see the medium leaderboard, or hl to see the hard leaderboard. Type anything else if you wish to not see the leaderboard");
			option=br.readLine();
			if (option.equals("el")) { //if the player wants to see the easy leaderboard
				displayLeaderboard("Easy_Leaderboard.txt"); //call displayLeaderboard method passing the file name of the easy leaderboard
			}
			else if (option.equals("ml")) {//if the player wants to see the medium leaderboard
				displayLeaderboard("Medium_Leaderboard.txt");//call displayLeaderboard method passing the file name of the medium leaderboard
			}
			else if(option.equals("hl")) { //if the player wants to see the hard leaderboard
				displayLeaderboard("Hard_Leaderboard.txt"); //call displayLeaderboard method passing the file name of the hard leaderboard
			}
			option="";
			//ask if player wants to play against ai or friend
			while (!option.equals("ai")&&!option.equals("f")){ //input validation while loop
				System.out.println("Enter f to play against your friend and ai to play against ai");
				option=br.readLine();
				//set boolean values which indicate what gamemode player want
				if (option.equals("f")){
					playerVsPlayer=true;
					playerVsAI=false;
				}
				else if (option.equals("ai")){
					playerVsAI=true;
					playerVsPlayer=false;
				}
				else{ //tell player to reprompt if the input is not f or ai
					System.out.println("Invalid input. Enter f to play against your friend and ai to play against ai");
				}
			} 
			if (option.equals("f")){ //ask player to enter their friend's name so we can assign the name to the attribute of HumanPlayer object
				System.out.println("Enter your friend's name");
				friendName=br.readLine();
			}
			option="";
			if (playerVsAI){ //if the player wants to play against the ai, enter this selection statement
				System.out.println(name+", would you like to be the code creator or the guesser? Type g for guesser or c for code creator");
				while (!option.equals("g")&&!option.equals("c")){ // code validation
					option=br.readLine();
					if (option.equals("g")){
						humanIsCodeCreator=false;
					}
					else if (option.equals("c")){
						humanIsCodeCreator=true;
					}
					else{ //tell player input is invalid if the input is not c or g
						System.out.println("Invalid input. Please enter c for code maker or g for guesser.");
					}
				}
				System.out.println(name+", would you like the ai to be on easy mode, medium mode, or hard mode?");
				while (!mode.equals("easy")&&!mode.equals("medium")&&!mode.equals("hard")){ //input validation
					System.out.println("For easy mode enter easy");
					System.out.println("For medium mode enter medium");
					System.out.println("For hard mode enter hard");
					mode=br.readLine();
					if (!mode.equals("easy")&&!mode.equals("medium")&&!mode.equals("hard")) { //if the input is not any of the modes, tell tell them the input is invalid
						System.out.println("Invalid input");
					}
				}
				aIP = new AIPlayer(!humanIsCodeCreator,mode,true); //redeclare aIP based on player preferences 
				HumanPlayer hP = new HumanPlayer(humanIsCodeCreator,name); //declare hP object, passing the player's name and whether or not they are the code creator
				//assign the objects into the correct indexes in the players array
				if (humanIsCodeCreator) {
					players[CODECREATORINDEX]=hP;
					players[GUESSERINDEX]=aIP;
				}
				else {
					players[CODECREATORINDEX]=aIP;
					players[GUESSERINDEX]=hP;
				}
			}
			else{ //if it is not player vs ai, then it is player vs player
				playerVsPlayer=true; //set playerVsPlayer boolean to true
			}
			Board b = new Board(); //create board object b
			while (!won&&!gameFinished&&playerVsPlayer){ //while the game has not finished and it is pvp
				if (b.getGuesses().isEmpty()) { //if it is the first guess, we need to assign roles to the two human players
					//human player object instantiation
					HumanPlayer hP2 = new HumanPlayer(false, name); 
					HumanPlayer hP3 = new HumanPlayer(false,friendName);
					//assign the players the role of code creator or guesser randomly
					int randInt=(int)(Math.random()*2);
					players[randInt]=hP2;
					players[players.length-1-randInt]=hP3;
					//display to the players who is the guesser and who is the code creator
					System.out.println(players[CODECREATORINDEX].getName()+" will be the code creator");
					System.out.println(players[GUESSERINDEX].getName()+" will be the guesser");
					System.out.print(players[CODECREATORINDEX].getName()+" ");
					players[CODECREATORINDEX].makeCode();
					System.out.println("Once you have thought of a code, enter anything to continue");
					br.readLine();
				}
				System.out.print(players[GUESSERINDEX].getName()+" ");
				b.addGuess(players[GUESSERINDEX].makeGuess()); //allow guesser to make a guess
				b.displayBoard(); //display the board
				System.out.print(players[CODECREATORINDEX].getName()+" please give the red and black peg clue based on the guess. "); 
				b.addClue(players[CODECREATORINDEX].giveClue()); //allow code creator to give the clue
				b.displayBoard(); //display the board
				//check if the game has finished(lose or win)
				won=b.checkWin();
				gameFinished=b.checkLost(won);
				numGuesses++; //add one to guesses counter
			}
			while (!won&&!gameFinished&&playerVsAI) { //if the game is not finished, and it is player vs ai
				if (b.getGuesses().isEmpty()) { //if it is the first guess
					players[CODECREATORINDEX].makeCode(); //get the code creator to make the code
				}
				b.addGuess(players[GUESSERINDEX].makeGuess()); //allow guesser to guess code
				if (b.getGuesses().get(b.getGuesses().size()-1).equals("error")) //if error is returned by the AI makeCode method
					gameFinished=true; //game is finished
				if (!gameFinished) { //if the game is not finished, continue flow of control
					if (humanIsCodeCreator) { 
						b.displayBoard(); //display the board again if human is the code creator
					}
					b.addClue(players[CODECREATORINDEX].giveClue()); //allow the code creator to give a clue
					if (!humanIsCodeCreator) { //if human is not the code creator
						numGuesses++; //add one to guesses counter
						b.displayBoard(); //display the board
						aIP.filter(); //filter the possible permutations 
					}
					//check if the game is finished
					won=b.checkWin();
					gameFinished=b.checkLost(won);
				}
	
			}
			if (playerVsAI){ //if it was player vs ai
				if (mode.equals("hard")) {
					leaderboardFile="Hard_Leaderboard.txt"; //assign the file of the leaderboard to the hard file name location
				}
				else if (mode.equals("medium")) { 
					leaderboardFile="Medium_Leaderboard.txt"; //assign the file of the leaderboard to the medium file name location
				}
				else if (mode.equals("easy")) {
					leaderboardFile="Easy_Leaderboard.txt"; //assign the file of the leaderboard to the easy file name location
				}
				if (!humanIsCodeCreator) {
					checkLeaderboard(leaderboardFile,mode,name,numGuesses);
				}
				if (b.getGuesses().get(b.getGuesses().size()-1).equals("error")) { //if there was an error and error was returned
					while (!valid) {
						valid=false;
						System.out.println("You made a mistake in giving the clues.");
						System.out.println("Please enter your code to identify your mistake");
						try{
							mistakeClueCode=br.readLine();
						}
						catch (IOException e){
							System.out.println(e.getLocalizedMessage());
						}
						if (mistakeClueCode.length()!=AIPlayer.CODELENGTH) {
							System.out.println("invalid code length");
							continue;
						}
						valid=true;
						char charsArr[] = mistakeClueCode.toCharArray();
						for (char i:charsArr) {
							if (aIP.possibleColours.indexOf(i)==-1) {
								valid=false;
							}
						}
						if (!valid){
							System.out.println("Invalid input. Use the colors r,g,b,y,o,p");
						}
					}
					b.removeGuess("error");
					aIP.identifyMistake(mistakeClueCode); //call the identifyMistakes method to show the user which clue they gave was wrong
				}
				//display messages based on if the user won/lost and if they were the code creator/gueser
				else if (!won&&!humanIsCodeCreator) {
					System.out.println("You lost. You could not guess the AI's code. It was:");
					System.out.println(aIP.getCode());
				}
				else if (!won&&humanIsCodeCreator) {
					b.displayBoard();
					System.out.println("You won! The AI could not guess your code");
				}
				else if (won&&humanIsCodeCreator) {
					System.out.println("You lost. The AI guessed your code");
				}
				else {
					System.out.println("You won, you successfully guessed the AI's code");
				}
			}
			else{ //for pvp
				if (won){ //display who won and who lost
					System.out.println(players[GUESSERINDEX].getName()+" won in "+ numGuesses+" guesses");
					playerVsPlayer=false;
					playerVsAI=true;
				}
				else{ //ask for the secret code
					System.out.println(players[CODECREATORINDEX].getName()+", what was the secret code?");
					code=br.readLine();
					valid=true;
					char codeArr[] = code.toCharArray();
					for (char i:codeArr) {
						if (aIP.possibleColours.indexOf(i)==-1) {
							valid=false;
						}
					}
					aIP.identifyMistake(code); //check if the clues given was correct
					if (!valid||code.length()!=AIPlayer.CODELENGTH){ //if the code created is invalid, disqualify code creator
						System.out.println(players[CODECREATORINDEX].getName()+", you are disqualified and lose because that is an invalid code.");
						System.out.println(players[GUESSERINDEX].getName()+" wins.");
					}
					else if (b.getClues().get(b.getClues().size()-1).equals("all correct")) { //if all the clues given were correct, tell the code creator they win
						System.out.println(players[CODECREATORINDEX].getName()+" won as "+players[GUESSERINDEX].getName()+" could not guess the code");
					}
					else { //if the clues given were wrong, tell the code creator they lose
						System.out.println(players[CODECREATORINDEX].getName()+" gave the wrong clue so "+players[GUESSERINDEX].getName()+" wins.");
					}
				}
			}
			
			
			
			System.out.println("Do you wish to play again? Type y to play again, type anything else to exit the program");
			playAgainOption=br.readLine();
			if (playAgainOption.toLowerCase().equals("y")) { //if the user wants to play again, reset variables
				playAgain=true;
				b.playAgain();
				gameFinished=false;
				won=false;
				option="d";
				mode="b";
				numGuesses=0;
				valid=false;
			}
			else {
				playAgain=false;
			}
		
		
		}
	}
	/**
	 * Checks if the user made it onto the leaderboard.
	 * @param String file, file location
	 * @param String mode, mode of the ai
	 * @param String name, name of player
	 * @param int guesses, number of guesses it took to guess the code
	 * @throws IOException
	 */
	public static void checkLeaderboard(String file, String mode, String name, int guesses)throws IOException {
		ArrayList<Integer> guessCount = new ArrayList<>();
		ArrayList<String> names = new ArrayList<>();
		String line;
		BufferedReader br2 = new BufferedReader(new FileReader(file));
		line=br2.readLine();
		FileWriter fw = new FileWriter(file,false);
		BufferedWriter bw = new BufferedWriter(fw);
		guessCount.add(guesses);
		names.add(name);
		line=br2.readLine(); //read from file
		while (line!=null) {
			String lineSplit[]=line.split(":");
			names.add(lineSplit[0]);
			if (lineSplit[1].length()>9) { //if the leaderboard score has a length of greator than 1, it has to be 10 
				guessCount.add(10);
			}
			else {
				int num=(int)(lineSplit[1].charAt(0)-'0'); //convert the character to a int
				guessCount.add(num); //add the num to the guessCount
			}
			line=br2.readLine();
		}
		//insertion sort 
		for (int i=1;i<guessCount.size();i++) {
			int curValue=guessCount.get(i);
			String curName=names.get(i);
			int j=i-1;
			while (j>=0&&guessCount.get(j)>curValue) {
				guessCount.set(j+1, guessCount.get(j));
				names.set(j+1, names.get(j));
				j--;
			}
			guessCount.set(j+1, curValue);
			names.set(j+1, curName);
		}
		bw.write(mode+" mode leaderboard\n");
		if (guessCount.size()==6) {
			guessCount.remove(5); //remove the 6th player from leaderboard
			names.remove(5);
		}
		for (int i=0;i<guessCount.size();i++) {
			bw.write(names.get(i)+":"+guessCount.get(i)+" guesses\n"); //write onto file
		}
			
		
		bw.close();
		br2.close();
	}
	/**
	 * Displays the leaderboard onto the screen
	 * @param String location, name of the file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void displayLeaderboard(String location) throws FileNotFoundException, IOException{
		BufferedReader br2 = new BufferedReader(new FileReader(location));
		String line;
		line=br2.readLine();
		//read from file
		if (line==null) {
			System.out.println("The leaderboard is empty");
		}
		while (line!=null) {
			System.out.println(line);
			line=br2.readLine();
		}
		br2.close();
	}
}