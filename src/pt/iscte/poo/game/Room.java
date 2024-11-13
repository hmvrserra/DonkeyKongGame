package pt.iscte.poo.game;

import objects.Manel;
import objects.Stairs;
import objects.Sword;
import objects.Trap;
import objects.Wall;
import objects.DonkeyKong;
import objects.Floor;
import objects.GoodMeat;
import objects.Hammer;
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
	
	private Point2D heroStartingPosition = new Point2D(0, 8);
	private Manel manel;
	private List<ImageTile> gameObjects = new ArrayList<>();
	
	public Room() {
        this.manel = new Manel(heroStartingPosition);
		ImageGUI.getInstance().addImage(manel);
        gameObjects = new ArrayList<>();
    }
	
	public void loadFromFile(String fileName) {
	    try (Scanner scanner = new Scanner(new File(fileName))) {
	        if (scanner.hasNextLine()) {
	            // Ignora a primeira linha
	            scanner.nextLine();
	        }

	        int y = 0; // Coordenada Y para cada linha (começando na segunda linha)
	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            for (int x = 0; x < line.length(); x++) { // Coordenada X para cada caractere
	                char symbol = line.charAt(x);
	                ImageTile obj = createObjectFromSymbol(symbol, x, y);
	                if (obj != null) {
	                    gameObjects.add(obj);
	                    ImageGUI.getInstance().addImage(obj);
	                }
	            }
	            y++; // Move para a próxima linha
	        }
	    } catch (IOException e) {
	        System.err.println("Erro ao ler o arquivo: " + e.getMessage());
	    }
	}
	
	private ImageTile createObjectFromSymbol(char symbol, int x, int y) {
	    Point2D position = new Point2D(x, y);

	    switch (symbol) {
	        case 'W':
	            return new Wall(position);
	        case 'F':
	            return new Floor(position);
	        case 'S':
	            return new Stairs(position); // Representa uma escada
	        case 'H':
	            return new Hammer(position); // Representa um martelo
	        case 's':
	            return new Sword(position); // Representa uma espada
	        case 't':
	            return new Trap(position); // Representa uma armadilha
	        case 'G':
	            return new DonkeyKong(position); // Representa o Donkey Kong
	        case 'b':
	            return new GoodMeat(position); // Representa carne
	        default:
	            return null; // Ignora caracteres desconhecidos
	    }
	}
	
	// Método para encontrar a posição mais à esquerda acima da linha de paredes mais baixa
//	private Point2D findLeftmostPositionAboveLowestWall() {
//	    int lowestWallY = -1;
//
//	    // Encontra a linha mais baixa com Wall
//	    for (ImageTile obj : gameObjects) {
//	        if (obj instanceof Wall && obj.getPosition().getY() > lowestWallY) {
//	            lowestWallY = obj.getPosition().getY();
//	        }
//	    }
//
//	    // Define a posição inicial do Manel, logo acima e à esquerda da linha de Wall mais baixa
//	    for (int x = 0; x < 10; x++) { // Supondo uma largura de 10 blocos
//	        Point2D potentialPosition = new Point2D(x, lowestWallY - 1);
//
//	        // Verifica se a posição está livre (sem outros objetos)
//	        boolean positionIsFree = true;
//	        for (ImageTile obj : gameObjects) {
//	            if (obj.getPosition().equals(potentialPosition)) {
//	                positionIsFree = false;
//	                break;
//	            }
//	        }
//
//	        if (positionIsFree) {
//	            return potentialPosition;
//	        }
//	    }
//
//	    // Se não encontrar uma posição disponível (caso improvável), retorna a posição padrão
//	    return new Point2D(1, lowestWallY - 1);
//	}
	
	public List<ImageTile> getGameObjects() {
        return gameObjects;
    }

	public void moveManel(Direction direction) {
	    // Define os limites da sala (ajuste conforme o tamanho real da sala)
	    int roomWidth = 10;
	    int roomHeight = 10;

	    // Calcula a nova posição com base na direção do movimento
	    Point2D newPosition = manel.getPosition().plus(direction.asVector());

	    // Verifica se a nova posição está dentro dos limites da sala
	    if (newPosition.getX() < 0 || newPosition.getX() >= roomWidth ||
	        newPosition.getY() < 0 || newPosition.getY() >= roomHeight) {
	        // Se a nova posição está fora dos limites, cancela o movimento
	        return;
	    }

	    // Verifica se a nova posição contém um objeto de tipo Wall (ou outro obstáculo)
	    boolean canMove = true;
	    for (ImageTile obj : gameObjects) {
	        if (obj.getPosition().equals(newPosition) && obj instanceof Wall) {
	            canMove = false; // Se houver um Wall na nova posição, o movimento é bloqueado
	            break;
	        }
	    }

	    // Se não houver obstáculos e a posição estiver dentro dos limites, atualiza a posição de Manel
	    if (canMove) {
	        manel.setPosition(newPosition);
	        ImageGUI.getInstance().update(); // Atualiza a GUI para refletir o movimento
	    }
	}


	
}