package it.polimi.ingsw.gc_12.client.rmi;

import it.polimi.ingsw.gc_12.FamilyMember;
import it.polimi.ingsw.gc_12.FamilyMemberColor;
import it.polimi.ingsw.gc_12.Match;
import it.polimi.ingsw.gc_12.MatchRemote;
import it.polimi.ingsw.gc_12.Player;
import it.polimi.ingsw.gc_12.action.ActionFactory;
import it.polimi.ingsw.gc_12.action.ActionPlace;
import it.polimi.ingsw.gc_12.mvc.CLIAdapter;
import it.polimi.ingsw.gc_12.mvc.View;
import it.polimi.ingsw.gc_12.occupiables.Occupiable;
import it.polimi.ingsw.gc_12.action.Action;
import it.polimi.ingsw.gc_12.server.controller.query.Query;
import it.polimi.ingsw.gc_12.server.view.RMIViewRemote;
import com.google.gson.TypeAdapterFactory;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientRMI { //Main class of the Clients using RMI

	private final static int RMI_PORT = 52365;

	
	public final static String HOST = "127.0.0.1";

	public final static int PORT = 52365;

	private static final String NAME = "lorenzo";

	private RMIViewRemote serverStub;
	private Registry registry;
	private View view;

	public void start() throws RemoteException, NotBoundException, AlreadyBoundException {
		//Get the remote registry
		registry = LocateRegistry.getRegistry(HOST, PORT);

		//get the stub (local object) of the remote view
		serverStub = (RMIViewRemote) registry.lookup(NAME);



		Scanner stdIn = new Scanner(System.in);
		System.out.println("Choose a name");
		String name = "";
		while (true) {
			name = stdIn.nextLine();
			if(!"\n".equals(name) && !"".equals(name)) {
				ClientRMIView rmiView = new ClientRMIView(this, name);
				System.out.println("You are being registered on the server...");
				// register the client view in the server side (to receive messages from the server)
				serverStub.registerClient(rmiView);
				break;
			}
			else {
				System.out.println("Choose a name");
				//stdIn.next();
			}
		}
		

		


		//while (true) {
			//Capture input from user
			/*String inputLine = stdIn.nextLine();
			System.out.println("SENDING "+inputLine);
			Action action;
			Query query;
			try {

				// Call the appropriate method in the server
				switch (inputLine) {


					default:
						break;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

		//}
	}
	
	/*public void askAction(boolean isFMPlaced) throws RemoteException {
		view.askAction(isFMPlaced);
	}*/
	
	public void sendAction(Action action) throws RemoteException {
		System.out.println("ClientRMI: " + action.getClass().getSimpleName() + " recieved from CLIView. Sending it to RMIView...");
		serverStub.receiveAction(action);
	}

	public static void main(String[] args) throws RemoteException, NotBoundException, AlreadyBoundException {
		ClientRMI clientRMI = new ClientRMI();
		clientRMI.start();

	}




}
