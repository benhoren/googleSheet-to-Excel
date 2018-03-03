import java.awt.ComponentOrientation;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class mainScreen {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private static JTextField log;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainScreen window = new mainScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setEnabled(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 682, 390);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setFocusable(false);
		textPane.setText(":\u05E7\u05D9\u05E9\u05D5\u05E8");
		textPane.setBounds(595, 11, 61, 20);
		frame.getContentPane().add(textPane);
		
		textField = new JTextField();
		textField.setBounds(10, 11, 575, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		
		//מוסד נבחר
		JButton btnNewButton = new JButton("מוסד נבחר");
		btnNewButton.setFocusPainted(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log.setText("");
				String url = textField.getText();
				String rng = textField_1.getText();
				rng = rng.trim();
				main.start(url, rng, exportType.exportInstitution);
				
			}
		});
		
		
		btnNewButton.setBounds(208, 117, 97, 66);
		frame.getContentPane().add(btnNewButton);
		
		textField_1 = new JTextField();
		textField_1.setBounds(313, 42, 141, 20);
		
		textField_1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setEditable(false);
		textPane_1.setFocusable(false);
		textPane_1.setText("\u05DE\u05E1\u05E4\u05E8/ \u05E9\u05DD \u05DE\u05D5\u05E1\u05D3");
		textPane_1.setBounds(464, 42, 121, 20);
		frame.getContentPane().add(textPane_1);
		
		//יצא גליון
		JButton button = new JButton("\u05D9\u05E6\u05D0 \u05D2\u05D9\u05DC\u05D9\u05D5\u05DF");
		button.setFocusPainted(false);
		button.setBounds(422, 117, 97, 66);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log.setText("");
				String url = textField.getText();
				main.start(url, "", exportType.exportAll);
			}
		});
		frame.getContentPane().add(button);
		
		//כל המוסדות
		JButton button_1 = new JButton("\u05DB\u05DC \u05D4\u05DE\u05D5\u05E1\u05D3\u05D5\u05EA");
		button_1.setFocusPainted(false);
		button_1.setBounds(315, 117, 97, 66);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log.setText("");
				String url = textField.getText();
				main.start(url, "", exportType.exportTable);
			}
		});
		frame.getContentPane().add(button_1);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setEditable(false);
		textPane_2.setText("\u05DC\u05D5\u05D2");
		textPane_2.setBounds(488, 251, 83, 20);
		frame.getContentPane().add(textPane_2);
		
		log = new JTextField();
		log.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		log.setEditable(false);
		log.setBounds(392, 251, 86, 20);
		frame.getContentPane().add(log);
		log.setColumns(10);
	}
	
	public static void addToLog(String msg){
		log.setText(msg);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
