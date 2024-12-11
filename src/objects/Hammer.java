package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Hammer extends Item {
    public Hammer(Point2D position) {
        super(position, 1);
    }

    @Override
    public String getName() {
        return "Hammer";
    }

    @Override
    public void collect(Character character) {
        character.setDamage(character.getDamage() + 20);
        ImageGUI.getInstance().setStatusMessage(character.getName() + " collected a sword! Damage increased by 20. Damage: " + character.getDamage());
    }
}
