/**
 * This class holds all the valid words that can be used by the player
 * @author Mariam Almalki
 *
 */
public class CommandWords {
	
	 /**
	  * A constant array that holds all valid command words
	  */
    private static final String[] validCommands = {
        "move", "quit", "help" 
    };
    
    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }

    public String getCommandWords(){
        String returnString = "   ";
        for(String command : validCommands) {
            returnString += " " + command;
        }
        return returnString;
    }
    
    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }
    
    
}
