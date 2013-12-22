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
        for(int i = 0; i < 30; i = i +1)
            createRandomBacteria();
    }
    
    public void draw(){
        m_window.draw(m_borderShape);
        m_window.draw(m_dishShape);
        
        for(Bacteria bacteria : m_bacteria){
            bacteria.draw();
        }
    }
    
    public void createRandomBacteria()
    {
        Bacteria tmp = new Bacteria(m_window);
        boolean posOk = false;
        int tries = 0;
        Vector2f pos = Vector2f.ZERO;
        while(!posOk && tries < 10)
        {
            // Set initial position within the dish
            posOk = true;
            float t = (float)(Math.random() * Math.PI*2);
            float r = (float)(Math.random() * m_dishShape.getRadius()) 
                            - tmp.getSize() - m_dishShape.getOutlineThickness();

            pos = Vector2f.add( m_dishShape.getPosition(), 
                                HelperStuff.polar2Vec2(r, t));
            
            // Don't put bacteria on top of buddies!
            float length2buddy; 
            for(Bacteria bac : m_bacteria){
                length2buddy = HelperStuff.Vector2length(
                        new Vector2f(pos.x - bac.getPosition().x, 
                                     pos.y - bac.getPosition().y));
                if(length2buddy < (tmp.getSize() + bac.getSize())){
                    posOk = false;
                    break;
                }
            }
            tries++;
        }
        
        // did we find a spot to put it?
        if(posOk){
            tmp.setPosition(pos);
            m_bacteria.add(tmp);
        }
    }
    
    private CircleShape m_dishShape;
    private RectangleShape m_borderShape;
    private RenderWindow m_window;
    
    private List<Bacteria> m_bacteria;
}
