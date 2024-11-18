package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class Character extends GameObject implements Collidable {
    private int health;
    private int damage;

    public Character(Point2D position , int layer, int health, int damage) {
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

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void move(Direction direction, Room room) {
        Point2D newPosition = getPosition().plus(direction.asVector());

        if (newPosition.getX() < 0 || newPosition.getY() < 0 || newPosition.getX() >= 10 || newPosition.getY() >= 10) {
            return;
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
        Point2D belowPosition = getPosition().plus(new Vector2D(0, 1)); // Position directly below the character
        if (!room.isCollision(belowPosition)) {
            setPosition(belowPosition);
        }
    }

    public void update(Room room) {
        applyGravity(room);
    }

}
