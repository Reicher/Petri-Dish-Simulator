/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import org.jsfml.graphics.*;
import org.jsfml.system.*;

import org.jsfml.system.Vector2f;
import org.jsfml.window.event.MouseEvent;
/**
 *
 * @author regen
 */
public class TimePanel extends Panel{
        public TimePanel(Vector2f size, Vector2f position){
            super(size, position);
            
            m_gameClock = new Clock();
            m_gameSpeed = 1.0f;
            m_dt = 0;
            
            m_pauseButton = new SwitchButton( new Vector2f(25, 525), 
                      "Pause", false, false);
            
            m_timesOneButton = new SwitchButton( new Vector2f(180, 525), 
                      "x1", true, true);
            
            m_timesTwoButton = new SwitchButton( new Vector2f(240, 525), 
                      "x2", false, true);

            m_timesFiveButton = new SwitchButton( new Vector2f(320, 525), 
                      "x5", false, true);
            m_timesTenButton = new SwitchButton( new Vector2f(400, 525), 
                      "x10", false, true);
        }
        
        public void update()
        {
            if(m_pauseButton.isPushed())
                m_gameSpeed = 0.0f;
            else if(m_timesOneButton.isPushed())
                m_gameSpeed = 1.0f;
            else if(m_timesTwoButton.isPushed())
                m_gameSpeed = 2.0f;
            else if(m_timesFiveButton.isPushed())
                m_gameSpeed = 5.0f;  
            else if (m_timesTenButton.isPushed())
                m_gameSpeed = 10.0f;
            
            m_dt = m_gameClock.restart().asSeconds() * m_gameSpeed;

        }
        
        public void clickedSomewhere(MouseEvent mouseEvent)
        {
            boolean pause, x1, x2, x5, x10;
            
            pause = m_pauseButton.update(mouseEvent);
            
            x1 = m_timesOneButton.update(mouseEvent);
            x2 = m_timesTwoButton.update(mouseEvent);
            x5 = m_timesFiveButton.update(mouseEvent);
            x10 = m_timesTenButton.update(mouseEvent);
            
            // if any speed is pushed, unpush pause
            if(x1 || x2 || x5 || x10)
                m_pauseButton.setUnPushed();
            
            if( x2 || x5 || x10)
                m_timesOneButton.setUnPushed();
            if( x1 || x5|| x10)
                m_timesTwoButton.setUnPushed();
            if( x1 || x2|| x10)
                m_timesFiveButton.setUnPushed();
            if( x1 || x2|| x5)
                m_timesTenButton.setUnPushed();
        }
        
        public void draw(RenderWindow window){
            super.draw(window);
            
            m_pauseButton.draw(window);
            m_timesOneButton.draw(window);
            m_timesTwoButton.draw(window);
            m_timesFiveButton.draw(window);
            m_timesTenButton.draw(window);
        }
        
        public void togglePause(){
            m_pauseButton.setPushed();
        }
        
        private void setSpeed(float multi){
            m_gameSpeed = multi;
        }
        
        public float getDt(){
            return m_dt;
        }

        private Clock m_gameClock;     
        private float m_dt;
        private float m_gameSpeed;
        
        private SwitchButton m_pauseButton;
        
        private SwitchButton m_timesOneButton;
        private SwitchButton m_timesTwoButton;
        private SwitchButton m_timesFiveButton;
        private SwitchButton m_timesTenButton;
        
}
