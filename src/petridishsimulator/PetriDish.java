/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import java.util.*;
/**
 *
 * @author regen
 */
public class PetriDish {
    public PetriDish(RenderWindow window){
        m_window = window;
        
        float lowerBorder = 40;
        float petriEdge = 10;
        float radius = window.getSize().y/2.0f - lowerBorder - petriEdge;
        
        // Dish shape
        m_dishShape = new CircleShape(radius);
        m_dishShape.setFillColor(new Color(230, 230, 230));
        m_dishShape.setOutlineColor(new Color(200, 200, 200));
        m_dishShape.setOutlineThickness(petriEdge);
        m_dishShape.setOrigin(new Vector2f(radius+petriEdge, radius+petriEdge));
        m_dishShape.setPosition(radius+petriEdge*2, radius+petriEdge*2);
        
        // Border shape around dish, maybe should be handled elsewhere
        m_borderShape = new RectangleShape(new Vector2f(radius*2+petriEdge*2, 
                                                        radius*2+petriEdge*2));
        m_borderShape.setFillColor(Color.WHITE);
        m_borderShape.setOutlineThickness(5);
        m_borderShape.setOutlineColor(Color.BLACK);
        
        m_bacteria = new ArrayList<Bacteria>();
        for(int i = 0; i < 20; i = i +1)
            createRandomBacteria();
        
        m_nutrient = new ArrayList<Nutrient>();
        for(int i = 0; i < 10; i = i +1)
            createRandomNutrient();
    }
    
    public void draw(){
        m_window.draw(m_borderShape);
        m_window.draw(m_dishShape);
        
        for(Nutrient food : m_nutrient){
            food.draw();
        }
        
        for(Bacteria bacteria : m_bacteria){
            bacteria.draw();
        }
    }
    
    public void update(float dt)
    {
        for(Bacteria bacteria : m_bacteria){
            bacteria.update(dt);
            if(bacteria.isDecayed())
                m_bacteria.remove(bacteria);
        }
        
        for(Nutrient food : m_nutrient){
            if(food.isEaten())
                m_nutrient.remove(food);
        }
    }
    
    private Vector2f getRandomPositionWithinDish(float size){
        float t = (float)(Math.random() * Math.PI*2);
        float r = (float)(Math.random() * m_dishShape.getRadius()) 
                        - (size - m_dishShape.getOutlineThickness());

        return Vector2f.add( m_dishShape.getPosition(), 
                             HelperStuff.polar2Vec2(r, t));
    }
    
    private void createRandomNutrient(){
        float size = 10.0f + (float)Math.random() * 60.0f;
        float density = 0.1f + (float)Math.random() * 0.6f;
        Nutrient tmp = new Nutrient(  m_window, 
                                      getRandomPositionWithinDish(size),
                                      size, 
                                      0.4f);
        m_nutrient.add(tmp);
    }
    
    private void createRandomBacteria()
    {
        Bacteria tmp = new Bacteria(m_window);

        // Set initial position within the dish
        tmp.setPosition(getRandomPositionWithinDish(tmp.getSize()));

        m_bacteria.add(tmp);
    }
    
    private CircleShape m_dishShape;
    private RectangleShape m_borderShape;
    private RenderWindow m_window;
    
    private List<Bacteria> m_bacteria;
    private List<Nutrient> m_nutrient;
}
