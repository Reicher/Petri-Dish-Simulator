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
public class DNA {
    
    public DNA(){
        setLimits();

        for(int feno = 0; feno != Fenotype.MUTATION_POWER.ordinal(); feno++){ 
        m_genes[feno] = m_minLimit[feno]
                + ((float)Math.random() 
                    * (m_maxLimit[feno]
                    - m_minLimit[feno]));
        }
       
       setRelativeDNA();
    }
    
    public DNA(DNA copy){
        setLimits();
        
        float mutationChance = 0.2f;
        float mutationPower = 0.2f;

        for(int feno = 0; feno != Fenotype.MUTATION_POWER.ordinal(); feno++){ 
            //BLACK MUTATION MAGIC
            if((float)Math.random() > 
                    (1.0f-copy.getFenotype(Fenotype.MUTATION_CHANCE))){
                
                float deviation = (m_maxLimit[feno] 
                                - m_minLimit[feno] )
                                * copy.getFenotype(Fenotype.MUTATION_POWER) 
                                * 0.5f;

                float lower = copy.getFenotype(Fenotype.values()[feno]) - deviation;
                float upper = copy.getFenotype(Fenotype.values()[feno]) + deviation;

                if(lower > m_minLimit[feno])
                    lower = m_minLimit[feno];
                
                if(upper > m_maxLimit[feno])
                    upper = m_maxLimit[feno];


                m_genes[feno] = lower + ((float)Math.random() * (upper - lower));
            }
            else
                m_genes[feno]  = copy.getFenotype(Fenotype.values()[feno]);
        }
        setRelativeDNA();
    }
    
    private void setRelativeDNA()
    {
         for(Fenotype fenotype : Fenotype.values()){
            switch(fenotype){
                case MAX_HEALTH:
                    float RelativeRange = m_maxLimit[fenotype.ordinal()] 
                        - m_minLimit[fenotype.ordinal()]; 
                    m_genes[fenotype.ordinal()] = m_minLimit[fenotype.ordinal()] 
                        + (RelativeRange 
                        * getRelativeStrength(Fenotype.SIZE));
                    break;
            }

        }
        
    }
    
    private void setLimits(){
        for(Fenotype fenotype : Fenotype.values()){ 
            switch(fenotype){
                case SIZE:
                    m_minLimit[Fenotype.SIZE.ordinal()] = 5.0f;
                    m_maxLimit[Fenotype.SIZE.ordinal()] = 20.0f;
                    break;
                case ENERGY_STORAGE:
                    m_minLimit[Fenotype.ENERGY_STORAGE.ordinal()] = 5.0f;
                    m_maxLimit[Fenotype.ENERGY_STORAGE.ordinal()] = 10.0f;
                break;
                case MEMBRANE:
                    m_minLimit[Fenotype.MEMBRANE.ordinal()] = 1.0f;
                    m_maxLimit[Fenotype.MEMBRANE.ordinal()] = 3.0f;
                break;
                case RED:
                    m_minLimit[Fenotype.RED.ordinal()] = 0.0f;
                    m_maxLimit[Fenotype.RED.ordinal()] = 255.0f;
                break;
                case GREEN:
                    m_minLimit[Fenotype.GREEN.ordinal()] = 0.0f;
                    m_maxLimit[Fenotype.GREEN.ordinal()] = 255.0f;
                break;
                case BLUE:
                    m_minLimit[Fenotype.BLUE.ordinal()] = 0.0f;
                    m_maxLimit[Fenotype.BLUE.ordinal()] = 255.0f;
                break;
                case MUTATION_CHANCE:
                    m_minLimit[Fenotype.BLUE.ordinal()] = 0.0f;
                    m_maxLimit[Fenotype.BLUE.ordinal()] = 1.0f;
                break;
                case MUTATION_POWER:
                    m_minLimit[Fenotype.BLUE.ordinal()] = 0.0f;
                    m_maxLimit[Fenotype.BLUE.ordinal()] = 1.0f;
                break;
                // END OF RANDOM FENOTYPES
                case MAX_HEALTH:
                    m_minLimit[Fenotype.MAX_HEALTH.ordinal()] = 1.0f;
                    m_maxLimit[Fenotype.MAX_HEALTH.ordinal()] = 10.0f;
                    break;
                    
            }
        }
    }
        
    
    public enum Fenotype{
    SIZE, ENERGY_STORAGE, MEMBRANE, RED, GREEN, BLUE, MUTATION_CHANCE
    , MUTATION_POWER, // Always last of random genes
    MAX_HEALTH // Always first of relative genes
    }
    
    public float getFenotype(Fenotype type){
        
        return m_genes[type.ordinal()];
    }
    
    private float getRelativeStrength(Fenotype type){
        float value = getFenotype(type) - m_minLimit[type.ordinal()];
        float range = m_maxLimit[type.ordinal()]- m_minLimit[type.ordinal()];
        return value / range;
    }
    
    private float[] m_genes = new float[Fenotype.values().length];
    private float[] m_maxLimit = new float[Fenotype.values().length];
    private float[] m_minLimit = new float[Fenotype.values().length];
}
