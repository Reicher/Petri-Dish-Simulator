/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
/**
 *
 * @author regen
 */
public class Bacteria {
    public Bacteria(RenderWindow window){
        m_window = window;
        
        float radius = 20;
        float membraneThickness = 3;
        m_shape = new CircleShape(radius);
        m_shape.setFillColor(Color.MAGENTA);
        m_shape.setOutlineColor(Color.BLACK);
        m_shape.setOutlineThickness(membraneThickness);
        
        m_shape.setOrigin(new Vector2f(radius+membraneThickness, 
                                       radius+ membraneThickness));
    }
    
    public void draw(){
        m_window.draw(m_shape);
    }
    
    public void setPosition(Vector2f pos){
        m_shape.setPosition(pos);
    }
    
    public Vector2f getPosition(){
        return m_shape.getPosition();
    }
    
    public float getSize(){
        return m_shape.getRadius() + m_shape.getOutlineThickness();
    }
    
    private CircleShape m_shape;
    private RenderWindow m_window;
}
