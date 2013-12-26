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
public class Nutrient {
    
    public Nutrient(RenderWindow window, Vector2f pos, float size, float dens){
        m_window = window;
        m_shape = new CircleShape(size);
        m_shape.setOrigin(size, size);
        m_shape.setPosition(pos);
        m_fillColor = new Color(100, 255, 50, (int)(dens*255.0f));
        m_shape.setFillColor(m_fillColor);
        m_density = dens;
        m_isEaten = false;
        
        m_id = maxId;
        maxId++;
    }
    
    public void draw(){
        m_window.draw(m_shape);
    }
    
    public void eatOf(float dt){
        
        m_density -= 0.01 * dt;
        m_fillColor = new Color(m_fillColor.r, 
                        m_fillColor.g, 
                        m_fillColor.b, 
                        (int)(m_density*255.0f));
        
        m_shape.setFillColor(m_fillColor);
        
        if(m_density <= 0)
            m_isEaten = true;
    }
    
    public boolean isEaten(){
        return m_isEaten;
    }
    
    public Vector2f getPosition(){
        return m_shape.getPosition();
    }
    
    public float getSize(){
        return m_shape.getRadius();
    }
    
    public int getId(){
        return m_id;
    }
    
    private RenderWindow m_window;
    private CircleShape m_shape;
    private float m_density;
    private Color m_fillColor;
    
    private int m_id;
    private static int maxId = 0;
    
    private boolean m_isEaten;
}
