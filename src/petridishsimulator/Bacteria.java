/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import java.text.DecimalFormat;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
/**
 *
 * @author regen
 */

public class Bacteria {
    public Bacteria(){
        m_dNA = new DNA();
        init();
    }
    
    public Bacteria(Bacteria copy)
    {
        m_dNA = new DNA(copy.getDna());
        init();
    }
    
    private void init(){
        m_fillColor = new Color((int) m_dNA.getFenotype(DNA.Trait.RED), 
                        (int)m_dNA.getFenotype(DNA.Trait.GREEN), 
                        (int)m_dNA.getFenotype(DNA.Trait.BLUE), 
                        180);
              
        m_outlineColor = Color.BLACK;
        
        m_shape = new CircleShape(m_dNA.getFenotype(DNA.Trait.SIZE));
        m_shape.setFillColor(m_fillColor);
        m_shape.setOutlineColor(m_outlineColor);
        m_shape.setOutlineThickness(m_dNA.getFenotype(DNA.Trait.MEMBRANE));
        
        m_shape.setOrigin(new Vector2f(
                m_shape.getRadius()+m_shape.getOutlineThickness(),
                m_shape.getRadius()+m_shape.getOutlineThickness()));
        
        m_maxHealth = m_dNA.getFenotype(DNA.Trait.MAX_HEALTH);
        m_maxEnergy = 10.0f - m_dNA.getFenotype(DNA.Trait.METABOLISM) *10.0f;
        m_speed = m_dNA.getFenotype(DNA.Trait.SPEED);
        m_metabolism = m_dNA.getFenotype(DNA.Trait.METABOLISM);

        
        m_energy = m_maxEnergy * 0.7f;
        m_health = m_maxHealth * 0.7f;
        m_foodInBelly = getSize() / 3.0f;
        stateOfDecay = 1.0f;

    }
    
    public void draw(RenderWindow window){
        window.draw(m_shape);
        
                //System.out.println(m_metabolism);
        /*DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(1);
        System.out.println("SPEED: " + df.format(m_speed) 
                + " Energy: " + df.format(m_energy) 
                + " Health: " + df.format(m_health));*/
    }
    
    public void updateWants(){
        // what to do?
        if(m_fillColor.a <= 0)// Decayed
            m_activity =  Activity.DECAYED;
        else if(m_health <= 0)// Dead      
            m_activity =  Activity.DEAD;
        else if(m_energy >= m_maxEnergy * 0.9f // SPLIT
            && m_health >= m_maxHealth * 0.9f)
            m_activity =  Activity.SPLIT;        
        else if(foodInReach(m_targetFood)) // Food is in reach, EAT
            m_activity =  Activity.EAT;
        else // MOVE TO FOOD
            m_activity = Activity.MOVE_TO_FOOD;
    }
    
    public void update(float dt, Nutrient closestFood){
        m_targetFood = closestFood;
        
        updateWants();
        
        // food. hold withing limits
        if(m_foodInBelly > getSize() / 2.0f)
            m_foodInBelly = getSize() / 2.0f;        
        if (m_foodInBelly > 0.0f){
            m_energy +=  m_metabolism * dt;
            m_foodInBelly -= 0.5 * m_metabolism * dt;
        }
        
        // Energy, hold within limits and starve when 0
        if(m_energy <= 0){
            m_health -= 1 * dt;
            m_energy = 0.0f;
        } else if (m_energy >= m_maxEnergy)
            m_energy = m_maxEnergy;
        
        // Health, hold within limits and die when 0, regenerate otherwise
        if(m_energy > 0 && m_health <m_maxHealth && m_activity != Activity.DEAD){
            m_health +=  m_metabolism * dt;
            m_energy -= m_metabolism * dt;
            if(m_health > m_maxHealth)
                m_health =m_maxHealth;
        }
        m_energy -= m_metabolism * getMass()* 0.5 * dt; // it exhausting to live
        
        //System.out.println("H: " + m_health + " E: " + m_energy);
    }
     
    public Nutrient Eat(float dt){

        float bite = getSize() * 0.1f * dt;
        m_foodInBelly += bite;
        m_targetFood.eatOf(bite);
        return m_targetFood;
    }
    
    public Bacteria Split(float dt){
        //System.out.println("Splitting");
        Bacteria tmp = new Bacteria(this);

        tmp.setPosition( HelperStuff.getPosWithin(getPosition(), 
                         getSize() + tmp.getSize()));
        
        m_energy = m_maxEnergy * 0.5f;
        m_health = m_maxHealth * 0.5f;
        
        return tmp;
    }
    
    public void moveToFood(float dt)
    {
        if(m_targetFood == null)
            return;
        float energyLoss = 0.5f *  getMass() * (float)Math.pow(m_speed, 2) * dt;
        //System.out.println(energyLoss);
        m_energy -=energyLoss *0.1;
        
        Vector2f distVec = Vector2f.sub(m_targetFood.getPosition(),
            getPosition());
        
        Vector2f moveVec = HelperStuff.makeUnit(distVec);
        m_shape.move(Vector2f.mul(moveVec, dt*m_speed));
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
                food.getSize() - getSize();
        
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
    
    public float getTraitRelativeStrenght(DNA.Trait feno){
        return m_dNA.getRelativeStrength(feno);
    }
    
    public float getSize(){
        return m_shape.getRadius() + m_shape.getOutlineThickness();
    }
    
    public float getMass(){
        return m_shape.getRadius() * 0.05f + m_shape.getOutlineThickness() * 0.1f;
    }
    
    private DNA getDna(){
        return m_dNA;
    }
    
    public Activity getActivity(){
        return m_activity;
    }
    
    private CircleShape m_shape;
     
    private float m_maxHealth;
    private float m_maxEnergy;
    private float m_speed;
    private float m_metabolism;
    private float m_foodInBelly;
    
    private float m_health;
    private float m_energy;
    private Color m_fillColor;
    private Color m_outlineColor;
    private Activity m_activity;
    private float stateOfDecay; 
    
    private Nutrient m_targetFood;
   
    private DNA m_dNA;
    
    public enum Activity {
    MOVE_TO_FOOD, EAT, SPLIT, DEAD, DECAYED
    }
}
