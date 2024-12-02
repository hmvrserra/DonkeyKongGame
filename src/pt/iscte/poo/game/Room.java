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
	private String filename;
	private Manel manel;
	private List<GameObject> gameObjects;
	private char doorSymbol; // Símbolo da porta atual
	
	public Room(String filename) {
		this.filename = filename;
		this.gameObjects = new ArrayList<>();
        loadRoomFromFile(filename);
    }
	
	public void loadRoomFromFile(String filename) {

        try (Scanner scanner = new Scanner(new File(filename))) {
			addBackground();
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
						case 'm':
							gameObjects.add(new Hammer(position));
							break;
                    }
                }
                y++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        }
    }

	private Point2D findStartingPositionForManel() { // Encontra a posição mais abaixo e à esquerda possível para Manel
		for (int x = 0; x <= 9; x++){
	        for (int y = 9; y >= 0; y--){
	            Point2D position = new Point2D(x, y);
				boolean isOccupied = false;
	            for (GameObject obj : gameObjects) {
	                if (isCollision(position)) {
	                    isOccupied = true;
						break;
	                }
	            }
				if (!isOccupied)
	            	return position;
	        }
	    }
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

	public boolean isInterectable(Point2D position) {
		for (GameObject obj : gameObjects) {
			if (obj instanceof Interactable && obj.getPosition().equals(position)) {
				return true;
			}
		}
		return false;
	}
	public boolean isClimbable(Point2D position) {
		for (GameObject obj : gameObjects) {
			if (obj.canClimb() && obj.getPosition().equals(position)) {
				return true;
			}
		}
		return false;
	}

	public boolean isTrap(Point2D position) {
		for (GameObject obj : gameObjects) {
			if (obj instanceof Trap && obj.getPosition().equals(position)) {
				return true;
			}
		}
		return false;
	}

	public void removeItem(Point2D position) {
		for (GameObject obj : gameObjects) {
			if (obj instanceof Collectable && obj.getPosition().equals(position)) {
				gameObjects.remove(obj);
				ImageGUI.getInstance().removeImage(obj);
				break;
			}
		}
	}

	public void update() {
		manel.update(this);
	}
	public Manel getManel() {
		return this.manel;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public String getFilename() {
		return filename;
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
