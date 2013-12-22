/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event; 
import org.jsfml.system.*;

/**
 *
 * @author regen
 */
public class PetriDishSimulator {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Create the window
        
        RenderWindow window = new RenderWindow();
        window.create(new VideoMode(800, 600), "Petri Dish Simulator");
        
        Clock clock = new Clock();
        float dt = 0.0f;
        
        PetriDish petriDish = new PetriDish(window);
        
        //Limit the framerate
        window.setFramerateLimit(30);

        //Main loop
        while(window.isOpen()) {

            //Handle events
            for(Event event : window.pollEvents()) {
                switch(event.type)
                {
                    case CLOSED:
                        window.close();
                        break;
                    case KEY_PRESSED:
                        if(event.asKeyEvent().key.equals(Keyboard.Key.ESCAPE))
                            window.close();
                        break;
                }
            }
            
            // Update things
            dt = clock.restart().asSeconds();
            petriDish.update(dt);
            
            //Fill the window with WHITE
            window.clear(Color.WHITE);

            // Draw things
            petriDish.draw();
            
            //Display what was drawn (... the red color!)
            window.display();

        }
    }
}
