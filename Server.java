package project2;
/**
 * Jahlil Owens
 * Project 2
 * 11/10/2023
 * This is my Original work
 */
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class Server {
    /**
     * This creats a array of fixedwords that can be randomly pick or pick
     */
    private static final String[] fixedWords = {
        "FUTURE",
        "NETWORK",
        "DISTANCE",
        "PHOTOSYNTHESIS",
        "ROLLERCOASTER",
        "CHRISTMAS",
        "FORTUNATE",
        "EMBERS",
        "INFORMATE",
        "HYGIENE",
        "WINNERS",
        "CHRIS"
    };

    /**
     * This randomly gets a word from the array of fixed words.
     * @return
     */
    private static String randomwords() {
        Random random = new Random();
        int randomword = random.nextInt(fixedWords.length); // Gets a random integer from 0 to teh end of teh array
        return fixedWords[randomword]; // This returns the word based on the number it chose
    }
    public static void main(String[] args) throws Exception {
        try (ServerSocket welcomeSocket = new ServerSocket(6789)) { // Allows connection between client and server
            System.out.println("Server is ready...");
            while (true){
                Socket serverSocket = welcomeSocket.accept(); // Accepts the connection between the server and client
                System.out.println("Client connected: " + serverSocket);
                Scanner infromclient = new Scanner(serverSocket.getInputStream()); // Read input from the client
                DataOutputStream outToClient = new DataOutputStream(serverSocket.getOutputStream()); // Sends data back to the client
                String pickword = randomwords();
                System.out.println(pickword);
                int wordlength = pickword.length();
                System.out.println(wordlength);
                outToClient.writeBytes(""+wordlength +'\n'); // Sends the length of the word to the client
                Word guessword = new Word(pickword); // A insatcne variable as the word class that uses the random word that was picked
                while (true) {
                    try{
                        String guessedletter = infromclient.nextLine().toUpperCase(); // Uppercases the clients message after reading it 
                        if (guessedletter.equals("WON")) {
                            System.out.println("You Win! Good Job");
                            outToClient.writeBytes("You Win, Good job on getting the word: " +pickword+'\n'); // Sends this prompt back to the client if the message was won
                            break;
                        }
                        else if (guessedletter.equals("LOST")) {
                            System.out.println("You Lost, The Word is: " + pickword);
                            outToClient.writeBytes(""+pickword + '\n'); // Sends this prompt back to the client if the message was lost
                            break;
                        }
                        else if (guessedletter.length() == 1 && Character.isLetter(guessedletter.charAt(0))) {
                            char letter = guessedletter.charAt(0); // The positions of the letter that exist
                            String positions = guessword.getSpots(letter); // Gets the positions of the letter that was guess corectly
                            System.out.println("Positions of the letter "+guessedletter+" are: "+positions+".\n"); // Prints out the prompt of the letter and its positions
                            if ((positions.isEmpty())) {
                                outToClient.writeBytes("\n"); // Sends back a new line to the client
                            }
                            else {
                                outToClient.writeBytes(""+positions+'\n'); // Sends back the position to client
                            }
                        }
                        else {
                            System.out.println("Invalid guess.");
                            continue;
                        }
        } catch (NoSuchElementException e) { // Once the client disconnects it makes a print statement to catch the sequence
            System.out.println("client disconnected");
            break;
        }
    }
    serverSocket.close();
    infromclient.close();
    outToClient.close();
    }
    }
    catch (IOException checker) {
        checker.printStackTrace();
        }
    }
}
