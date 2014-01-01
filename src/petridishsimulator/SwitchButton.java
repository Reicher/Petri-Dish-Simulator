/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;




import java.io.IOException;

import java.io.InputStream;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseEvent;

/**
 *
 * @author regen
 */
public class SwitchButton {
    public SwitchButton(    Vector2f pos, 
                            String text, 
                            boolean pushed, 
                            boolean onlyPush){
        
        m_isPushed = pushed;
        m_onlyPush = onlyPush;

        // Load font
        m_buttonFont = new Font();
        try {
            InputStream istream = getClass().
                    getResourceAsStream("/Resources/00TT.TTF");
            m_buttonFont.loadFromStream(istream);
        } catch(IOException ex) {
            //Failed to load font
            ex.printStackTrace();
        }
        m_buttonText = new Text(text, m_buttonFont, 35);
        m_buttonText.setColor(Color.BLACK);
        m_buttonText.setPosition(Vector2f.add(pos, new Vector2f(10.0f, 0.0f)));
        
        m_shape = new RectangleShape(
                new Vector2f(m_buttonText.getLocalBounds().width+20, 50));
        m_shape.setPosition(pos);
        
        setLook();
    }
    
    public boolean isPushed(){
        return m_isPushed;
    }
    
    public void setUnPushed(){
        m_isPushed = false;
        
        setLook();
    }
    
    public void setPushed(){
        m_isPushed = true;
        
        setLook();
    }
    
    public void draw(RenderWindow window){
        window.draw(m_shape);
        window.draw(m_buttonText);
    }
    
    public boolean update(MouseEvent mouseEvent){
        if(mouseEvent.type != Event.Type.MOUSE_BUTTON_RELEASED 
                || !m_shape.getGlobalBounds().contains((float)mouseEvent.position.x, 
                                                       (float)mouseEvent.position.y))
            return false;
        if(!m_onlyPush)
            m_isPushed = !m_isPushed;
        else if(!m_isPushed) // this button can only be turned on
            m_isPushed = true;
        
        setLook();
        return true;
    }
    
    private void setLook(){
        if(m_isPushed){
            m_shape.setFillColor(new Color(140, 140, 140, 255));
            m_shape.setOutlineThickness(-5);
            m_shape.setOutlineColor(new Color(200, 100, 100, 100));
        }else{
            m_shape.setFillColor(new Color(200, 200, 200, 255));
            m_shape.setOutlineThickness(-5);
            m_shape.setOutlineColor(new Color(200, 100, 100, 150));
        }        
    }
    
    private Text m_buttonText;
    private Font m_buttonFont;
    private RectangleShape m_shape;
    private boolean m_isPushed;
    private boolean m_onlyPush;
}
