/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petridishsimulator;

import org.jsfml.graphics.*;
import org.jsfml.window.*;
import org.jsfml.window.event.Event; 
import org.jsfml.system.*;

import java.io.IOException;
/**
 *
 * @author regen
 */
public class PetriDishSimulator {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args )  throws IOException {
        //Create the window
        
        RenderWindow window = new RenderWindow();
        window.create(new VideoMode(800, 600), "Petri Dish Simulator");

        Gui gui = new Gui(window);

        //Limit the framerate
        window.setFramerateLimit(30);

        //Main loop
        while(window.isOpen()) {
            // Update things
            gui.update();
            
            //Fill the window with WHITE
            window.clear(Color.WHITE);

            // Draw things
            gui.draw();
            
            //Display what was drawn (... the red color!)
            window.display();

        }
    }
}
