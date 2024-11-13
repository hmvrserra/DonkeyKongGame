package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.gui.ImageTile;

public class GameEngine implements Observer {
	
	private Room currentRoom = new Room();
	private int lastTickProcessed = 0;
	
	public GameEngine() {
	    loadRoom("rooms/room0.txt");
	    ImageGUI.getInstance().update();
	}

	@Override
	public void update(Observed source) {
		
		if (ImageGUI.getInstance().wasKeyPressed()) {
			int k = ImageGUI.getInstance().keyPressed();
			System.out.println("Keypressed " + k);
			if (Direction.isDirection(k)) {
				System.out.println("Direction! ");
				Direction direction = Direction.directionFor(k);
				currentRoom.moveManel(direction);
			}
		}
		int t = ImageGUI.getInstance().getTicks();
		while (lastTickProcessed < t) {
			processTick();
		}
		ImageGUI.getInstance().update();
	}

	private void processTick() {
		System.out.println("Tic Tac : " + lastTickProcessed);
		lastTickProcessed++;
	}
	
	// Método para carregar uma sala a partir de um arquivo de configuração
	private void loadRoom(String fileName) {
		// Delegar a leitura e criação de objetos para a classe Room
		currentRoom.loadFromFile(fileName);
		// Adicionar todos os objetos carregados à GUI
		for (ImageTile obj : currentRoom.getGameObjects()) {
			ImageGUI.getInstance().addImage(obj);
		}
	}

}
