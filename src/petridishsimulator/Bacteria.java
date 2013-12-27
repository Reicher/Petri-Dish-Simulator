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
    public Bacteria(){
        m_size = new Gene(Gene.Fenotype.SIZE);
        m_energyStorage = new Gene(Gene.Fenotype.ENERGY_STORAGE);
        m_membrane = new Gene(Gene.Fenotype.MEMBRANE);
        m_red = new Gene(Gene.Fenotype.RED);
        m_green = new Gene(Gene.Fenotype.GREEN);
        m_blue = new Gene(Gene.Fenotype.BLUE);
        
        init();
    }
    
    public Bacteria(Bacteria copy)
    {
        m_size = new Gene(copy.m_size);
        m_energyStorage = new Gene(copy.m_energyStorage);
        m_membrane = new Gene(copy.m_membrane);
        m_red = new Gene(copy.m_red);
        m_green = new Gene(copy.m_green);
        m_blue = new Gene(copy.m_blue);

        init();
    }
    
    private void init(){
        m_fillColor = new Color((int)m_red.getValue(), 
                        (int)m_green.getValue(), 
                        (int)m_blue.getValue(), 
                        255);
              
        m_outlineColor = Color.BLACK;
        
        m_shape = new CircleShape(m_size.getValue());
        m_shape.setFillColor(m_fillColor);
        m_shape.setOutlineColor(m_outlineColor);
        m_shape.setOutlineThickness(m_membrane.getValue());
        
        m_shape.setOrigin(new Vector2f(m_size.getValue()+m_membrane.getValue(), 
                                       m_size.getValue()+m_membrane.getValue()));
        
        m_energy = 6.0f;
        m_health = 10.0f;
        stateOfDecay = 1.0f;
    }
    
    public void draw(RenderWindow window){
        window.draw(m_shape);
    }
    
    public void update(float dt, Nutrient closestFood){
        m_targetFood = closestFood;
        
        // what to do?
        // DIE
        if(m_fillColor.a <= 0)
            m_activity =  Activity.DECAYED;
        if(m_health <= 0)      
            m_activity =  Activity.DEAD;
        // SPLIT
        else if(m_energy >= m_energyStorage.getValue() && m_health >= 10.0f)
            m_activity =  Activity.SPLIT;
        // EAT
        else if(foodInReach(closestFood))
            m_activity =  Activity.EAT;
        // MOVE TO FOOD
        else
            m_activity = Activity.MOVE_TO_FOOD;

        // Energy, hold within limits and starve when 0
        if(m_energy <= 0){
            m_health -= 1 * dt;
            m_energy = 0.0f;
        } else if (m_energy >= m_energyStorage.getValue())
            m_energy = m_energyStorage.getValue();
        
        // Health, hold within limits and die when 0, regenerate otherwise
        if(m_energy > 0 && m_health < 10.0f){
            m_health += 0.5 * dt;
            m_energy -= 0.5 * dt;
            if(m_health > 100)
                m_health = 100;
        }
        m_energy -= 0.5 * dt; // it exhausting to live
        //System.out.println("H: " + m_health + " E: " + m_energy);
    }
    
            
    public Nutrient Eat(float dt){
            float bite = m_size.getValue() / 20.0f * dt;
            m_energy += bite;
            m_targetFood.eatOf(bite);
            return m_targetFood;
    }
    
    public Bacteria Split(float dt){
        Bacteria tmp = new Bacteria(this); 
        tmp.setPosition(HelperStuff.getPosWithin(getPosition(), 
                                                 tmp.getSize() + getSize()));
        m_energy = m_energyStorage.getValue() * 0.75f;
        m_health = 10 * 0.75f;
        
        return tmp;
    }
    
    public void moveToFood(float dt)
    {
        if(m_targetFood == null)
            return;
        
        m_energy -= m_size.getValue() / 20.0f * dt;
        
        Vector2f distVec = Vector2f.sub(m_targetFood.getPosition(),
            getPosition());
        
        Vector2f moveVec = HelperStuff.makeUnit(distVec);
        float speed = (40.0f / m_size.getValue()) * 20.0f;
        m_shape.move(Vector2f.mul(moveVec, dt*speed));
    }
    
    public void decay(float dt){
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
    }
    
        
    private boolean foodInReach(Nutrient food)
    {
        if(food == null)
            return false;
        
        float dist2Food = HelperStuff.distance(
                food.getPosition(), getPosition()) - 
                food.getSize();
        
        if(dist2Food < 0)
            return true;
        
        return false;
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
    
    public Activity getActivity(){
        return m_activity;
    }
    
    private CircleShape m_shape;
    
    private float m_health;
    private float m_energy;
    private Color m_fillColor;
    private Color m_outlineColor;
    private Activity m_activity;
    private float stateOfDecay; 
    
    private Nutrient m_targetFood;
    
    public Gene m_size;
    public Gene m_energyStorage;
    public Gene m_membrane;
    public Gene m_red;
    public Gene m_green;
    public Gene m_blue;
    
    public enum Activity {
    MOVE_TO_FOOD, EAT, SPLIT, DEAD, DECAYED
    }
}
