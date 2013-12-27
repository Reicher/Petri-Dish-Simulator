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
public class Panel {
    public Panel(Vector2f size, Vector2f position, Texture texture){
        m_size = size;
        m_position = position;
        m_texture = texture;
        
        m_sprite = new Sprite();
        m_sprite.setTexture(m_texture);
        m_sprite.setPosition(position);
    }
    
    private Vector2f m_size;
    private Vector2f m_position;
    
    private Texture m_texture;
    private Sprite m_sprite;
    
    public void draw(RenderWindow window){
        window.draw(m_sprite);
    }
    
    public void update(float dt){}
}
