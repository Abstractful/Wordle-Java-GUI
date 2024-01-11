
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.*;
import java.awt.Font;

	class WordPanel extends JPanel {
	
		private JLabel[] charColumns = new JLabel[6];
//This sets the size of the game
		public WordPanel() {
			this.setLayout(new GridLayout(1, 5));
			this.setSize(500, 20);
//The borders of each box is created. to make the illusion of each box having their own outline, a compound border is used. 
Border blackline = BorderFactory.createLineBorder(Color.white,2);
Border grayline = BorderFactory.createLineBorder(Color.gray,1);
Border border = BorderFactory.createCompoundBorder(blackline, grayline);
//here, an array of labels are defined. the inputed word is split up into individual letters and each letter will be displayed in the Jlabel
			for (int i = 0; i < 5; i++) {
				charColumns[i] = new JLabel("", JLabel.CENTER);
        charColumns[i].setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        charColumns[i].setForeground(Color.white);
				charColumns[i].setOpaque(true);
				charColumns[i].setBorder(border);
				this.add(charColumns[i]);
			}
		}
//this gives us the background color in the GUI
		public void updatePanel(String inputWord, int position, Color color) {
			charColumns[position].setBackground(color);
			charColumns[position].setText(inputWord);
		}
//sets the individual label fields as empty strings 
		public void cleanAllColumns() {
			for (int i = 0; i < 5; i++) {
				charColumns[i].setText("");
			}
		}
	}