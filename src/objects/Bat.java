package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import java.util.Random;

public class Bat extends Character{
    
    private final Random random;
    public Bat(Point2D position) {
        super(position, 2, 1, 20);
        this.random = new Random();
    }
    
    @Override
    public int getMaxHealth() {
        return 1;
    }
    
    @Override
    public void update() {
        Room room = GameEngine.getInstance().getCurrentRoom();
        
        super.update();

        int moveChoice = random.nextInt(3); // 0 = esquerda, 1 = direita, 2 = ficar
        Direction direction;
        switch (moveChoice) {
            case 0: direction = Direction.LEFT; break;
            case 1: direction = Direction.RIGHT; break;
            default: return; // Fica parado
        }
        Point2D belowPosition = getPosition().plus(Direction.DOWN.asVector());
        if(room.isClimbable(belowPosition)) {
            direction = Direction.DOWN;
        }

        move(direction);
    }
    
    @Override
    public void attack(Character target) {
        super.attack(target);
        setHealth(0);
    }
    
    @Override
    public void takeDamage(int damage) {
    }
    
    
    @Override
    public void checkForTraps(){}
    
    @Override
    public void applyGravity(){}
    
    @Override
    public String getName() {
        return "Bat";
    }
    
    @Override
    public boolean canCollide() {
        return false;
    }
}
