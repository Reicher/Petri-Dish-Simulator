/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import org.jsfml.graphics.*;
import org.jsfml.system.*;

import org.jsfml.system.Vector2f;
/**
 *
 * @author regen
 */
public class TimePanel extends Panel{
        public TimePanel(Vector2f size, Vector2f position, Texture texture){
            super(size, position, texture);
            
            m_gameClock = new Clock();
            m_paus = false;
            m_gameSpeed = 1.0f;
            m_dt = 0;
        }
        
        public void update()
        {
            m_dt = m_gameClock.restart().asSeconds() * m_gameSpeed;

            if(m_paus)
                m_dt = 0.0f;
            
        }
        
        public void togglePaus(){
            m_paus = !m_paus;
        }
        
        public void setSpeed(float multi){
            m_gameSpeed = multi;
        }
        
        public float getDt(){
            return m_dt;
        }

        private Clock m_gameClock;     
        private float m_dt;
        private boolean m_paus;
        private float m_gameSpeed;
        
}
