package project2;
/**
 * Jahlil Owens
 * Project 2
 * 11/10/2023
 * This is my Original work
 */
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    /**
     * This operates the server side of the game by sending and recieving data with the client
     * @param args
     * @throws UnknownHostException
     * @throws Exception
     */
    public static void main(String[] args) throws UnknownHostException, Exception {
        Scanner infromuser = new Scanner(System.in); // For the user input
        try {
            Socket clientSocket = new Socket("localhost", 6789); // Allows connection to server
            System.out.println("served connected.");
            Scanner infromServer = new Scanner(clientSocket.getInputStream()); // Read input from the server
            DataOutputStream outToserver = new DataOutputStream(clientSocket.getOutputStream());  // Send the data from the client to the server
            int newwordlength = Integer.parseInt(infromServer.nextLine()); // Reads the length of the word that the server sent to the client
            System.out.println(newwordlength);
            GUI hangmanGui = new GUI(newwordlength); // Insatnce for the GUI class
            String guessword = "*".repeat(newwordlength); // Makes the word hidden
            int misses = 0; // Tracks the number of wrong guesses
            boolean gamedecision = false; // Track the game for a win or lost
            System.out.println("Word: " + guessword);
            while (misses < 6 && hangmanGui.isNotSolved()) {
                System.out.println("Enter a letter, won or lost: ");
                String message = infromuser.nextLine().toUpperCase(); // Turns the message into a uppercase message
                if (message.equals("WON") || message.equals("LOST")) {
                    if (message.equals("WON")) {
                        if (!guessword.contains("*")) {
                            outToserver.writeBytes(message+'\n'); // Sends the message won to the server
                            String lastword = infromServer.nextLine();
                            System.out.println(lastword); // Print out the word from the server that was picked
                            gamedecision = true;
                            break;
                    }
                    else {
                        System.out.println("guess the word first"); // A statment that the user cant win until the word is done
                        continue;
                    }
                }
                    else if(message.equals("LOST")) { 
                        outToserver.writeBytes(message+'\n'); // Send the message lost to the server
                        String finalword = infromServer.nextLine();
                        System.out.println("You Lost, The word is: " + finalword); // Prints out the word that was received from the server
                        String updateword = hangmanGui.setWord(finalword);
                        System.out.println(updateword);// Add the word that needed to be guess to the gui
                        break;
                    }
                    break;
                }
                else if (message.length() == 1 && hangmanGui.isNotSolved()) {
                    outToserver.writeBytes(message + '\n'); // Sends the message to the server
                    String positions = infromServer.nextLine().toUpperCase();
                    System.out.println(message); // Prints out the uppercase version of the message
                    if (positions.isEmpty()) {
                        System.out.println("there are no positions for that letter");
                        hangmanGui.addMiss(message); // Adds that message to the gui
                        misses++; // Increases the missed guesses count
                    }
                    else {
                        char [] newguess = guessword.toCharArray(); // Turns the word into a character array for easier access
                        for (String position : positions.split(" ")) { // Makes a for loop that goes through each position and takes away any empty space in the choosen word
                            int pos = Integer.parseInt(position); // Takes each substring and converts it to an integer
                            newguess[pos] = message.charAt(0); // Updates the newguess array and replaces each * with the letter from the message
                            hangmanGui.addLetter(message.charAt(0), pos); // Adds the correct letter to the hangman
                        }
                        guessword = new String(newguess); // Brings the newguess back to a string so the guessword can be updated
                    }
                    if (misses >= 6) {
                        System.out.println("Word: " + guessword);
                    }
                    if (!guessword.contains("*")) {
                        System.out.println("You win, Good job getting the word "+ guessword);
                        gamedecision = true;
                        break;
                    }
                }
            else {
                System.out.println("Invalid input.");
            }
        }
        if (!gamedecision && misses >= 6) {
            System.out.println("You Lost, the word is: " + guessword);
        }
        else if(misses >= 6) {
            System.out.println("You lose! the word was: "+guessword);
        }
            clientSocket.close();
            infromuser.close();   
            }
            finally {
        }
            
    }
}