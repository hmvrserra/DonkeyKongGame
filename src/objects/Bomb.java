package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;
public class Bomb extends Item{
    
    public Bomb(Point2D position) {
        super(position, 1);
    }
    
    @Override
    public String getName() {
        return "Bomb";
    }
    
    public void collect(Character character) {
        ((Manel) character).collectBomb();
        ImageGUI.getInstance().setStatusMessage(character.getName() + " picked up a bomb!");
    }
}
