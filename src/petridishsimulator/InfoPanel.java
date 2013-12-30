/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import java.io.IOException;
import java.io.InputStream;
import org.jsfml.graphics.*;

import org.jsfml.system.Vector2f;
/**
 *
 * @author regen
 */
public class InfoPanel extends Panel {
        public InfoPanel(Vector2f size, Vector2f position){
            super(size, position);
            
            // Load font
            m_headerFont = new Font();
            try {
                InputStream istream = getClass().
                        getResourceAsStream("/Resources/00TT.TTF");
                m_headerFont.loadFromStream(istream);
            } catch(IOException ex) {
                //Failed to load font
                ex.printStackTrace();
            }
            m_headerText = new Text("Petri Dish\nSimulator", m_headerFont, 45);
            m_headerText.setColor(Color.BLACK);
            m_headerText.setPosition(Vector2f.add(m_position, new Vector2f(45, 10)));
            m_headerText.setStyle(Text.UNDERLINED);

        }
        
        public void draw(RenderWindow window){
            super.draw(window);
            
            window.draw(m_headerText);
        }
        
    private Text m_headerText;
    private Font m_headerFont;
}
