package pt.iscte.poo.game;

import objects.*;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Room {
	private Manel manel;
	private List<GameObject> gameObjects;
	private char doorSymbol; // Símbolo da porta atual (ex: '0')
	private String nextRoomFile; // Nome do próximo ficheiro de sala, se houver
	
	public Room() {
        gameObjects = new ArrayList<>();
		addBackground();
        loadRoomFromFile("rooms/room0.txt");
        ImageGUI.getInstance().addImages(gameObjects);
    }
	
	private void loadRoomFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Ignorar a primeira linha
            }
            int y = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (int x = 0; x < line.length(); x++) {
                    char ch = line.charAt(x);
                    Point2D position = new Point2D(x, y);
                    switch (ch) {
                        case 'W':
                            gameObjects.add(new Wall(position));
                            break;
                        case 'S':
                            gameObjects.add(new Stairs(position));
                            break;
                        case 'H':
                            this.manel = new Manel(position);
                            gameObjects.add(manel);
                            break;
                        case 'G':
                            gameObjects.add(new DonkeyKong(position));
                            break;
                        case 't':
                            gameObjects.add(new Trap(position));
                            break;
                        case 's':
                            gameObjects.add(new Sword(position));
                            break;
                        case '0':
                            gameObjects.add(new Door(position));
                            break;
                        //ADICIONAR O RESTO DOS OBJETOS
                    }
                }
                y++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        }
    }

	private Point2D findStartingPositionForManel() { // Encontra a posição mais abaixo e à esquerda possível para Manel
	    int maxY = 9;
	    int maxX = 9;

	    for (int y = maxY; y >= 0; y--) {
	        for (int x = 0; x <= maxX; x++) {
	            Point2D position = new Point2D(x, y);
	            boolean positionIsFree = true;

	            // Verifica se há algum objeto na posição para além de um 'Black' (layer 0)
	            for (GameObject obj : gameObjects) {
	                if (obj.getPosition().equals(position) && obj.getLayer() > 0) {
	                    positionIsFree = false;
	                    break;
	                }
	            }

	            // Retorna a primeira posição livre encontrada
	            if (positionIsFree) {
	                return position;
	            }
	        }
	    }

	    // Retorna uma posição padrão caso não encontre uma posição livre (caso improvável)
	    return new Point2D(1, 1);
	}
	
	public void moveManel(Direction direction, Room room) {
		manel.move(direction, room);
	}

	public void addBackground() {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				gameObjects.add(new Floor(new Point2D(x, y)));
			}
		}
	}

	public boolean isCollision(Point2D position) {
		for (GameObject obj : gameObjects) {
			if (obj instanceof Collidable && obj.getPosition().equals(position)) {
				return true;
			}
		}
		return false;
	}

	public void update() {
		manel.update(this);
	}

	@Override

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (GameObject gameObject : gameObjects) {
			sb.append(gameObject.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
}
