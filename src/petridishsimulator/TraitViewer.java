/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import java.io.IOException;
import java.io.InputStream;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.MouseEvent;
import petridishsimulator.DNA.Trait;

/**
 *
 * @author regen
 */
public class TraitViewer {
    public TraitViewer(Vector2f pos){
            // Load font
            m_font = new Font();
            try {
                InputStream istream = getClass().
                        getResourceAsStream("/Resources/00TT.TTF");
                m_font.loadFromStream(istream);
            } catch(IOException ex) {
                //Failed to load font
                ex.printStackTrace();
            }
        m_pos = pos;
            
        m_currentTrait = DNA.Trait.SIZE;
        String trait = m_currentTrait.toString();
        trait.replace("_", " ");
        trait = trait.toLowerCase();
        trait = trait.substring(0, 1).toUpperCase() + trait.substring(1);       
            
        m_traitText = new Text(trait, m_font, 28);
        m_traitText.setColor(Color.BLACK);
        m_traitText.setOrigin(new Vector2f(m_traitText.getGlobalBounds().width/2.0f, 
                                           m_traitText.getGlobalBounds().height/2.0f));
        m_traitText.setPosition( Vector2f.add(pos, new Vector2f(130, 18)));

        m_background = new RectangleShape(new Vector2f(250.0f, 150.0f));
        m_background.setPosition(pos);
        m_background.setFillColor(new Color(255, 255, 255, 150));
        m_background.setOutlineColor(Color.BLACK);
        m_background.setOutlineThickness(2);
        
        m_headerBackground = new RectangleShape(new Vector2f(230.0f, 40.0f));
        m_headerBackground.setPosition(Vector2f.add(pos, new Vector2f(10.0f, 10.0f)));
        m_headerBackground.setFillColor(new Color(255, 255, 255, 150));
        m_headerBackground.setOutlineThickness(2);
        m_headerBackground.setOutlineColor(Color.BLACK);
        
        m_nextTriangle = new CircleShape(18, 3);
        m_nextTriangle.setPosition(Vector2f.add(pos, new Vector2f(235.0f, 12.0f)));
        m_nextTriangle.setFillColor(new Color(255, 100, 100, 200));
        m_nextTriangle.setOutlineThickness(1);
        m_nextTriangle.setOutlineColor(Color.BLACK);
        m_nextTriangle.rotate(90);
        
        m_prevTriangle = new CircleShape(18, 3);
        m_prevTriangle.setPosition(Vector2f.add(pos, new Vector2f(13.0f, 48.0f)));
        m_prevTriangle.setFillColor(new Color(255, 100, 100, 200));
        m_prevTriangle.setOutlineThickness(1);
        m_prevTriangle.setOutlineColor(Color.BLACK);
        m_prevTriangle.rotate(-90);
    }
    
    public void update(float[] values){ 
        m_values = values;
    }
    
    private void updateText(){
        String trait = m_currentTrait.toString();
        trait = trait.replace("_", " ");
        trait = trait.toLowerCase();
        trait = trait.substring(0, 1).toUpperCase() + trait.substring(1);
        m_traitText.setString(trait);
        
        if(m_traitText.getGlobalBounds().width > 150)
            m_traitText.setCharacterSize(19);
        else 
            m_traitText.setCharacterSize(28);
        
        m_traitText.setOrigin(new Vector2f(m_traitText.getGlobalBounds().width/2.0f, 
                                           m_traitText.getGlobalBounds().height/2.0f));
        m_traitText.setPosition( Vector2f.add(m_pos, new Vector2f(130, 20)));
        


    }
    
    public void draw(RenderWindow window){
        window.draw(m_background);
        window.draw(m_headerBackground);

        window.draw(m_traitText);
        window.draw(m_nextTriangle);
        window.draw(m_prevTriangle);
        
        RectangleShape bar;
        for(int i = 0; i < 10; i++){
            bar = new RectangleShape(new Vector2f(15, -90.0f * m_values[i]));
            bar.setFillColor(Color.RED);
            bar.setPosition( Vector2f.add(m_background.getPosition()
                                            , new Vector2f(5.0f + 24.0f * i, 145)));
            window.draw(bar);
        }
    }
    
    
    public void clickedSomewhere(MouseEvent mouseEvent)
    {
        Vector2f mouse = new Vector2f((float)mouseEvent.position.x, 
                (float )mouseEvent.position.y);
        if(m_prevTriangle.getGlobalBounds().contains(mouse) 
                && m_currentTrait.ordinal() != 0 ){
            m_currentTrait = m_currentTrait.getPrev();
            updateText();
        }                
        else if(m_nextTriangle.getGlobalBounds().contains(mouse) 
                && m_currentTrait.ordinal() != DNA.Trait.values().length -1){
            m_currentTrait = m_currentTrait.getNext();
            updateText();
        }                 
    }

    public Trait getTrait(){
        return m_currentTrait;
    }

    private Vector2f m_pos;
    
    private float[] m_values;
    private Text m_traitText;
    private Font m_font;
    
    private DNA.Trait m_currentTrait;
    
    private RectangleShape m_background;
    
    private RectangleShape m_headerBackground;
    private CircleShape m_nextTriangle;
    private CircleShape m_prevTriangle;
    
    
}
