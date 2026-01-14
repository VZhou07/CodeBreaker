/*
 *The HumanPlayer class takes care of guessing, giving clues, as well as input validation. 
 Implements the abstract methods declared in Player
*/
package CodeBreaker;
import java.io.*;
public class HumanPlayer extends Player{
	private boolean hintGiven; // indicates if a hint has been used by the player
	/**
	 * constructor initializes the human player with their role and name.
	 * @param boolean codeCreator, indicates if the player is the code creator.
	 * @param String name, the name of the player.
	 */
	public HumanPlayer(boolean codeCreator, String name) {
		this.codeCreator=codeCreator;
		this.hintGiven=false; //initialize attribute
		this.name=name;
	}
   /**
    * allows the player to make a guess and checks if the input is valid.
    * @return the validated guess.
    */
	public String makeGuess(){
		String guess="";
		boolean valid=false; //initialize valid as false
		while (!valid) {
			valid=false;
			System.out.println("Enter a guess of length " + CODELENGTH + " using the first letters of the colours green, red, blue, yellow, orange, purple in lowercase:");
			System.out.println("Example format: ggrb");
			if (AIPlayer.getStatus()) {
				System.out.println("Type hint to get a one time use hint for the code from the AI"); //if the AIPlayer is participating and true is returned for the static getStatus method, it is player vs ai so tell the user they can get a hint
			}
			else {
				System.out.println("");
			}
			try{
				guess=br.readLine(); //read their input
			}
			catch (IOException e){
				System.out.println(e.getLocalizedMessage()); //print error message
			}
			if (guess.length()!=CODELENGTH) {
				System.out.println("invalid code length"); //if the code length is not CODELENGTH, reprompt
				continue; //skip rest of iteration
			}
			valid=true; //set valid to true. If flow of control makes it past the enhanced for loop without valid being set back to false, the data is validated
			char charsArr[] = guess.toCharArray(); //turn String input into character array
			for (char i:charsArr) { //iterate the values through charsArr
				if (possibleColours.indexOf(i)==-1) { //if any one of the characters is not in the possibleColours string, input is not valid
					valid=false; //set valid to false
				}
			}
			if (!valid){ //if valid is false
				if (guess.equals("hint")&&AIPlayer.getStatus()){ //if the guess is equal to hint, and it is ai vs player
					hintGiven=AIPlayer.giveHint(hintGiven); //call static AIPlayer giveHint method
				}
				else {
					System.out.println("Invalid input. Use the colors r,g,b,y,o,p");
				}
			}
		}
		return guess;
	}
	/**
	 * getter method for name attribute.
	 * @return String name of the player.
	 */
	public String getName(){
		return name;
	}
   /**
    * allows the player to give a clue for a guess and validates the input.
    * @return generated clue String.
    */
	public  String giveClue(){
		//initialize local variables
		boolean valid=false;
		int numBlack=0,numWhite=0;
		String input="";
		String clue="";
		while (!valid) { //while input is not valid
			valid=false;
			System.out.println("Give your hint. Indicate how many white and  black pegs");
			System.out.println("Format: b w (e.g., '2 1' for 2 black and 1 white peg)");
			try{
				input=br.readLine();
			}
			catch (IOException e){
				System.out.println("IO Exception encountered");
			}
			String[] numBlackWhitePegs = input.split(" "); //split the input into 2 arrays, one array should have a number that is type String if the user entered correct input based on the prompt
			if (numBlackWhitePegs.length!=2) { //if the array length is more than 2, invalid input
				System.out.println("Error incorrect format. Format: b w (e.g., '2 1' for 2 black and 1 white peg)");
				continue; //skip rest of iteration
			}
			try { //store the number of black and number of white pegs in integer variables
				numBlack=Integer.parseInt(numBlackWhitePegs[0]);
				numWhite=Integer.parseInt(numBlackWhitePegs[1]);
				if ((numBlack+numWhite)>CODELENGTH){ //number of white and black pegs must be less or equal than 4
					System.out.println("Error there are only 4 pegs");
					continue; //skip rest of iteration
				}
				if (numBlack<0||numWhite<0) { //pegs cannot be negative
					System.out.println("Number of pegs cannot be negative");
					continue; //skip rest of iteration
				}
			}
			catch (NumberFormatException e) { //exception occurs if the player did not enter integers
				System.out.println("Please enter integers");
				continue;
			}
			valid=true; //valid input if flow of control makes it to the end of the while loop
		}
		for (int i=0;i<numBlack;i++) {
			clue+="b"; //concatenate black first
		}
		for (int i=0;i<numWhite;i++) {
			clue+="w"; //concatenate white second
		}
		while (clue.length()<4) { //fill empty indexes up to length 4 with "e"
			clue+="e";
		}
		return clue;
	}
   /**
    * allows the player to input the code they created after the game is finished.
    */
	public void inputCode(){
		boolean valid=false;
		String input="";
		while (!valid) { //while the input is invalid
			//user friendly prompts
			System.out.println("Create code in format ");
			System.out.println("Enter a guess of length " + CODELENGTH + " using the first letters of the colours green, red, blue, yellow, orange, purple in lowercase:");
			System.out.println("Example format: ggrb");
			try{
				input=br.readLine();
			}
			catch (IOException e){
				System.out.println(e.getLocalizedMessage()); //print error message
			}
			if (input.length()!=CODELENGTH) { //if the code is not the same length as the code length, invalid input
				System.out.println("Invalid code length");
				continue; //skip rest of iteration
			}
			valid=true; //set valid to true. If flow of control makes it past the enhanced for loop without valid being set back to false, the data is validated
			char charsArr[] = input.toCharArray(); //turn input into character array
			for (char i:charsArr) {  //iterate through values of charsArr
				if (possibleColours.indexOf(i)==-1) { //if any one of the characters is not in the possibleColours string, input is not valid
					valid=false; //invalid input
				}
			}
		}
		code=input; //set the code as the input
	}
	/**
	 * mandatory implementation of abstract makeCode method. Tells the player to think of a code in their head
	 */
	public void makeCode() {
		System.out.println("Think of a code of length 4 in your head using the colors g, r, b, y, o, p");
	}
}