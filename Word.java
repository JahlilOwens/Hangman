package project2;

//package hangserv;
import java.io.*;
//import java.math.*;
import java.util.Scanner;
public class Word {
  private String word; // word to be guessed
  public Word(String w) {
    word = w.toUpperCase();
  }

  public Word(Scanner in) throws IOException
  {
    // sets up word to be a random word in the stream
    // referred to by in
    // assumes first line of in gives the length of the file
    int len = Integer.parseInt(in.nextLine());
    int numWord = (int)(Math.random() * len) + 1;
    for (int i=1; i<=numWord; i++)
      word = in.nextLine().toUpperCase();
  }

  public String getWord()
  {
    return word;
  }

  public int getLength()
  {
    return word.length();
  }
  public String getSpots(char c)
  {
    // returns a String containing all positions where c occurs
    // in word (first position is 0)
    // returns empty string if c does not appear in word
    c = Character.toUpperCase(c);
    String ans = "";
    String tempword = word;
    int spot;
    int accum = 0;
    while ((spot = tempword.indexOf(c)) != -1)
    {
      if (ans.length() > 0)
        ans = ans + " " + (spot+accum);
      else
        ans = ans + (spot+accum);
      tempword = tempword.substring(spot+1);
      accum += (spot+1);
    }
    return ans;
  }
}