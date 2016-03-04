package lab4.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import lab4.GomokuMain;
import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

/*
 * The GUI class
 */

public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;
	private JFrame frame;
	private GamePanel gamePanel;
	private GameGrid grid;
	private JLabel messageLabel;
	private JButton connectButton;
	private JButton newGameButton;
	private JButton disconnectButton;
	private ConnectionWindow connection;
	
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c, int port){
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);
		frame = new JFrame("Port: "+port);
		grid = gamestate.getGameGrid();
		gamePanel = new GamePanel(grid);
		gamePanel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				int[] a = gamePanel.getGridPosition(e.getX(), e.getY());
				gamestate.move(a[0], a[1]);
			}
		});
		messageLabel = new JLabel("Welcome to Gomoku");
		messageLabel.setFont(new Font("Serif", Font.ROMAN_BASELINE, 20));

		connectButton = new JButton("Connect");
		connectButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				connection = new ConnectionWindow(client);
			}
		});
		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gamestate.newGame();
				client.sendNewGameMessage();
			}
		});
		disconnectButton = new JButton("Disconnect");
		disconnectButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gamestate.disconnect();
				client.disconnect();
			}
		});
		
		SpringLayout layout = new SpringLayout();
		frame.getContentPane().setLayout(layout);
		frame.getContentPane().add(gamePanel);
		frame.getContentPane().add(connectButton);
		frame.getContentPane().add(newGameButton);
		frame.getContentPane().add(disconnectButton); 
		frame.getContentPane().add(messageLabel);
		
		layout.putConstraint(SpringLayout.NORTH, gamePanel,
                5,
                SpringLayout.NORTH, frame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, connectButton, 
				5, 
				SpringLayout.SOUTH, gamePanel);
		layout.putConstraint(SpringLayout.NORTH, newGameButton, 
				5, 
				SpringLayout.SOUTH, gamePanel);
		layout.putConstraint(SpringLayout.WEST, newGameButton, 
				5, 
				SpringLayout.EAST, connectButton);
		layout.putConstraint(SpringLayout.NORTH, disconnectButton, 
				5, 
				SpringLayout.SOUTH, gamePanel);
		layout.putConstraint(SpringLayout.WEST, disconnectButton, 
				5, 
				SpringLayout.EAST, newGameButton);
		layout.putConstraint(SpringLayout.NORTH, messageLabel, 
				5, 
				SpringLayout.SOUTH, connectButton);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setMinimumSize(new Dimension(350, 400));
		frame.setPreferredSize(new Dimension(350, 400));
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
	public void update(Observable arg0, Object arg1) {
		
		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the gamestate has changed
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}
		
	}
	
}