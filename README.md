Petri-Dish-Simulator
====================

The life on a petri dish up close! The idea is to show bacteria live, die and evolve on a Petri dish!
This is my first take on java and the JFSML library (http://jsfml.org/).

Controls:
'P' = Pause

'1' - '3' = Speed x1, x2, x5

Ideas:
* Bacteria multiply by splitting, creating a clone (sometimes it's a bit mutated)
* Bacteria have different features like size, color, outer-shell thickness etc. 
* Bacteria need to eat nutrients found laying around on the dish, this grows back slowly.
* Bacteria have energy that can be used to live, move, eat, split.
* Time is a variable that can be speeded up. 
* GRAPHS loads of graphs. Graphs are awesome.

Right now:
* Crude GUI base
* Bacteria with energy, health and 5 types of genes (SIZE, ENERGY_STORAGE, MEMBRANE, RED, GREEN, BLUE)
* Confusing and crude AI, bacteria seek up food, eat and split when they feeling good enough.
* Nutrients growing on the dish

Next ToDo's:
* Fine tune how the genes should interact with speed, eating pace etc.
* Add time panel for speeding up simulation
* Add first infopanel elements like Population size etc.
* Refractor code...
