/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;
/**
 *
 * @author regen
 */
public class DNA {
    
    public DNA(){
        setLimits();

        for(int feno = 0; feno != Trait.MAX_HEALTH.ordinal(); feno++){ 
        m_genes[feno] = m_minLimit[feno]
                + ((float)Math.random() 
                    * (m_maxLimit[feno]
                    - m_minLimit[feno]));
        }
       setRelativeDNA();
       
       //System.out.print(this);
    }
    
    public DNA(DNA copy){
        setLimits();
        
        float mutationChance = copy.getFenotype(Trait.MUTATION_CHANCE);
        float mutationPower = copy.getFenotype(Trait.MUTATION_POWER);
        
        //System.out.println("Chance: " + mutationChance);
        //System.out.println("Power: " + mutationPower);
        
        for(Trait feno : Trait.values()){ 
             
            if(feno == Trait.MAX_HEALTH)
                break; // dont not do this kind of mutation on relative genes.
            
            //BLACK MUTATION MAGIC
            if((float)Math.random() > (1.0f-mutationChance)){
                
                float deviation = (m_maxLimit[feno.ordinal()] 
                                - m_minLimit[feno.ordinal()] )
                                * mutationPower * 0.5f;

                float lower = copy.getFenotype(feno) - deviation;
                float upper = copy.getFenotype(feno) + deviation;

                if(lower < m_minLimit[feno.ordinal()])
                    lower = m_minLimit[feno.ordinal()];
                
                if(upper > m_maxLimit[feno.ordinal()])
                    upper = m_maxLimit[feno.ordinal()];

                m_genes[feno.ordinal()] = 
                        lower + ((float)Math.random() * (upper - lower));
            }
            else
                m_genes[feno.ordinal()]  = copy.getFenotype(feno);
        }
        setRelativeDNA();
    }
    
    private void setRelativeDNA()
    {
        float RelativeRange;
         for(Trait fenotype : Trait.values()){
            switch(fenotype){
                case MAX_HEALTH:
                    RelativeRange = m_maxLimit[fenotype.ordinal()] 
                        - m_minLimit[fenotype.ordinal()]; 
                    m_genes[fenotype.ordinal()] = m_minLimit[fenotype.ordinal()] 
                        + (RelativeRange 
                        * getRelativeStrength(Trait.SIZE));
                    break;
                case SPEED:
                    RelativeRange = m_maxLimit[fenotype.ordinal()] 
                        - m_minLimit[fenotype.ordinal()]; 
                    m_genes[fenotype.ordinal()] = m_minLimit[fenotype.ordinal()] 
                        + (RelativeRange 
                        * getRelativeStrength(Trait.METABOLISM));
                    break;
            }

        }
        
    }
    
    private void setLimits(){
        for(Trait fenotype : Trait.values()){ 
            switch(fenotype){
                case SIZE:
                    m_minLimit[fenotype.ordinal()] = 4.0f;
                    m_maxLimit[fenotype.ordinal()] = 13.0f;
                    break;
                case MEMBRANE:
                    m_minLimit[fenotype.ordinal()] = 1.0f;
                    m_maxLimit[fenotype.ordinal()] = 3.0f;
                break;
                case RED:
                    m_minLimit[fenotype.ordinal()] = 0.0f;
                    m_maxLimit[fenotype.ordinal()] = 255.0f;
                break;
                case GREEN:
                    m_minLimit[fenotype.ordinal()] = 0.0f;
                    m_maxLimit[fenotype.ordinal()] = 255.0f;
                break;
                case BLUE:
                    m_minLimit[fenotype.ordinal()] = 0.0f;
                    m_maxLimit[fenotype.ordinal()] = 255.0f;
                break;
                case MUTATION_CHANCE:
                    m_minLimit[fenotype.ordinal()] = 0.1f;
                    m_maxLimit[fenotype.ordinal()] = 0.9f;
                    break;
                case METABOLISM:
                    m_minLimit[fenotype.ordinal()] = 0.1f;
                    m_maxLimit[fenotype.ordinal()] = 1.0f;
                    break;
                case MUTATION_POWER:
                    m_minLimit[fenotype.ordinal()] = 0.1f;
                    m_maxLimit[fenotype.ordinal()] = 0.9f;
                    break;
                // END OF RANDOM FENOTYPES
                case MAX_HEALTH:
                    m_minLimit[fenotype.ordinal()] = 1.0f;
                    m_maxLimit[fenotype.ordinal()] = 5.0f;
                    break;
                case SPEED:
                    m_minLimit[fenotype.ordinal()] = 1.0f;
                    m_maxLimit[fenotype.ordinal()] = 10.0f;
                    break;                    
                    
            }
        }
    }
        
    
    public enum Trait{
        SIZE, MEMBRANE, RED, GREEN, BLUE, MUTATION_CHANCE, METABOLISM
        , MUTATION_POWER, // Always last of random genes
        MAX_HEALTH, SPEED; // Always first of relative genes
    
         public Trait getNext() {
         return this.ordinal() < Trait.values().length - 1
             ? Trait.values()[this.ordinal() + 1]
             : null;
            }
          public Trait getPrev() {
         return this.ordinal() > 0
             ? Trait.values()[this.ordinal() - 1]
             : null;
            }
    }
    
    public float getFenotype(Trait type){
        return m_genes[type.ordinal()];
    }
    
    public float getRelativeStrength(Trait type){
        float value = getFenotype(type) - m_minLimit[type.ordinal()];
        float range = m_maxLimit[type.ordinal()]- m_minLimit[type.ordinal()];
        return value / range;
    }
    
    public String toString(){
        String string = new String();
        for(Trait feno : Trait.values())
        {
            string += feno.toString() + ": " + m_genes[feno.ordinal()] + "\n";
        }
        return string;
    }
    
    private float[] m_genes = new float[Trait.values().length];
    private static float[] m_maxLimit = new float[Trait.values().length];
    private static float[] m_minLimit = new float[Trait.values().length];
}
