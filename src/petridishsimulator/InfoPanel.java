/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import org.jsfml.graphics.*;

import org.jsfml.system.Vector2f;
import org.jsfml.window.event.MouseEvent;
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
            
            m_versionNrText = new Text("v 0.3", m_headerFont, 30);
            m_versionNrText.setColor(Color.BLACK);
            m_versionNrText.setPosition(Vector2f.add(m_position, new Vector2f(110, 100)));
            
            m_populationSizeText= new Text("Population: ", m_headerFont, 27);
            m_populationSizeText.setColor(Color.BLACK);
            m_populationSizeText.setPosition(Vector2f.add(m_position, new Vector2f(20, 150)));
            
            m_nutrientSizeText = new Text("Nutrients: ", m_headerFont, 27);
            m_nutrientSizeText.setColor(Color.BLACK);
            m_nutrientSizeText.setPosition(Vector2f.add(m_position, new Vector2f(20, 190)));
            
            m_generations = new Text("Generations: ", m_headerFont, 27);
            m_generations.setColor(Color.BLACK);
            m_generations.setPosition(Vector2f.add(m_position, new Vector2f(20, 230)));
            
            m_oldestGenerationAlive = new Text("Oldest alive: ", m_headerFont, 27);
            m_oldestGenerationAlive.setColor(Color.BLACK);
            m_oldestGenerationAlive.setPosition(Vector2f.add(m_position, new Vector2f(20, 270)));
            
            m_traitViewer = new TraitViewer(Vector2f.add(m_position, new Vector2f(25, 330)));
            
            m_byMe = new Text("By Robin Reicher", m_headerFont, 25);
            m_byMe.setColor(Color.BLACK);
            m_byMe.setPosition(Vector2f.add(m_position, new Vector2f(20, 560)));
            
        }
        
        public void draw(RenderWindow window){
            super.draw(window);
            
            window.draw(m_headerText);
            window.draw(m_versionNrText);
            window.draw(m_populationSizeText);
            window.draw(m_nutrientSizeText);
            window.draw(m_generations);
            window.draw(m_oldestGenerationAlive);
            window.draw(m_byMe);
            m_traitViewer.draw(window);
        }

        public void setPopulationSize(int size){
            m_population = size;
            m_populationSizeText.setString("Population: " + m_population);
        }
        
        public void setNutrientSize(float size){
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(1);
            m_nutrientSizeText.setString("Nutrients: " + df.format(size));
        }
        
        public void setTraitSpread(int[] spread){
            float[] values = new float[10];
            for(int i = 0; i < 10; i++)
                values[i] += (float)spread[i]/(float)m_population;
            
            m_traitViewer.update(values);
        }
        
        public void setGenerations(int generations){
            m_generations.setString("Generations: " + generations);
        }
        
        public void setOldestGeneration(int generation){
            m_oldestGenerationAlive.setString("Oldest alive: Gen " + generation);
        }
        
        public DNA.Trait getCurrentTrait(){
            return m_traitViewer.getTrait();
        }
        
        public void clickedSomewhere(MouseEvent mouseEvent){
            m_traitViewer.clickedSomewhere(mouseEvent);
        }
        
    private Text m_headerText;
    private Text m_versionNrText;
    
    private Text m_populationSizeText;
    private Text m_nutrientSizeText;
    private Font m_headerFont;
    private int m_population;
    
    private Text m_generations;
    private Text m_oldestGenerationAlive;
    
    private TraitViewer m_traitViewer; 
    
    private Text m_byMe;
    
}
