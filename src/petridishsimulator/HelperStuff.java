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
public class HelperStuff {
    private HelperStuff(){}
    public static Vector2f polar2Vec2(float r, float t)
    {
        return new Vector2f((float)(r * Math.cos(t)), 
                            (float)(r * Math.sin(t)));
    }
    
    public static float distance(Vector2f first, Vector2f second){
        return vector2length( Vector2f.sub(first, second));
    }
    
    public static float vector2length(Vector2f vec){
        return (float)Math.sqrt(Math.pow(vec.x, 2) + Math.pow(vec.y, 2));
    }
    
    public static Vector2f makeUnit(Vector2f vec){
        return Vector2f.div(vec, HelperStuff.vector2length(vec));
    }
}
