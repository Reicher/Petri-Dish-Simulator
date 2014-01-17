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
            float spaceBetween = 10.0f;
            
            m_pauseButton = new SwitchButton( new Vector2f(25, 525), 
                      "Pause", false, false);
            
            m_timesOneButton = new SwitchButton( 
                    Vector2f.add(m_pauseButton.getUpperRightCorner(),
                    new Vector2f(spaceBetween, 0)), 
                      "x1", true, true);
            
            m_timesTwoButton = new SwitchButton( 
                    Vector2f.add(m_timesOneButton.getUpperRightCorner(),
                    new Vector2f(spaceBetween, 0)), 
                      "x2", false, true);

            m_timesTenButton = new SwitchButton( 
                    Vector2f.add(m_timesTwoButton.getUpperRightCorner(),
                    new Vector2f(spaceBetween, 0)), 
                      "x10", false, true);
            m_times20Button = new SwitchButton( 
                    Vector2f.add(m_timesTenButton.getUpperRightCorner(),
                    new Vector2f(spaceBetween, 0)), 
                      "x20", false, true);
        }
        
        public void update()
        {
            if(m_pauseButton.isPushed())
                m_gameSpeed = 0.0f;
            else if(m_timesOneButton.isPushed())
                m_gameSpeed = 1.0f;
            else if(m_timesTwoButton.isPushed())
                m_gameSpeed = 2.0f;
            else if(m_timesTenButton.isPushed())
                m_gameSpeed = 10.0f;  
            else if (m_times20Button.isPushed())
                m_gameSpeed = 20.0f;
            
            m_dt = m_gameClock.restart().asSeconds() * m_gameSpeed;

        }
        
        public void clickedSomewhere(MouseEvent mouseEvent)
        {
            boolean pause, x1, x2, x10, x20;
            
            pause = m_pauseButton.update(mouseEvent);
            
            x1 = m_timesOneButton.update(mouseEvent);
            x2 = m_timesTwoButton.update(mouseEvent);
            x10 = m_timesTenButton.update(mouseEvent);
            x20 = m_times20Button.update(mouseEvent);
            
            // if any speed is pushed, unpush pause
            if(x1 || x2 || x10 || x20)
                m_pauseButton.setUnPushed();
            
            if( x2 || x10 || x20)
                m_timesOneButton.setUnPushed();
            if( x1 || x10|| x20)
                m_timesTwoButton.setUnPushed();
            if( x1 || x2|| x20)
                m_timesTenButton.setUnPushed();
            if( x1 || x2|| x10)
                m_times20Button.setUnPushed();
        }
        
        public void draw(RenderWindow window){
            super.draw(window);
            
            m_pauseButton.draw(window);
            m_timesOneButton.draw(window);
            m_timesTwoButton.draw(window);
            m_timesTenButton.draw(window);
            m_times20Button.draw(window);
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
        private SwitchButton m_timesTenButton;
        private SwitchButton m_times20Button;
        
}
