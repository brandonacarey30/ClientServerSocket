package client;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import server.NewConnection;

@SuppressWarnings("serial")
public class Chatroom extends JFrame {
	
	//The different chatroom fields
	private JPanel panel, panel2;
	private JTextArea sentMessagesArea;
	private JTextField messageField;
	private JButton sendButton;
	
	//Client of this chatroom
    private Client client;
    
    //Constructor
    public Chatroom(Client client) {
	this.client = client;
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		
		Listener listener = new Listener();
		Listener kListener = new Listener();
		
		this.setTitle("Chatroom");
		this.getContentPane();
		this.setResizable(true);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		sentMessagesArea = new JTextArea(42, 60);
		sentMessagesArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(sentMessagesArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(20, 20, 20, 20);
		getContentPane().add(scroll);
		setLocationRelativeTo(null);
		sentMessagesArea.setBackground(new Color(255,255,255));
		sentMessagesArea.addKeyListener(kListener);
		panel.add(scroll);
		
		messageField = new JTextField(64);
		messageField.addKeyListener(kListener);
		
		sendButton = new JButton("Send");
		sendButton.addActionListener(listener);
		
		panel2.add(messageField);
		panel2.add(sendButton);
		panel2.setBackground(new Color(20, 40, 70));
		
		this.add(panel, "Center");
		this.add(panel2, "South");
		this.setVisible(true);
		this.pack();
	}
	
    //Updates the text area of the gui
	public void updatePanel(String message) {
		messageField.setText("");
		sentMessagesArea.append(message + "\n" + "\n");
	}
	
	//Listens for a button press and does an action
	private class Listener implements ActionListener, KeyListener {
		
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == sendButton) {
				//updatePanel(messageField.getText());
			    // c.alertAllUsers(": ", messageField.getText());  // DUNCAN: Not possible.
			    client.sendMessage(messageField.getText());
			} 
		}
		
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			    client.sendMessage(messageField.getText());
			    // c.alertAllUsers(": ", messageField.getText());
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}

}
