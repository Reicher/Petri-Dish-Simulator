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
    static Vector2f polar2Vec2(float r, float t)
    {
        return new Vector2f((float)(r * Math.cos(t)), 
                            (float)(r * Math.sin(t)));
    }
    
    static float Vector2length(Vector2f vec){
        return (float)Math.sqrt(Math.pow(vec.x, 2) + Math.pow(vec.y, 2));
    }
}
