package project2;

//package hangclient;

import java.awt.*;
//import java.awt.image.*;
import java.awt.event.*;
//import javax.swing.*;
import java.awt.Frame;

class HangPanel extends Panel
{
  int misses = 0;
  public boolean addMiss()
  {
    // true if misses < 6, false otherwise
    misses++;
    repaint();
    return (misses < 6);
  }
  public synchronized void paint(Graphics g)
  {
    g.drawLine(100, 100, 100, 40);
    g.drawLine(100, 40, 300, 40);
    g.drawLine(300, 40, 300, 500);
    g.drawLine(370, 500, 60, 500);
    if (misses >= 1)
      g.drawOval(70, 100, 60, 60);
    if (misses >= 2)
      g.drawLine(100, 160, 100, 310);
    if (misses >= 3)
      g.drawLine(100, 235, 50, 200);
    if (misses >= 4)
      g.drawLine(100, 235, 150, 200);
    if (misses >= 5)
      g.drawLine(100, 310, 50, 400);
    if (misses >= 6)
      g.drawLine(100, 310, 150, 400);
  }
}
public class GUI extends Frame {
  private class myListener extends WindowAdapter
  {
    Frame f;
    public myListener(Frame fr)
    {
      f = fr;
    }

    public void windowClosing(WindowEvent e)
    {
      f.dispose();
      System.exit(0);
    }
  }
  private Panel labels;
  private Label theWord;
  private Label wrongLetters;
  private String wd;
  private String wLets;
  private HangPanel hp;
  public GUI(int wdlen) {
    labels = new Panel();
    labels.setLayout(new GridLayout(2, 1));
    hp = new HangPanel();
    wd = "";
    for (int i=0; i<wdlen; i++)
      wd = wd + '*';
    theWord = new Label(wd);
    setLayout(new GridLayout(1, 2));
    labels.add(theWord);
    theWord.setFont(new Font("Times New Roman", Font.PLAIN, 36));
    labels.setFont(new Font("Times New Roman", Font.PLAIN, 36));
    setSize(800, 600);
    add(labels);
    add(hp);
    wLets = "";
    wrongLetters = new Label("");
    labels.add(wrongLetters);
    setVisible(true);
    addWindowListener(new myListener(this));
  }

  public boolean addMiss(String guess)
      // true if game can continue
  // false otherwise
  {
    boolean ans = hp.addMiss();
    wLets = wLets + guess + " ";
    wrongLetters.setText(wLets.toUpperCase());
    this.repaint();
    return ans;
  }
  public void addLetter(char c, int spot)
  {
    c = Character.toUpperCase(c);
    String newWd;
    if (spot > 0)
      newWd = wd.substring(0, spot);
    else
      newWd = "";
    newWd = newWd + c;
    newWd = newWd + wd.substring(spot+1);
    wd = newWd;
    theWord.setText(wd);
    this.repaint();
  }
  public boolean isNotSolved()
  {
    return (wd.indexOf("*") != -1);
  }
  public String setWord(String w)
  {
    wd = w;
    theWord.setText(wd);
    repaint();
    return wd;
  }
}