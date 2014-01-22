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
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.MouseEvent;
/**
 *
 * @author regen
 */
public class PlayPanel extends Panel{
    public PlayPanel(Vector2f size, Vector2f position, RenderWindow window){
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
      
        
        m_defaultView = window.getDefaultView();
        m_currentView = new View(new Vector2f(400, 300), new Vector2f(800, 600));
        
        m_zoom = 0;
        m_leftMouseHold = false;
        m_currentPan = m_holdMousePos = m_pan = Vector2i.ZERO;
    }
    
    public void draw(RenderWindow window)
    {
        super.draw(window);
        window.setView(m_currentView);
       
        m_petriDish.draw(window);
        
        window.setView(m_defaultView);
        
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
        
        // View changes
        m_currentView.setSize(800 - m_zoom * 10, 600 - m_zoom * 10);
        m_currentView.setCenter(400 - m_zoom + m_pan.x + m_currentPan.x, 
                                300 - m_zoom + m_pan.y + m_currentPan.y);
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

    public void resetView(){
        m_currentView = new View(m_defaultView.getCenter(), m_defaultView.getSize());
    }
    
    public void zoom(int delta){
        m_zoom += delta;
    }
    
    @Override
    public void clickedSomewhere(MouseEvent mouseEvent){
        m_leftMouseHold = false;
        m_pan = Vector2i.add(m_pan, m_currentPan);
        m_currentPan = Vector2i.ZERO;
    }
    
    @Override
    public void holdSomewhere(MouseEvent mouseEvent){
        // Is inside 
        if(mouseEvent.position.x > m_size.x && mouseEvent.position.y > m_size.y)
            return;
        m_leftMouseHold = true;
        m_holdMousePos = mouseEvent.position;
    }
    
    @Override
    public void MouseMoved(MouseEvent mouseEvent){
        // Is inside 
        if(mouseEvent.position.x > m_size.x && mouseEvent.position.y > m_size.y)
            return;
  
        if(m_leftMouseHold)
            m_currentPan = Vector2i.sub(m_holdMousePos, mouseEvent.position);
    }
    
    private PetriDish m_petriDish;
    private Time m_gameTime;
    private Text m_timeText;
    
    private float m_fpsSecondUpdate;
    private Text m_fpsText;
    private boolean m_showGps;
        
    private ConstView m_defaultView;
    private View m_currentView;
    private int m_zoom;

    private boolean m_leftMouseHold;
    private Vector2i m_holdMousePos;
    private Vector2i m_currentPan;
    private Vector2i m_pan;
    
}
