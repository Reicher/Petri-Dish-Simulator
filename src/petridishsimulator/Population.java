/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

/**
 *
 * @author regen
 */
public class Population {
    public Population(int size, PetriDish dish){
        
        m_bacteria = new ArrayList<Bacteria>();
        
        for(int i = 0; i < 10; i = i +1){
            Bacteria tmp = new Bacteria();            
            tmp.setPosition(dish.getRandomPositionWithinDish(tmp.getSize()));
            m_bacteria.add(tmp);
        }
    }
    
    public void update(float dt, PetriDish dish){
        List<Bacteria> newGuys = new ArrayList<Bacteria>();
        Bacteria newTmp;
        
        for(Bacteria bacTmp : m_bacteria){
            bacTmp.update(dt, getClosestNutrient(bacTmp.getPosition(), dish.m_nutrient));

            if(bacTmp.getActivity() == Bacteria.Activity.EAT){
                Nutrient tmp = bacTmp.Eat(dt);
                // So dumb, fucking java
                for(Nutrient food : dish.m_nutrient)
                    if(food.getId() == tmp.getId())
                        dish.m_nutrient.set(dish.m_nutrient.indexOf(food), tmp);
            }
            else if(bacTmp.getActivity() == Bacteria.Activity.SPLIT){
                newTmp = bacTmp.Split(dt);
                newTmp.setPosition(dish.adjustPositionToDisc(newTmp.getPosition(), newTmp.getSize()));
                newGuys.add(newTmp);
            }
            else if(bacTmp.getActivity() == Bacteria.Activity.DEAD)
               bacTmp.decay(dt);
            else if(bacTmp.getActivity() == Bacteria.Activity.MOVE_TO_FOOD)
                bacTmp.moveToFood(dt);
        }
        m_bacteria.addAll(newGuys);
        
        removeTheDead();
    }
    
    public void removeTheDead(){
        //bacteria
        Iterator<Bacteria> bacIt = m_bacteria.iterator();
        while (bacIt.hasNext()) {
            Bacteria bacTmp = bacIt.next();
            if(bacTmp.getActivity() == Bacteria.Activity.DECAYED)
                bacIt.remove();
        }
    }
    
    public int[] getFenotypeSpreed(DNA.Trait fenotype){
        int[] spread = new int[10];

        for(Bacteria bacTmp : m_bacteria){
            float value = bacTmp.getTraitRelativeStrenght(fenotype);
            spread[(int)(value*10.0f)]++;
            
        }
        return spread;
    }
        
    private Nutrient getClosestNutrient(Vector2f pos, List<Nutrient> nutrients ){
        float closestDist = Float.MAX_VALUE;
        float dist;
        Nutrient closest = null;
        for(Nutrient food : nutrients){
            dist = HelperStuff.distance(pos, food.getPosition()) - food.getSize();
            if(dist < closestDist){
                closest = food;
                closestDist = dist;
            }
        }
        return closest;
    }
        
    public int getPopulationSize(){
        return m_bacteria.size();
    }
    
    public void draw(RenderWindow window){
        for(Bacteria bacteria : m_bacteria)
                bacteria.draw(window);
    }
    
    private List<Bacteria> m_bacteria;
}
