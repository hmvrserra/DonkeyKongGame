package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.gui.ImageTile;

import java.util.ArrayList;
import java.util.List;

public class GameEngine implements Observer {
    
    private Room currentRoom = new Room();
    private int lastTickProcessed = 0;
    private List<Room> rooms = new ArrayList<>(); // Lista para armazenar todas as salas carregadas
    private int currentRoomIndex = 2; // Índice da sala atual
    
    public GameEngine() {
        // Carrega todas as salas no início do jogo
        loadAllRooms();

        // Carrega a primeira sala na GUI
        loadRoom(currentRoomIndex);
        ImageGUI.getInstance().update();
    }

    @Override
    public void update(Observed source) {
        if (ImageGUI.getInstance().wasKeyPressed()) {
            int key = ImageGUI.getInstance().keyPressed();
            System.out.println("Key pressed: " + key);
            if (Direction.isDirection(key)) {
                Direction direction = Direction.directionFor(key);
                Room currentRoom = rooms.get(currentRoomIndex);            
                currentRoom.moveManel(direction); // Move o Manel na sala atual
            }
        } else {
        	int t = ImageGUI.getInstance().getTicks();
            while (lastTickProcessed < t) {
                processTick();
            }
        }
        
        ImageGUI.getInstance().update();
    }


    private void processTick() {
        System.out.println("Tic Tac : " + lastTickProcessed);
        lastTickProcessed++;
    }

    private void loadAllRooms() {
        // Lista de arquivos de sala a serem carregados (adapte conforme a sua estrutura de arquivos)
        String[] roomFiles = {"rooms/room0.txt", "rooms/room1.txt", "rooms/room2.txt"};

        for (String roomFile : roomFiles) {
            Room room = new Room();
            room.loadFromFile(roomFile);
            rooms.add(room); // Adiciona a sala carregada à lista de salas
        }
    }

    // Carrega uma sala específica na GUI (nesse caso, apenas a primeira sala para inicialização)
    private void loadRoom(int roomIndex) {
        Room room = rooms.get(roomIndex);

        // Limpa a GUI e carrega todos os objetos da sala atual na GUI
        ImageGUI.getInstance().clearImages();
        for (ImageTile obj : room.getGameObjects()) {
            ImageGUI.getInstance().addImage(obj);
        }
    }
}
