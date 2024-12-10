package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
public abstract class Character extends GameObject implements Interactable {
    private static final int TRAP_DAMAGE = 10;

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

    public int getDamage() {
        return damage;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public void attack(Character target, Room room) {

        target.setHealth(target.getHealth() - this.getDamage());

        ImageGUI.getInstance().setStatusMessage(
                target.getName() + " was attacked! Life: " + target.getHealth());

    }
    public void setHealth(int hp) {
        this.health = hp;
    }

    public void setDamage(int dmg) {
        this.damage = dmg;
    }

    public void checkIfDead(Room room) {
        if (this.getHealth() <= 0) {
            ImageGUI.getInstance().setStatusMessage(this.getName() + " has died!");
            room.getGameObjects().remove(this);
            ImageGUI.getInstance().removeImage(this);
        }
    }

    public void move(Direction direction, Room room) {
        Point2D newPosition = getPosition().plus(direction.asVector());

        if (newPosition.getX() < 0 || newPosition.getY() < 0 || newPosition.getX() > 9 || newPosition.getY() > 9 ){
            return;
        }

        for (GameObject obj : room.getObjectsAt(newPosition)) {
            if (obj instanceof Character) {
                Character other = (Character) obj;
                this.attack(other, room);
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

    public void applyGravity(Room room) {
        Point2D belowPosition = getPosition().plus(Direction.DOWN.asVector());
        if (!room.isCollision(belowPosition) && !room.isClimbable(belowPosition)) {
            setPosition(belowPosition);
        }
    }

    public void checkForTraps(Room room) {
        if (room.isTrap(getPosition())) {
            takeDamage(TRAP_DAMAGE);
            ImageGUI.getInstance().setStatusMessage(getName() + " triggered a trap! Health: " + getHealth() + "/" + room.getManel().getMaxHealth());
        }
    }

    public void update(Room room) {
        applyGravity(room);
        checkForTraps(room);
        checkIfDead(room);
    }

    public boolean isInteractable() {
        return true;
    }
}