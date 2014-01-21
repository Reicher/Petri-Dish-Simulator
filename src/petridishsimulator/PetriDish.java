 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import org.jsfml.graphics.*;
import org.jsfml.system.*;


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
        
        m_population = new Population(30, this);
        m_nutrientHolder = new NutrientHolder(50, this);
    }
    
    public void draw(RenderWindow window){
        window.draw(m_dishShape);
        
        m_nutrientHolder.draw(window);
        m_population.draw(window);
    }
    
    public void update(float dt)
    {
        m_population.update(dt, m_nutrientHolder);
        m_nutrientHolder.update(dt);
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
       
    private CircleShape m_dishShape;
    Vector2f m_centre; 
    
    public Population m_population;
    public NutrientHolder m_nutrientHolder;

}
