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
        m_fillColor = Color.MAGENTA;
        m_outlineColor = Color.BLACK;
        
        m_shape = new CircleShape(radius);
        m_shape.setFillColor(m_fillColor);
        m_shape.setOutlineColor(m_outlineColor);
        m_shape.setOutlineThickness(membraneThickness);
        
        m_shape.setOrigin(new Vector2f(radius+membraneThickness, 
                                       radius+ membraneThickness));
        
        m_activity = Activity.WANDERING;
        m_energy = 10;
        m_health = 1;
        stateOfDecay = 1.0f;
    }
    
    public void draw(){
        m_window.draw(m_shape);
    }
    
    public void update(float dt){
        // Ugly as hell
        m_energy -= 0.5 * dt; // it exhausting to live
        switch(m_activity)
        {
            case WANDERING:
                m_energy -= 1 * dt;
                break;
            case MOVING_TO_TARGET:
                m_energy -= 2 * dt;
                break;
            case EATING:
                
                break;
            case DEAD:
                stateOfDecay -= dt*0.1;
                m_fillColor = new Color(m_fillColor.r, 
                                        m_fillColor.g, 
                                        m_fillColor.b, 
                                        (int)(m_fillColor.a * stateOfDecay));
                m_outlineColor = new Color(m_outlineColor.r, 
                                           m_outlineColor.g, 
                                           m_outlineColor.b, 
                                           (int)(m_outlineColor.a * stateOfDecay));
                
                m_shape.setFillColor(m_fillColor);
                m_shape.setOutlineColor(m_outlineColor);
                if(m_fillColor.a <= 0)
                    m_activity = Activity.DECAYED;
                break;
            default:
        }
        
        if(m_energy <= 0){
            m_health -= 1 * dt;
            m_energy = 0.0f;
        }
        if(m_health <= 0){
            m_activity =  Activity.DEAD;
            m_health = 0.0f;
        }
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
    
    public boolean isDecayed(){
        return m_activity == Activity.DECAYED;
    }
    
    private CircleShape m_shape;
    private RenderWindow m_window;
    
    private float m_health;
    private float m_energy;
    private Color m_fillColor;
    private Color m_outlineColor;
    private Activity m_activity;
    private float stateOfDecay; 
    
    private enum Activity {
    WANDERING, MOVING_TO_TARGET, EATING, SPLITTING, DEAD, DECAYED
    }
}
