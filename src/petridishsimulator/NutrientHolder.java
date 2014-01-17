/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import java.util.ArrayList;
import java.util.Iterator;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

/**
 *
 * @author regen
 */
public class NutrientHolder {
    public NutrientHolder(int startNumber, PetriDish dish){
        m_dish = dish;
        m_nutrient = new ArrayList<Nutrient>();
        for(int i = 0; i < startNumber; i = i +1)
            createRandomNutrient();
        
        m_foodClock = 0.0f;
        m_nextNutrient = 0.0f;
    }
    
    private void createRandomNutrient(){
        float size = 3.0f + (float)Math.random() * 10.0f;
        Nutrient tmp = new Nutrient( m_dish.getRandomPositionWithinDish(size),
                                     size);
        m_nutrient.add(tmp);
    }
    
    private void removeTheEaten(){
        Iterator<Nutrient> foodIt = m_nutrient.iterator();
        while (foodIt.hasNext()) {
            Nutrient foodTmp = foodIt.next();
            if(foodTmp.isEaten())
                foodIt.remove();
        }
    }
    
    public void update(float dt){
        for(Nutrient food : m_nutrient)
            food.update(dt);
        
        removeTheEaten();
        
        //new food!
        if(m_nextNutrient <= m_foodClock){
            createRandomNutrient();
            m_foodClock = 0.0f;
            m_nextNutrient = (float)Math.random() * 0.5f;
        }
        else
            m_foodClock += dt;
    }
    
    public Nutrient getClosestNutrient(Vector2f pos ){
        float closestDist = Float.MAX_VALUE;
        float dist;
        Nutrient closest = null;
        for(Nutrient food : m_nutrient){
            dist = HelperStuff.distance(pos, food.getPosition()) - food.getSize();
            if(dist < closestDist){
                closest = food;
                closestDist = dist;
            }
        }
        return closest;
    }
    
    public void updateNutrientWith(Nutrient newOne){
        for(Nutrient food : m_nutrient)
            if(food.getId() == newOne.getId())
                m_nutrient.set(m_nutrient.indexOf(food), newOne);
    }
    
    public void draw(RenderWindow window){
        for(Nutrient food : m_nutrient)
            food.draw(window);
    }
    
    public float getNutrientSize(){
        float size = 0.0f;
        for(Nutrient food : m_nutrient){
            size += food.getValue();
        }
        return size;
    }
        
    public ArrayList<Nutrient> m_nutrient;
    private float m_nextNutrient;
    private float m_foodClock;
    
    private PetriDish m_dish;
}
