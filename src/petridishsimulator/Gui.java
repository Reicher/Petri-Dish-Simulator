/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import org.jsfml.graphics.*;
import org.jsfml.window.*;
import org.jsfml.system.*;

import java.nio.file.Paths;
import java.io.IOException;
/**
 *
 * @author regen
 */
public class Gui {
    
    Gui() throws IOException {
        
        //Create Play Panel
        Texture playTex = new Texture();
        playTex.loadFromFile(Paths.get("Resources/PlayPanel.png"));
        m_playPanel = new PlayPanel(new Vector2f(500, 500), 
                                    new Vector2f(0, 0), 
                                    playTex);
        
        //Create Time Panel
        Texture timeTex = new Texture();
        timeTex.loadFromFile(Paths.get("Resources/TimePanel.png"));
        m_timePanel = new TimePanel(new Vector2f(500, 100), 
                                    new Vector2f(0, 500), 
                                    timeTex);
        
        //Create Time Panel
        Texture infoTex = new Texture();
        infoTex.loadFromFile(Paths.get("Resources/InfoPanel.png"));
        m_infoPanel = new InfoPanel(new Vector2f(300, 600), 
                                    new Vector2f(500, 0), 
                                    infoTex);
        
        
    }
    
    public void draw(RenderWindow window){
        m_playPanel.draw(window);
        m_timePanel.draw(window);
        m_infoPanel.draw(window);
    }
    
    public void update(float dt){
        m_playPanel.update(dt);
        m_timePanel.update(dt);
        m_infoPanel.update(dt);
    }
    
    PlayPanel m_playPanel;
    TimePanel m_timePanel;
    InfoPanel m_infoPanel;
}
