import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;




public class Main implements ActionListener {
  //initializes objects
	private JFrame jframe;
	private List<WordPanel> panelList;
	private InputPanel inputPanel;
	private int colCount = 0;
	private String wordleWord;



	public static void main(String[] args) throws IOException, URISyntaxException{
		
    new Main();
	}

//the default constructors are initialized. the game frame is set a size with  7 rows and its default position is in the middle. 
  public Main() throws IOException, URISyntaxException{
		wordleWord = fetchWord().trim().toUpperCase();
		jframe = new JFrame("WORDLE");
		jframe.setLayout(new GridLayout(7, 1));
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(300, 450);
		panelList = new ArrayList<>();
	//a for loop is used of 6 to initialize all 6 individual word panels
		for (int i = 0; i < 6; i++) {
			panelList.add(new WordPanel());
			jframe.add(panelList.get(i));
		}

		inputPanel = new InputPanel();
		inputPanel.getSubmit().addActionListener(this);
		jframe.add(inputPanel);
		jframe.setLocationRelativeTo(null);
		jframe.revalidate();

	}

  //simple return statements that return objects
	public JFrame getJframe() {
		return jframe;
	}
	public String getWordleWord() {
		return wordleWord;
	}

	public WordPanel getCurrentActivePanel() {
		return panelList.get(colCount);
	}

	public List<WordPanel> getPanelList() {
		return this.panelList;
	}
//resets the Panels
	private void cleanPanelList() {
		for (WordPanel panel : getPanelList()) {
			panel.cleanAllColumns();
		}
	}

  //this method is called each time the command button is pressed. It checks the inputed word, if it is 5 letters exactly and if the ok button was pressed. If is the right word, then the game will display "you win!!!" or if you lose it will display You lose!!! and the correct word
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		String userWord = inputPanel.getTextField().getText().trim();
		if (colCount > 5) {
			JOptionPane.showMessageDialog(null, "You lose!!! The Wordle was: " + wordleWord, "GET GOOD", 0);
			getJframe().dispose();
      outputLoss();
			return;
		}
    if ("OK".equals(action) && userWord.length() > 4 && userWord.length() < 6 && !userWord.contains(" ")) {
			if (isWordleWordEqualsTo(userWord)) {
				cleanPanelList();
				JOptionPane.showMessageDialog(null, "You Win!!!", "Congrats!" + "", 1);
				getJframe().dispose();
        outputWin();
				return;
			}
			colCount++;
		}
	}


//The for loop compares each individual letter in the string the player inputed and the wordle word. If the wordle word contains a letter which is in the same spot as the guess, then it will be green, if the wordle word contains a letter from the player's guess but it is not in the same spot, then it will be yellow, and if it is not in the wordle word at all then it will be gray.
	private boolean isWordleWordEqualsTo(String userWord) {
		String[] userWordLetterArray = userWord.toUpperCase().split("");
		List<String> wordleLetters = Arrays.asList(getWordleWord().split(""));
		List<Boolean> wordMatchList = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			if (wordleLetters.contains(userWordLetterArray[i])) {
				if (wordleLetters.get(i).equals(userWordLetterArray[i])) {
					getCurrentActivePanel().updatePanel(userWordLetterArray[i], i, new Color(107,170,99,255));
					wordMatchList.add(true);
				} else {
					getCurrentActivePanel().updatePanel(userWordLetterArray[i], i, new Color(201,180,87,255));
					wordMatchList.add(false);
				}
			} else {
				getCurrentActivePanel().updatePanel(userWordLetterArray[i], i, new Color(120,124,127,255));
				wordMatchList.add(false);
			}
		}
  //the return statement will check if there are any false entries present in the list. it means that wordle word  is not equal to user input.
		return !wordMatchList.contains(false);
	}

//fetchWord() generates a number between 1 and 173 inclusive of both. After generating this number, the Scanner will loop through the text file words.txt to find the line that relates to the number generated. The method will return the line of the text file, which will be the string the player has to attempt to find
        File file = new File("words.txt"); 
    private String fetchWord() throws IOException, URISyntaxException{
        String line = "";
        try{
          int i =0;

          Scanner in = new Scanner(file);
          int num = ((int)(Math.random() * 12942) + 1);

          while (in.hasNextLine() && i <num ){
            line = in.nextLine();
            i++;
          }
          in.close();
          return line;
        } catch (Exception FileNotFoundException){ 
          return "no file found";
         } 
     
      }

  //if the player wins the game , the file wscore.txt will be updated with a new line consisting of the word the player won in the amount of tries the player took
  private String outputWin(){
    try{
      Writer output = new BufferedWriter(new FileWriter("wscore.txt", true));
      output.append("word: " + wordleWord + " in " + (colCount+1) + " tries" + "\n");
      output.close();
      return "";
    }  
    catch(Exception FileNotFoundException){
      return "error";
    }
  }
  
//if the player fails to get the wordle in the amount of allowed tries, the file wscore.txt will be updated with a new line consisting of the word the player lost to. 
  private String outputLoss(){
    try{
     Writer output = new BufferedWriter(new FileWriter("wscore.txt", true));
      output.append("You lost to: " + wordleWord + "\n");
      output.close();
      return "";
    }  
    catch(Exception FileNotFoundException){
      return "error";
    }
  }
  
}
