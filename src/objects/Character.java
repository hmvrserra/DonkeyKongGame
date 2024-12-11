package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class Character extends GameObject implements Collidable {
    private static final int TRAP_DAMAGE = 20;
    private static final int MAX_HEALTH = 50;

    private int health;
    private int damage;

    public Character(Point2D position, int layer, int health, int damage) {
        super(position, layer);
        this.health = health;
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    public int getDamage() {
        return damage;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public void attack(Character target) {

        target.takeDamage(this.getDamage());

        ImageGUI.getInstance().setStatusMessage(
                target.getName() + " was attacked! Life: " + target.getHealth() + "/" + target.getMaxHealth());

    }
    public void setHealth(int hp) {
        this.health = hp;
    }

    public void setDamage(int dmg) {
        this.damage = dmg;
    }

    public boolean isDead() {
        if (this.getHealth() <= 0) {
            return true;
        }
        return false;
    }

    public void move(Direction direction) {
        Room room = GameEngine.getInstance().getCurrentRoom();

        Point2D newPosition = getPosition().plus(direction.asVector());

        if (newPosition.getX() < 0 || newPosition.getY() < 0 || newPosition.getX() > 9 || newPosition.getY() > 9 ){
            return;
        }

        for (GameObject obj : room.getObjectsAt(newPosition)) {
            if (obj instanceof Character) {
                Character other = (Character) obj;
                this.attack(other);
                return;
            }
        }

        if (direction == Direction.UP || direction == Direction.DOWN) {
            boolean canClimb = false;

            if (direction == Direction.UP) {
                Point2D belowNewPosition = newPosition.plus(Direction.DOWN.asVector());
                canClimb = room.isClimbable(belowNewPosition);
            } else if (direction == Direction.DOWN) {
                canClimb = room.isClimbable(newPosition);
            }

            if (!canClimb) {
                return;
            }
        }

        if (!room.isCollision(newPosition)) {
            setPosition(newPosition);
        }
    }

    public void applyGravity() {
        Room room = GameEngine.getInstance().getCurrentRoom();
        Point2D belowPosition = getPosition().plus(Direction.DOWN.asVector());
        if (room.canMoveTo(belowPosition)) {
            setPosition(belowPosition);
        }
    }

    public void checkForTraps() {
        Room room = GameEngine.getInstance().getCurrentRoom();
        for (GameObject obj : room.getObjectsAt(getPosition())) {
            if (obj instanceof Trap) {
                takeDamage(TRAP_DAMAGE);
                ImageGUI.getInstance().setStatusMessage(
                        getName() + " was hit by a trap! Life: " + getHealth() + "/" + getMaxHealth());
                break;
            }
        }
    }

    public void update() {
        applyGravity();
        checkForTraps();
    }
}