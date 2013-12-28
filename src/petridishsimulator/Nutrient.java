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
    
    public Nutrient(Vector2f pos, float size){
        m_shape = new CircleShape(size);
        m_shape.setOrigin(size, size);
        m_shape.setPosition(pos);
        m_fillColor = new Color(100, 255, 50, 0);
        m_shape.setFillColor(m_fillColor);
        m_density = 0.0f;
        m_isEaten = false;
        
        m_id = maxId;
        maxId++;
    }
    
    public void draw(RenderWindow window){
        window.draw(m_shape);
    }
    
    public void update(float dt){
        //grow
        if(!m_isEaten){
            m_density += 0.01 * dt;
            
            if (m_density > 0.2f)
                m_density = 0.2f;
                
            updateColor();
        }
    }
    
    public void eatOf(float biteSize){
        
        m_density -= 0.01 * biteSize;
        updateColor();
        
        if(m_density <= 0)
            m_isEaten = true;
    }
    
    public void updateColor(){
        m_fillColor = new Color(m_fillColor.r, 
                m_fillColor.g, 
                m_fillColor.b, 
                (int)(m_density*255.0f));
        
        m_shape.setFillColor(m_fillColor);
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
