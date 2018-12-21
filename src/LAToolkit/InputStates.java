
package LAToolkit;

import javafx.scene.control.TextArea;

/** Maybe
 *
 * @author Tyler Jorgensen
 */
public class InputStates {
    /** States of the program 
     * I - Program is in free state
     * V - Variable is in process of being declared
     * M - Matrix state
     * F - Function state, or variable state
     * E - Error state
     */
    static char state = 'I';
    
    
    static void stateChange(char s){
        state = s;
    }
    
    static void checkState(){
        
    }
}
