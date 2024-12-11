package pt.iscte.poo.game;

import objects.*;
import objects.Character;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Room {
	private Manel manel;
	private List<GameObject> gameObjects;
	Map<java.lang.Character, String> doorDestinations = new HashMap<>();
	private String fileName;
	
	public Room(String filename) {
		this.fileName = filename;
		this.gameObjects = new ArrayList<>();
        loadRoomFromFile(filename);
    }
	
	public String getFileName() {
        return this.fileName;
    }
	
	private void loadRoomFromFile(String filename) {

        try (Scanner scanner = new Scanner(new File(filename))) {
            addBackground();
            int y = 0;
            boolean hasMatrixStarted = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.startsWith("#") && !hasMatrixStarted) {
                    String[] parts = line.substring(1).split(";");
                    if (parts.length == 2) {
                        char doorSymbol = parts[0].charAt(0);
                        String destination = parts[1];
                        doorDestinations.put(doorSymbol, destination);
                    }
                } else {
                    hasMatrixStarted = true;
                    if (!line.isEmpty()) {
                        processLineWithValidation(line, y, filename);
                        y++;
                    } else {
                        // Falta uma linha inteira
                        System.err.println("Error: Missing line in file '" + filename + "' at expected row " + y);
                        terminateGame("File is incomplete: missing rows in the file.");
                    }
                }
            }

            // Verifica falta alguma linha
            if (y < 10) {
                System.err.println("Error: File '" + filename + "' has fewer rows than expected.");
                terminateGame("File is incomplete: fewer rows than expected.");
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
            System.out.print("Please provide the filename for the next level: ");
            try (Scanner consoleScanner = new Scanner(System.in)) {
                String newFilename = consoleScanner.nextLine();
                loadRoomFromFile(newFilename);
            } catch (Exception ex) {
                terminateGame("File loading failed: unable to recover from missing file.");
            }
        }
    }
	
	private void processLineWithValidation(String line, int y, String filename) {
    	int lineLength = 10;

    	for (int x = 0; x < lineLength; x++) {
    		if (x < line.length()) {
                char symbol = line.charAt(x);
                GameObject obj = createObjectFromSymbol(symbol, x, y);
                if (obj != null) {
                    gameObjects.add(obj);
                } else {
                    // Caractere desconhecido
                    System.err.println("Warning: Unknown character '" + symbol + "' at position (" + x + ", " + y + ") in file '" + filename + "'");
                    gameObjects.add(new Floor(new Point2D(x, y)));
                }
            } else {
                // Falta caracteres
                System.err.println("Warning: Missing character at position (" + x + ", " + y + ") in file '" + filename + "'");
                gameObjects.add(new Floor(new Point2D(x, y)));
            }
        }
    }
	
	private GameObject createObjectFromSymbol(char symbol, int x, int y) {
        Point2D position = new Point2D(x, y);

        if (doorDestinations.containsKey(symbol)) {
            return new Door(position, doorDestinations.get(symbol));
        }

        switch (symbol) {
            case 'W': return new Wall(position);
            case ' ': return new Floor(position);
            case 'S': return new Stairs(position);
            case 's': return new Sword(position);
            case 't': return new Trap(position);
            case 'G': return new DonkeyKong(position);
            case 'b': return new GoodMeat(position);
            case 'm': return new Hammer(position);
            case 'P': return new Princess(position);
            case 'H':
                manel = new Manel(position);
                return manel;
            default: return null;
        }
    }

	private void terminateGame(String message) {
		System.err.println(message);
		ImageGUI.getInstance().dispose();
		System.exit(1);
	}
	
	public void moveManel(Direction direction) {
		manel.move(direction);
	}

	public void addBackground() {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				gameObjects.add(new Floor(new Point2D(x, y)));
			}
		}
	}

    public boolean canMoveTo(Point2D position) {
        return !isCollision(position) && !isClimbable(position);
    }


    public boolean isCollision(Point2D position) {
		for (GameObject obj : gameObjects) {
			if (obj instanceof Collidable && obj.getPosition().equals(position)) {
				return true;
			}
		}
		return false;
	}
	public boolean isClimbable(Point2D position) {
		for (GameObject obj : gameObjects) {
			if (obj instanceof Climbable && obj.getPosition().equals(position)) {
				return true;
			}
		}
		return false;
	}
	public void removeUpdate() {
		for (GameObject obj : gameObjects) {
			if (obj instanceof Item && manel.getPosition().equals(obj.getPosition())) {
                ((Item) obj).collect(manel);
				gameObjects.remove(obj);
				ImageGUI.getInstance().removeImage(obj);
				break;
			}else if (obj instanceof Banana && manel.getPosition().equals(obj.getPosition())) {
                getManel().setHealth(getManel().getHealth() - 10);
                gameObjects.remove(obj);
                ImageGUI.getInstance().removeImage(obj);
                ImageGUI.getInstance().setStatusMessage("JumpMan was hit by a banana! Health: " + getManel().getHealth() + "/" + getManel().getMaxHealth());
                break;
            }else if (obj instanceof Character) {
                Character character = (Character) obj;
                if (character.isDead()){
                    gameObjects.remove(obj);
                    ImageGUI.getInstance().removeImage(obj);
                    break;
                }
            }
		}
	}

	public void addGameObject(GameObject obj) {
		gameObjects.add(obj);
		ImageGUI.getInstance().addImage(obj);
	}

    private void removeGameObject(GameObject obj) {
        gameObjects.remove(obj);
        ImageGUI.getInstance().removeImage(obj);
    }

    public void reset() {
        gameObjects.clear();
        loadRoomFromFile(fileName);
        ImageGUI.getInstance().clearImages();
        ImageGUI.getInstance().addImages(gameObjects);
    }
    public void update() {
        updateCharacters();
        updateBananas();
        checkVictory();
    }

    private void updateCharacters() {
        for (GameObject obj : new ArrayList<>(gameObjects)) {
            if (obj instanceof Character) {
                ((Character) obj).update();
                if (((Character) obj).isDead()) {
                    removeGameObject(obj);
                }
            }
        }
    }

    private void updateBananas() {
        for (GameObject obj : new ArrayList<>(gameObjects)) {
            if (obj instanceof Banana) {
                Banana banana = (Banana) obj;
                banana.fall();
                if (banana.getPosition().getY() > 9) {
                    removeGameObject(banana);
                }
            }
        }
    }

    private void checkVictory() {
        for (GameObject obj : gameObjects) {
            if (obj instanceof Princess && manel.getPosition().equals(obj.getPosition())) {
                declareVictory();
            }
        }
    }

    private void declareVictory() {
        ImageGUI.getInstance().showMessage("Congratulations! You've won",  "JumpMan saved the Princess!");
        System.out.println("Victory! JumpMan has saved the Princess!");
        System.exit(0);
    }

    public Manel getManel() {
		return this.manel;
	}
	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public String getFilename() {
		return fileName;
	}

	public List<GameObject> getObjectsAt(Point2D position) {
        List<GameObject> objectsAtPosition = new ArrayList<>();
        for (GameObject obj : gameObjects) {
            if (obj.getPosition().equals(position)) {
                objectsAtPosition.add(obj);
            }
        }
        return objectsAtPosition;
    }
}
