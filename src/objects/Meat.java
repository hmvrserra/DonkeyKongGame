package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Meat extends Item {
    private boolean isRotten;
    public Meat(Point2D position) {
        super(position, 1);
        this.isRotten = false;
    }

    public void setRotten() {
        this.isRotten = true;
    }

    @Override
    public String getName() {
        if (isRotten) {
            return "BadMeat";
        }
        return "GoodMeat";
    }

    @Override
    public void collect(Character character) {
        if (!isRotten) {
            character.setHealth(character.getHealth() + 20);
            ImageGUI.getInstance().setStatusMessage(character.getName() + " collected a good meat! Health: " + character.getHealth() + "/" + character.getMaxHealth());
        } else {
            character.setHealth(character.getHealth() - 10);
            ImageGUI.getInstance().setStatusMessage(character.getName() + " collected a rotten meat! Health: " + character.getHealth() + "/" + character.getMaxHealth());
        }
    }
}
