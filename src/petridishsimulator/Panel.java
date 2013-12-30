/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import org.jsfml.graphics.*;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.*;
import org.jsfml.window.event.MouseEvent;

/**
 *
 * @author regen
 */
public class Panel {
    public Panel(Vector2f size, Vector2f position){
        m_size = size;
        m_position = position;
        
        m_borders = new RectangleShape(size);
        m_borders.setPosition(position);
        m_borders.setFillColor(new Color(255, 155, 155, 255));
        m_borders.setOutlineThickness(-3);
        m_borders.setOutlineColor(new Color(0, 0, 0, 255));
    }
    
    protected Vector2f m_size;
    protected Vector2f m_position;
    
    private RectangleShape m_borders;
    
    public void draw(RenderWindow window){
        window.draw(m_borders);
    }
    
    public void clickedSomewhere(MouseEvent mouseEvent){
        
    }
    
    public void update(float dt){}
}
