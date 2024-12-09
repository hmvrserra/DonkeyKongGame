package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import java.util.Random;

public class DonkeyKong extends Character implements Collidable {

    private final Random random ;
    public DonkeyKong(Point2D position) {
        super(position, 2 , 100, 20);
        this.random = new Random();

    }
    
    @Override
    public String getName() {
        return "DonkeyKong";
    }

    @Override
    public void update(Room room) {
        super.update(room); // Aplica gravidade e combate

        // Gera movimento aleatório
        int moveChoice = random.nextInt(3); // 0 = left, 1 = right, 2 = stay
        Direction direction;
        switch (moveChoice) {
            case 0: direction = Direction.LEFT; break;
            case 1: direction = Direction.RIGHT; break;
            default: return; // Fica parado
        }

        move(direction, room); // Usa o método genérico de movimentação com combate

        throwBanana(room); // DonkeyKong atira bananas
    }

    public void throwBanana(Room room) {
        Point2D bananaPosition = getPosition().plus(new Vector2D(0, 1));
        Banana banana = new Banana(bananaPosition); // Banana moves right
        room.addGameObject(banana); // Add banana to the room

        System.out.println("DonkeyKong threw a banana at " + bananaPosition);
    }
}
