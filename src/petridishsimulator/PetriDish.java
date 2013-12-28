/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import org.jsfml.graphics.*;
import org.jsfml.system.*;

import java.util.*;

/**
 *
 * @author regen
 */
public class PetriDish {
    public PetriDish(float size, Vector2f pos){
        float radius = size/2.0f;
        float petriEdge = -5;
        
        // Dish shape
        m_dishShape = new CircleShape(radius);
        m_dishShape.setFillColor(new Color(230, 230, 230, 150));
        m_dishShape.setOutlineColor(new Color(200, 200, 200, 200));
        m_dishShape.setOutlineThickness(petriEdge);
        m_dishShape.setPosition(pos.x, pos.y);
        m_centre = new Vector2f(pos.x + radius, pos.y + radius);

        
        m_bacteria = new ArrayList<Bacteria>();
        for(int i = 0; i < 10; i = i +1)
            createRandomBacteria();
        
        m_nutrient = new ArrayList<Nutrient>();  
        for(int i = 0; i < 20; i = i +1)
            createRandomNutrient();
        
        m_foodClock = 0.0f;
        m_nextNutrient = 0.0f;
    }
    
    public void draw(RenderWindow window){
        window.draw(m_dishShape);
        
        for(Nutrient food : m_nutrient){
            food.draw(window);
        }
        
        for(Bacteria bacteria : m_bacteria){
            bacteria.draw(window);
        }
    }
    
    private Nutrient getClosestNutrient(Vector2f pos){
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
    
    public void update(float dt)
    {
        List<Bacteria> newGuys = new ArrayList<Bacteria>();
        
        for(Bacteria bacTmp : m_bacteria){
            bacTmp.update(dt, getClosestNutrient(bacTmp.getPosition()));

            if(bacTmp.getActivity() == Bacteria.Activity.EAT){
                Nutrient tmp = bacTmp.Eat(dt);
                // So dumb, fucking java
                for(Nutrient food : m_nutrient)
                    if(food.getId() == tmp.getId())
                        m_nutrient.set(m_nutrient.indexOf(food), tmp);
            }
            else if(bacTmp.getActivity() == Bacteria.Activity.SPLIT)
                newGuys.add(bacTmp.Split(dt));
            else if(bacTmp.getActivity() == Bacteria.Activity.DEAD)
               bacTmp.decay(dt);
            else if(bacTmp.getActivity() == Bacteria.Activity.MOVE_TO_FOOD)
                bacTmp.moveToFood(dt);
        }
        m_bacteria.addAll(newGuys);
        
        removeDeadStuff();
        
        for(Nutrient food : m_nutrient)
            food.update(dt);
        
        //new food!
        if(m_nextNutrient <= m_foodClock){
            createRandomNutrient();
            m_foodClock = 0.0f;
            m_nextNutrient = (float)Math.random() * 1.0f;
        }
        else
            m_foodClock += dt;
        

    }
    
    private void removeDeadStuff(){
        
        //Food
        Iterator<Nutrient> foodIt = m_nutrient.iterator();
        while (foodIt.hasNext()) {
            Nutrient foodTmp = foodIt.next();
            if(foodTmp.isEaten())
                foodIt.remove();
        }
        
        //bacteria
        Iterator<Bacteria> bacIt = m_bacteria.iterator();
        while (bacIt.hasNext()) {
            Bacteria bacTmp = bacIt.next();
            if(bacTmp.getActivity() == Bacteria.Activity.DECAYED)
                bacIt.remove();
        }
    }
    
    private Vector2f getRandomPositionWithinDish(float size){
        float t = (float)(Math.random() * Math.PI*2);
        float r = (float)(Math.random() * m_dishShape.getRadius() - size);

        return Vector2f.add( m_centre, HelperStuff.polar2Vec2(r, t));
    }
    
    private void createRandomNutrient(){
        float size = 3.0f + (float)Math.random() * 20.0f;
        Nutrient tmp = new Nutrient( getRandomPositionWithinDish(size),
                                     size);
        m_nutrient.add(tmp);
    }
    
    private void createRandomBacteria()
    {
        Bacteria tmp = new Bacteria();

        // Set initial position within the dish
        tmp.setPosition(getRandomPositionWithinDish(tmp.getSize()));

        m_bacteria.add(tmp);
    }
    
    private CircleShape m_dishShape;
    Vector2f m_centre; 
    
    private List<Bacteria> m_bacteria;
    
    private List<Nutrient> m_nutrient;
    
    private float m_nextNutrient;
    private float m_foodClock;
    
}
