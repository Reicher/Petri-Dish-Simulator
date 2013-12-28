/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import org.jsfml.graphics.*;
import org.jsfml.window.*;
import org.jsfml.system.*;
import org.jsfml.window.event.Event; 

import java.nio.file.Paths;
import java.io.IOException;
/**
 *
 * @author regen
 */
public class Gui {
    
    Gui(RenderWindow window) throws IOException {
        m_window = window;
        
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
    
    public void draw(){
        m_playPanel.draw(m_window);
        m_timePanel.draw(m_window);
        m_infoPanel.draw(m_window);
    }
    
    public void update(){

        //Handle events
        for(Event event : m_window.pollEvents()) {
            switch(event.type)
            {
                case CLOSED:
                    m_window.close();
                    break;
                case KEY_PRESSED:
                    if(event.asKeyEvent().key.equals(Keyboard.Key.ESCAPE))
                        m_window.close();
                    else if(  event.asKeyEvent().key.equals(Keyboard.Key.P))
                        m_timePanel.togglePaus();
                    else if(  event.asKeyEvent().key.equals(Keyboard.Key.NUM1))
                        m_timePanel.setSpeed(1.0f);
                    else if(  event.asKeyEvent().key.equals(Keyboard.Key.NUM2))
                        m_timePanel.setSpeed(2.0f);
                    else if(  event.asKeyEvent().key.equals(Keyboard.Key.NUM3))
                        m_timePanel.setSpeed(5.0f);
                    break;                   
            }
        }
            
        m_timePanel.update(); // update time
        
        m_playPanel.update(m_timePanel.getDt());
        m_infoPanel.update(m_timePanel.getDt());
        
        
    }
    
    RenderWindow m_window; 
    
    PlayPanel m_playPanel;
    TimePanel m_timePanel;
    InfoPanel m_infoPanel;
}
