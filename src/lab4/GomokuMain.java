package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain {
	

	public static void main(String[] args) {
		/**
		 * @param
		 *  Checks if an argument is given, if no arguments is given sets a default portnumber to 4500.
		 */
	int port;
	if(args.length == 1){
		try{
			port = Integer.parseInt(args[0]);
			
		}
		catch(NumberFormatException e){
			port = 4500;
		}
	}
	else{
		port = 4500;
	}
	
	
		GomokuClient client = new GomokuClient(port);
		GomokuGameState gamestate = new GomokuGameState(client);
		GomokuGUI gui = new GomokuGUI(gamestate, client, port);

	}

}
