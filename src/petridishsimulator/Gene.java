/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import org.jsfml.system.Vector2f;
/**
 *
 * @author regen
 */
public class Gene {
    public Gene(Fenotype type){
        
        m_fenotype = type;
        Vector2f limits = Vector2f.ZERO;
        
        switch(m_fenotype)
        {
            case SIZE:
                limits = new Vector2f(10.0f, 40.0f);
                break;
            case ENERGY_STORAGE:
                limits = new Vector2f(5.0f, 10.0f);
                break;
            case MEMBRANE:
                limits = new Vector2f(0.0f, 5.0f);
                break;
            case RED:
                limits = new Vector2f(0.0f, 255.0f);
                break;
            case GREEN:
                limits = new Vector2f(0.0f, 255.0f);
                break;
            case BLUE:
                limits = new Vector2f(0.0f, 255.0f);
                break;
        }
        
        m_value =  limits.x + ((float)Math.random() * (limits.y - limits.x));
    }

    public Gene(Gene gene){
        this(gene.getFenotype());
        
        float mutationRate = 0.2f;
        if(Math.random() > 1.0-mutationRate)
            m_value  = gene.getValue();
    }
    
    public Gene getGene(){
        Gene tmp = new Gene(getFenotype());



        return tmp;
    }
    
    public Fenotype getFenotype(){
        return m_fenotype;
    }
        
    public float getValue(){
        return m_value;
    }
    
    public enum Fenotype{
    SIZE, ENERGY_STORAGE, MEMBRANE, RED, GREEN, BLUE
    }
    
    private final Fenotype m_fenotype;
    private float m_value;
}
