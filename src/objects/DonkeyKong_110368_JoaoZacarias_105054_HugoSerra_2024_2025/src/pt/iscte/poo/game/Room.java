package pt.iscte.poo.game;

import objects.Manel;
import objects.Stairs;
import objects.Sword;
import objects.Trap;
import objects.Wall;
import objects.DonkeyKong;
import objects.Floor;
import objects.GameObject;
import objects.GoodMeat;
import objects.Hammer;
import objects.Door;
import objects.Black;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Room {
	
	private Manel manel;
	private List<GameObject> gameObjects;
	private char doorSymbol; // Símbolo da porta atual (ex: '0')
	private String nextRoomFile; // Nome do próximo ficheiro de sala, se houver
	
	public Room() {
        gameObjects = new ArrayList<>();
    }
	
	public void loadFromFile(String fileName) {
	    try (Scanner scanner = new Scanner(new File(fileName))) {
	    	int maxY = 9;
	        int maxX = 9;

	        // Primeiro, preenche toda a matriz com Black (layer 0)
	        for (int y = 0; y <= maxY; y++) {
	            for (int x = 0; x <= maxX; x++) {
	                Point2D position = new Point2D(x, y);
	                Black blackTile = new Black(position);
	                gameObjects.add(blackTile);
	                ImageGUI.getInstance().addImage(blackTile);
	            }
	        }
	    	int y = 0; // Inicia `y` em 0 para começar da primeira linha da matriz

	        if (scanner.hasNextLine()) {
	            String firstLine = scanner.nextLine();
	            // Verifica se a primeira linha contém informações da porta
	            if (firstLine.startsWith("#")) {
	                String[] parts = firstLine.substring(1).split(";");
	                if (parts.length == 2) {
	                    doorSymbol = parts[0].charAt(0);
	                    nextRoomFile = parts[1];
	                }
	            } else {
	                // Se a primeira linha não começar com '#', trata-se da última sala,
	                // então essa linha faz parte da matriz dos objetos.
	                processLine(firstLine, y); // Processa a primeira linha como parte da matriz
	                y++; // Incrementa `y` somente após processar a primeira linha da matriz
	            }
	        }

	        // Processa as restantes linhas
	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            processLine(line, y);
	            y++;
	        }

	        // Define a posição inicial do Manel após carregar todos os objetos
	        Point2D startingPosition = findStartingPositionForManel();
	        manel = new Manel(startingPosition);
	        gameObjects.add(manel);
	        ImageGUI.getInstance().addImage(manel);

	    } catch (IOException e) {
	        System.err.println("Erro ao ler o arquivo: " + e.getMessage());
	    }
	}

	// Método auxiliar para processar cada linha da matriz de objetos
	private void processLine(String line, int y) {
	    for (int x = 0; x < line.length(); x++) {
	        char symbol = line.charAt(x);
	        GameObject obj = createObjectFromSymbol(symbol, x, y);
	        if (obj != null) {
	            gameObjects.add(obj);
	            ImageGUI.getInstance().addImage(obj);
	        }
	    }
	}
	
	private GameObject createObjectFromSymbol(char symbol, int x, int y) {
	    Point2D position = new Point2D(x, y);

	    switch (symbol) {
	        case 'W':
	            return new Wall(position);
	        case 'F':
	            return new Floor(position);
	        case 'S':
	            return new Stairs(position);
	        case 'H':
	            return new Hammer(position);
	        case 's':
	            return new Sword(position);
	        case 't':
	            return new Trap(position);
	        case 'G':
	            return new DonkeyKong(position);
	        case 'b':
	            return new GoodMeat(position);
	        default:
	            if (doorSymbol != '\0' && symbol == doorSymbol) {
	                return new Door(position);
	            }
	            return null;
	    }
	}
	
	// Encontra a posição mais abaixo e à esquerda possível para Manel
	private Point2D findStartingPositionForManel() {
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

	
	public List<GameObject> getGameObjects() {
        return gameObjects;
    }

	public void moveManel(Direction direction) {
	    int roomWidth = 10;
	    int roomHeight = 10;

	    Point2D newPosition = manel.getPosition().plus(direction.asVector());

	    // Verifica se a nova posição está dentro dos limites da sala
	    if (newPosition.getX() < 0 || newPosition.getX() >= roomWidth ||
	        newPosition.getY() < 0 || newPosition.getY() >= roomHeight) {
	        return;
	    }

	    // Lógica para movimentos verticais (subida e descida)
	    if (direction == Direction.UP || direction == Direction.DOWN) {
	        boolean canClimb = false;

	        if (direction == Direction.UP) {
	            // Verifica se há um objeto escalável abaixo da nova posição
	            Point2D belowNewPosition = newPosition.plus(Direction.DOWN.asVector());
	            canClimb = isClimbableAtPosition(belowNewPosition);
	        } else if (direction == Direction.DOWN) {
	            // Verifica se há um objeto escalável diretamente abaixo do Manel
	            Point2D belowCurrentPosition = manel.getPosition().plus(Direction.DOWN.asVector());
	            canClimb = isClimbableAtPosition(belowCurrentPosition);
	        }

	        if (!canClimb) {
	            return;
	        }
	    }

	    // Verifica se a posição de destino é passável
	    boolean canMove = true;
	    for (GameObject obj : gameObjects) {
	        if (obj.getPosition().equals(newPosition) && !obj.isPassable()) {
	            canMove = false;
	            break;
	        }
	    }

	    if (canMove) {
	        manel.setPosition(newPosition);
	        ImageGUI.getInstance().update();
	        
	        // Depois de mover, verifica se o Manel precisa de "cair"
	        fallIfNoSupport();
	    }
	}

	// Método auxiliar para verificar se uma posição contém um objeto escalável
	private boolean isClimbableAtPosition(Point2D position) {
	    for (GameObject obj : gameObjects) {
	        if (obj.getPosition().equals(position) && obj.isClimbable()) {
	            return true;
	        }
	    }
	    return false;
	}
	
	// Método auxiliar para verificar se há suporte por baixo do Manel
	private boolean hasSupportBelow(Point2D position) {
	    Point2D belowPosition = position.plus(Direction.DOWN.asVector());
	    for (GameObject obj : gameObjects) {
	        if (obj.getPosition().equals(belowPosition) && obj.isSupport()) {
	            return true;
	        }
	    }
	    return false;
	}
	
	// Método que faz o Manel "cair" até encontrar suporte
	private void fallIfNoSupport() {
	    while (!hasSupportBelow(manel.getPosition())) {
	        Point2D fallPosition = manel.getPosition().plus(Direction.DOWN.asVector());

	        // Verifica se a posição de queda está dentro dos limites
	        if (fallPosition.getY() >= 10) {
	            break;
	        }

	        manel.setPosition(fallPosition);
	        ImageGUI.getInstance().update();

	        try {
	            Thread.sleep(50); // Pequena pausa para visualizar a queda
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	    }
	}
	
}