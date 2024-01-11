import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;

class InputPanel extends JPanel {

		private JTextField text;
		private JButton submit;
//User input their words in the InputPanel. The grid has 2 parts: an input area and the "ok" button
		InputPanel() {
			this.setLayout(new GridLayout(1, 2));
			text = new JTextField();
			text.setText("");
			this.add(text);
//The OK button allows users to input their guesses
			submit = new JButton("OK");
    submit.setBackground(new Color(172, 240, 165));
			this.add(submit);
			this.setVisible(true);
		}
//resets the text field to an empty string
		public JTextField getTextField() {
      String temp = this.text.getText();
      JTextField jort = new JTextField();
      jort.setText(temp);
      text.setText("");
			return jort;
		}

		public JButton getSubmit() {
      System.out.println("getSubmit");
			return submit;
		}

	}