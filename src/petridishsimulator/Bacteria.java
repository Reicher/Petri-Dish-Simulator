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
        
        m_activity = Activity.GOTO_FOOD;
        m_energy = 10;
        m_health = 10;
        stateOfDecay = 1.0f;
    }
    
    public void draw(){
        m_window.draw(m_shape);
    }
    
    public Nutrient update(float dt, Nutrient closestFood){
        // Ugly as hell
        m_energy -= 0.5 * dt; // it exhausting to live
        switch(m_activity)
        {
            case GOTO_FOOD:
                goToFood(dt, closestFood);
                break;
            case EATING:
                m_energy += 2.0 * dt;
                if(foodStillthere(closestFood))
                    closestFood.eatOf(dt);
                else 
                    m_activity =  Activity.GOTO_FOOD;
                break;
            case DEAD:
                decay(dt);
                break;
            default:
        }
        
        // No energy, starting to starve
        if(m_energy <= 0){
            m_health -= 1 * dt;
            m_energy = 0.0f;
        }
        // No health left, dead!
        if(m_health <= 0){
            m_activity =  Activity.DEAD;
            m_health = 0.0f;
        }
        
        return closestFood;
    }
    
    private boolean foodStillthere(Nutrient food)
    {
        if(food == null)
            return false;
        
        float dist2Food = HelperStuff.vector2length(
                Vector2f.sub(food.getPosition(), getPosition())) - 
                food.getSize();
        
        if(dist2Food < 0)
            return true;
        
        return false;
    }
    
    private void goToFood(float dt, Nutrient closestFood)
    {
        m_energy -= 2 * dt;
        if(closestFood == null)
            return;
        
        Vector2f distVec = Vector2f.sub(closestFood.getPosition(),
            getPosition());
        
        Vector2f moveVec = HelperStuff.makeUnit(distVec);
        m_shape.move(Vector2f.mul(moveVec, dt*20));
        
        if((HelperStuff.vector2length(distVec) - closestFood.getSize()) < 0)
            m_activity = Activity.EATING;
    }
    
    private void decay(float dt){
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
    WANDERING, GOTO_FOOD, EATING, SPLITTING, DEAD, DECAYED
    }
}
