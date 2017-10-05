import java.util.Arrays;


/**
 *
 * Class uses the Vingenere cipher to encode/ decode a message that follows a secret phrase and pattern
 *
 * @author Kate Stone
 *
 */
public class Prog010Cipher {
    // INSTANCE VARIABLES
    char [ ] keyList; // Use standard alphabet as array
    char [ ][ ] cipherTable = new char[26][26]; // Use alphabetList and set the 2d array to it.
    //Created alphabetList to hold onto the alphabet and be utilized for cipherTable
    char [] alphabetList = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    // METHODS
    /**
     * encode is used by taking in the message param and using a for loop, if statement, and an else statement.
     * @param message = the message that needs to be encoded
     * @return result = message after being encoded
     */
    String encode( String message ) {
        //Create a variable named result
        String result = "";
        int ascii = -65;//ascii is the offset we're going to create in order to keep the variables in bounds
        String[ ] mesArray = message.split("");
        int i = 0;
        for ( String letterString: mesArray ) {
            if (  !" ".equals(letterString) && letterString.length()>0 ) {
                char letter = letterString.toUpperCase().charAt(0);
                int j = (int)keyList[ i ] + ascii;
                int k = (int)letter + ascii;
                result += Character.toString( cipherTable[ j ][ k ] );
            } else {
                result += " ";
            }
            i++;
            i = i == keyList.length ? 0: i;

        }
        return result;
    }

    /**
     * The decode method will take in the message param and use a for loop, if statement, and else statement to decipher the message
     * @param message = the string being decoded
     * @return result a string that has been decoded
     */
    String decode( String message ) {
        String result = "";
        String[] messageArray = message.split("");
        int i = 0;
        int rows = 0;
        int columns = 0;
        for ( String str: messageArray ) {
            if ( !" ".equals( str ) && str.length() > 0 ) {
                rows = Arrays.binarySearch(alphabetList, keyList[i]);
                for ( int k = 0; k < cipherTable[0].length; k++ ) {
                    if ( cipherTable[rows][k] == str.charAt(0) ) {
                        columns = k;
                    }
                }
                result += Character.toString(alphabetList[columns]);
            } else {
                result += " ";
            }
            i++;
            i = i == keyList.length ? 0: i;
        }
        return result;
    }

    // CONSTRUCTORS
    /**
     * This constructor will create the cipherTable, create our keyList using given key
     * it will then shift the table based on the code given.
     * @param code = the code given in main
     * @param key = the key given in main
     */

    public Prog010Cipher( char code, String key ) {
        int codeValue = 0;
        int start = Arrays.binarySearch(alphabetList, code);         //Where to start in the alphabet array for the row
        int index = start;
        //Used for decoding
        codeValue = (int)code;
        //Creates the cipherTable by going through alphabet and repeats
        for ( int i = 0; i < alphabetList.length; i++ ) {
            for ( int j = 0; j < alphabetList.length; j++ ) {
                cipherTable[ i ][ j ] = j + index >= alphabetList.length ? alphabetList[ j + index - 26 ]: alphabetList[ j + index ];
                System.out.println(cipherTable[i][j]);
            }
            index++;
            index = index == alphabetList.length ? 0: index;
        }
        //We need to trim spaces in the key
        String str = key.replaceAll( "\\s+", "" );
        char[ ] cArray = str.toCharArray();//Convert str to a character array
        keyList = cArray; //Globalize the cArray that was just created without spaces
    }



    // MAIN (TEST) Method
    /**
     * main function, sends code and key to Prog010Cipher.
     * sends message to encode and decode
     * 
     * @param args = none
     */
    public static void main( String[ ] args ) {
        // Testing only works if using VM argument -ea
        Prog010Cipher self = new Prog010Cipher( 'H', "BABBAGE" );
        assert "PHXXF MQYBPKNJ".equals( self.encode( "HAPPY BIRTHDAY" ) );
        assert "HAPPY BIRTHDAY".equals( self.decode( "PHXXF MQYBPKNJ" ) );
    }
} // END OF CLASS --------------------------------------------------------
