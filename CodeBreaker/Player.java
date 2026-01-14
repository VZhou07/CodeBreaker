/*
 * The abstract class that acts is the superclass to the AIPlayer and HumanPlayer subclasses. This abstract class defines all the abstract methods that are common in both classes but have different implementations.
 * This class also contains protected attributes that are visible to extended classes of this abstract class.
 */
package CodeBreaker;
import java.io.*;
public abstract class Player {
	protected final static int CODELENGTH=4; //constant integer value for length of the code
	protected final String possibleColours="rgbyop"; //valid colours for the code
	protected BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
	protected boolean guesser; //indicates if the player is the buffer
	protected boolean codeCreator; //indicates if the player is the code creator
	protected String code; //code created by the player 
	protected String name;
	
    /**
     * Abstract method for making a guess.
     * @return The player's guess String.
     */
	public abstract String makeGuess();
    /**
     * Abstract method for giving a clue based on a guess.
     * @return The String clue provided by the player.
     */
	public abstract String giveClue();
    /**
     * Abstract method for creating a code.
     */
	public abstract void makeCode();
	
    /**
     * Getter method to get the name of the player.
     * @return The player's name String.
     */
    public String getName() {
        return name;
    }
}









