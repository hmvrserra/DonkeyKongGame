package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;
public class Sword extends Item {

    public Sword(Point2D position) {
        super(position, 1);
    }
    
    @Override
    public String getName() {
        return "Sword";
    }

    @Override
    public void collect(Character character) {
        character.setDamage(character.getDamage() + 15);
        ImageGUI.getInstance().setStatusMessage(character.getName() + " collected a sword! Damage increased by 15. Damage: " + character.getDamage());
    }
}
