package objects;

import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class ExplodingBomb extends GameObject {
    
    private int lastTickProcessed = 0;
    private int tickNum = 10;
    public ExplodingBomb(Point2D position) {
        super(position, 1);
    }

    @Override
    public String getName() {
        return "Bomb";
    }

    public void explode() {
        Point2D position = getPosition();
        Room room = GameEngine.getInstance().getCurrentRoom();
        
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                Vector2D v = new Vector2D(i, j);
                Point2D newPosition = position.plus(v);
                for (GameObject obj : room.getObjectsAt(newPosition)) {
                    room.addGameObject(new FireOld(newPosition));
                    if (obj instanceof Item || obj instanceof Character) {
                        room.getManel().takeDamage(room.getManel().getMaxHealth());
                        break;
                    }
                }
            }
        }
    }
}
