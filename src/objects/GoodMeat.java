package objects;

import pt.iscte.poo.utils.Point2D;

public class GoodMeat extends Item {
    public GoodMeat(Point2D position) {
        super(position, 1);
    }

    @Override
    public String getName() {
        return "GoodMeat";
    }

    @Override
    public void collect(Character character) {
        character.setHealth(character.getHealth() + 20);
        System.out.println(character.getName() + " collected a good meat! Health increased by 20.");
    }
}
