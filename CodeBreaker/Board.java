/*
 * The Board class represents the game board for the CodeBreaker game.
 * It maintains the guesses and clues made during the game and provides methods
 * to display the game/board and change the attributes and class variables.
 */
package CodeBreaker;
import java.util.*;
public class Board {
	private static ArrayList<String> guesses;
	private static ArrayList<String> clues;
	/**
	 * constructor method that initializes guesses and clues class variables
	 */
	public Board(){ //constructor method
		guesses = new ArrayList<>(); //initialize class variables
		clues = new ArrayList<>();
	}
    /**
     * Adds a guess to the guesses list.
     * @param String guess, the player's guess.
     */
	public void addGuess(String guess){ 
		guesses.add(guess);
	}
    /**
     * Adds a clue to the clues list.
     * @param String clue, the clue corresponding to the guess.
     */
	public void addClue(String clue){
		clues.add(clue);
	}
	/**
     * Displays the current state of the board including guesses and clues.
     */
	public void displayBoard(){
		System.out.println("Guesses\tClues");
		System.out.println("********************");
		if (guesses.size()!=clues.size()) {
			for (int i=0;i<guesses.size()-1;i++){
				System.out.println(guesses.get(i)+"\t"+clues.get(i));
			}
			System.out.println(guesses.get(guesses.size()-1));
		}
		else {
			for (int i=0;i<guesses.size();i++){
				System.out.println(guesses.get(i)+"\t"+clues.get(i));
			}
		}
	}
    /**
     * clears all guesses and clues to reset the board.
     */
	public void playAgain() {
		guesses.clear();
		clues.clear();
	}
    /**
     * Getter method for the list of clues.
     * @return the clues Arraylist.
     */
	public ArrayList<String> getClues(){
		return clues;
	}
    /**
     * removes a specific guess from the guesses list.
     * @param String guess, the guess to remove.
     */
	public void removeGuess(String guess) {
		guesses.remove(guess);
	}
    /**
     * getter method for the list of guesses.
     * @return the guesses Arraylist.
     */
	public ArrayList<String> getGuesses(){
		return guesses;
	}

    /**
     * Checks if the last clue indicates a win "bbbb".
     * @return boolean True if the player has won, otherwise false.
     */
	public boolean checkWin(){
		if (clues.get(clues.size()-1).equals("bbbb")){ //if the last clue equals the winning clue "bbbb"
			return true; //player has won
		}
		else{
			return false; //otherwise, player has not won yet
		}
	}
	/**
     * Checks if the game is lost based on the number of clues and win status.
     * @param boolean won Indicates if the player has already won.
     * @return boolean True if the game is lost, otherwise false.
     */
	public boolean checkLost(boolean won){
		if (clues.size()>=10&&!won){ //if the number of clues given exceeds the max amount of turns and the game is not won 
			return true; //player has lost
		}
		else{
			return false; //if not, player has not lost
		}
	}
	
}









