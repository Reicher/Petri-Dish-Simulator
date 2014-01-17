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
        float petriEdge = 7;
        
        // Dish shape
        m_dishShape = new CircleShape(radius);
        m_dishShape.setFillColor(new Color(230, 230, 230, 150));
        m_dishShape.setOutlineColor(new Color(180, 180, 180, 200));
        m_dishShape.setOutlineThickness(petriEdge);
        m_dishShape.setPosition(pos.x, pos.y);
        m_centre = new Vector2f(pos.x + radius, pos.y + radius);
        
        m_population = new Population(10, this);
        
        m_nutrient = new ArrayList<Nutrient>();  
        for(int i = 0; i < 40; i = i +1)
            createRandomNutrient();
        
        m_foodClock = 0.0f;
        m_nextNutrient = 0.0f;
    }
    
    public void draw(RenderWindow window){
        window.draw(m_dishShape);
        
        for(Nutrient food : m_nutrient){
            food.draw(window);
        }

        m_population.draw(window);
    }
    
    public void update(float dt)
    {
        m_population.update(dt, this);
        
        removeEatenFood();
        
        for(Nutrient food : m_nutrient)
            food.update(dt);
        
        //new food!
        if(m_nextNutrient <= m_foodClock){
            createRandomNutrient();
            m_foodClock = 0.0f;
            m_nextNutrient = (float)Math.random() * 0.5f;
        }
        else
            m_foodClock += dt;
    }
    
    public float getNutrientSize(){
        float size = 0.0f;
        for(Nutrient food : m_nutrient){
            size += food.getValue();
        }
        
        return size;
    }

    private void removeEatenFood(){
        //Food
        Iterator<Nutrient> foodIt = m_nutrient.iterator();
        while (foodIt.hasNext()) {
            Nutrient foodTmp = foodIt.next();
            if(foodTmp.isEaten())
                foodIt.remove();
        }
    }
    
    public Vector2f getRandomPositionWithinDish(float size){
        float t = (float)(Math.random() * Math.PI*2);
        float r = (float)(Math.random() * m_dishShape.getRadius() - size);

        return Vector2f.add( m_centre, HelperStuff.polar2Vec2(r, t));
    }
    
    public Vector2f adjustPositionToDisc(Vector2f pos, float size){
        if (m_dishShape.getRadius() > (HelperStuff.distance(m_centre, pos) + size))
            return pos;
        
        Vector2f inVec = HelperStuff.makeUnit(Vector2f.sub(pos, m_centre));
        pos = Vector2f.add(m_centre, Vector2f.mul(inVec, m_dishShape.getRadius() - size));
        return pos;
    }
    
    private void createRandomNutrient(){
        float size = 3.0f + (float)Math.random() * 10.0f;
        Nutrient tmp = new Nutrient( getRandomPositionWithinDish(size),
                                     size);
        m_nutrient.add(tmp);
    }
       
    private CircleShape m_dishShape;
    Vector2f m_centre; 
    
    public Population m_population;
    private List<Bacteria> m_bacteria;
    
    public List<Nutrient> m_nutrient;
    
    private float m_nextNutrient;
    private float m_foodClock;
    
}
