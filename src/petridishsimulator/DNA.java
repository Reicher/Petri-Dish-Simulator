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

       for(Fenotype fenotype : Fenotype.values()){ 
        m_genes[fenotype.ordinal()] = m_minLimit[fenotype.ordinal()]
                + ((float)Math.random() 
                    * (m_maxLimit[fenotype.ordinal()]
                    - m_minLimit[fenotype.ordinal()]));
        }
    }
    
    public DNA(DNA copy){
        setLimits();
        
        float mutationChance = 0.2f;
        float mutationPower = 0.2f;

        for(Fenotype fenotype : Fenotype.values()){ 
            //BLACK MUTATION MAGIC
            if((float)Math.random() > (1.0f-mutationChance)){ //Mutation
                float deviation = (m_maxLimit[fenotype.ordinal()] 
                                - m_minLimit[fenotype.ordinal()] )
                                * mutationPower * 0.5f;

                float lower = copy.getFenotype(fenotype) - deviation;
                float upper = copy.getFenotype(fenotype) + deviation;

                if(lower > m_minLimit[fenotype.ordinal()])
                    lower = m_minLimit[fenotype.ordinal()];
                
                if(upper > m_maxLimit[fenotype.ordinal()])
                    upper = m_maxLimit[fenotype.ordinal()];


                m_genes[fenotype.ordinal()] = lower + 
                        ((float)Math.random() * (upper - lower));
            }
            else
                m_genes[fenotype.ordinal()]  = copy.getFenotype(fenotype);
        }
    }
    
    private void setLimits(){
        for(Fenotype fenotype : Fenotype.values()){ 
            switch(fenotype){
                case SIZE:
                    m_minLimit[Fenotype.SIZE.ordinal()] = 20.0f;
                    m_maxLimit[Fenotype.SIZE.ordinal()] = 40.0f;
                    break;
                case ENERGY_STORAGE:
                    m_minLimit[Fenotype.ENERGY_STORAGE.ordinal()] = 5.0f;
                    m_maxLimit[Fenotype.ENERGY_STORAGE.ordinal()] = 10.0f;
                break;
                case MEMBRANE:
                    m_minLimit[Fenotype.MEMBRANE.ordinal()] = 1.0f;
                    m_maxLimit[Fenotype.MEMBRANE.ordinal()] = 5.0f;
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
                    
            }
        }
    }
        
    
    public enum Fenotype{
    SIZE, ENERGY_STORAGE, MEMBRANE, RED, GREEN, BLUE
    }
    
    public float getFenotype(Fenotype type){
        return m_genes[type.ordinal()];
    }
    
    private float[] m_genes = new float[Fenotype.values().length];
    private float[] m_maxLimit = new float[Fenotype.values().length];
    private float[] m_minLimit = new float[Fenotype.values().length];
}
