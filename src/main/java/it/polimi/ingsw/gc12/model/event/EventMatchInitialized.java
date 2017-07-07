package it.polimi.ingsw.gc12.model.event;


import it.polimi.ingsw.gc12.view.client.ClientFactory;
import it.polimi.ingsw.gc12.view.client.ClientHandler;
import it.polimi.ingsw.gc12.view.client.gui.MainBoard;
import javafx.application.Platform;

import java.io.IOException;

public class EventMatchInitialized extends Event implements EventView{

	@Override
	public void executeClientSide(ClientHandler client) {}

	@Override
	public String toString() {
		return "";
	}

	@Override
	public void executeViewSide(MainBoard view) {
		if(view.isReady())
			view.getControllerMainBoard().notifyObservers(0);
		else
			ClientFactory.getClientHandler().setStarted(true);

		Platform.runLater(() -> {
			try {
				view.changeScene("FXMLMainBoard", 1980, 1080, true, "Lorenzo il magnifico");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}