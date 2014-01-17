/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import java.io.IOException;
import java.io.InputStream;
import org.jsfml.graphics.*;
import org.jsfml.system.Time;

import org.jsfml.system.Vector2f;
/**
 *
 * @author regen
 */
public class PlayPanel extends Panel{
    public PlayPanel(Vector2f size, Vector2f position){
        super(size, position);
        
        m_petriDish= new PetriDish(400.0f, new Vector2f(50.0f, 50.0f));
        
        m_gameTime = Time.ZERO;
        
        m_showGps = false;
        
        // Load font
        Font m_timeFont = new Font();
        try {
            InputStream istream = getClass().
                    getResourceAsStream("/Resources/00TT.TTF");
            m_timeFont.loadFromStream(istream);
        } catch(IOException ex) {
            //Failed to load font
            ex.printStackTrace();
        }
        m_timeText = new Text("", m_timeFont, 30);
        m_timeText.setColor(Color.BLACK);
        m_timeText.setPosition( new Vector2f(10.0f, 5.0f));
        
        m_fpsText= new Text("FPS: ", m_timeFont, 30);
        m_fpsText.setColor(Color.BLACK);
        m_fpsText.setPosition( new Vector2f(350.0f, 5.0f));
    }
    
    public void draw(RenderWindow window)
    {
        super.draw(window);
        m_petriDish.draw(window);
        
        window.draw(m_timeText);
        
        if(m_showGps)
            window.draw(m_fpsText);
    }
    
    public void update(float dt){
        m_petriDish.update(dt);
        
        m_gameTime = m_gameTime.add(m_gameTime, Time.getSeconds(dt));
        
        String timeString = (int)m_gameTime.asSeconds()/3600 + ":" 
                + (int)(m_gameTime.asSeconds()/60)%60 + ":" 
                + (int)m_gameTime.asSeconds()%60 + ":"
                + (int)(m_gameTime.asSeconds()%1 * 10.0f);
        m_timeText.setString(timeString);
        
        if(  m_fpsSecondUpdate >= 1.0f ){
            m_fpsText.setString("FPS: " + (int)(1.0f/dt));
            m_fpsSecondUpdate = 0;
        }
        
        m_fpsSecondUpdate += dt;
    }
    
    public void toggleFps()
    {
        m_showGps = !m_showGps;
    }
    
    public int getBacteriaPopulation(){
        return m_petriDish.m_population.getPopulationSize();
    }
    
    public float getNutrientSize(){
        return m_petriDish.m_nutrientHolder.getNutrientSize();
    }
    
    public int[] getTraitSpread(DNA.Trait feno){
        return m_petriDish.m_population.getFenotypeSpreed(feno);
    }
    
    public int getGenerations(){
        
        return m_petriDish.m_population.getMostGenerations();
    }
            
    public int getOldestGeneration(){
        return m_petriDish.m_population.getOldestGeneration();
    }
            
    private PetriDish m_petriDish;
    private Time m_gameTime;
    private Text m_timeText;
    
    private float m_fpsSecondUpdate;
    private Text m_fpsText;
    private boolean m_showGps;
    
}
