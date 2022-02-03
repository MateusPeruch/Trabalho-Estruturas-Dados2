package lib;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

public class DataTextField extends JFormattedTextField {

	private MaskFormatter io_mascara;
	private SimpleDateFormat io_formato;

	public DataTextField() {
		super();
		
		addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {				
				setCaretPosition(0);				
			}
		});
		
		try {
			io_mascara = new MaskFormatter("##/##/####");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		setFormatterFactory(new DefaultFormatterFactory(io_mascara));
		
		io_formato = new SimpleDateFormat("dd/MM/yyyy");
		
		setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		
		setHorizontalAlignment(CENTER);		
	}

	public Date getDate() {
		Date d = null;
		try {
			d = io_formato.parse(getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		
		return d;
	}
	
	public void setDate(Date date) {
		setText(io_formato.format(date));
	}
}













