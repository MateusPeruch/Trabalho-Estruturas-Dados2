package lib;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;

public class MyTextFieldSearch extends JTextField {
	
	public MyTextFieldSearch() {
		super();
		setBackground(Color.yellow);
		setText(null);
	}
	
	@Override
	public void setText(String t) {
		
		setFont(new Font("Arial", Font.PLAIN, 12));
		
		if (t == null || t.toString().isEmpty()) {
			setForeground(Color.GRAY);
			super.setText("");
		}
		else {
			setForeground(Color.black);
			super.setText(t);
		}
	}

}
