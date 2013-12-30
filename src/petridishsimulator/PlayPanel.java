/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import org.jsfml.graphics.*;
import org.jsfml.system.*;

import org.jsfml.system.Vector2f;
/**
 *
 * @author regen
 */
public class PlayPanel extends Panel {
    public PlayPanel(Vector2f size, Vector2f position){
        super(size, position);
        
        m_petriDish= new PetriDish(400.0f, new Vector2f(50.0f, 50.0f));
    }
    
    public void draw(RenderWindow window)
    {
        super.draw(window);
        m_petriDish.draw(window);
    }
    
    public void update(float dt){
        m_petriDish.update(dt);
    }
    
            
    private PetriDish m_petriDish;
}
