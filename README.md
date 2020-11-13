# Deep Dungeon Map Solver

This program can accept a graphical text-based map from the Unofficial Squaresoft MUD,
and solve it by finding a route through the map. This is only compatible with maps 
from the Deep Dungeon area of the game.

For more details, see the Docs folder, where there is a spec of this map, samples,
 and a generalized algorithm for solving them.

# Build

- Clone repository
- Run maven package

# CLI Capability

In order to run the CLI, launch the application with no arguments. This will prompt the
user to enter all lines of the deep dungeon map. After all lines are entered, it will
automatically solve the map and output the speedwalk. The user will then be allowed to either
continue solving maps (entering another) or exit.

# GUI Capability

To access the GUI, run the application with the "gui" argument. This
will open up a JavaFX application which allows you to enter a map,
press generate, and get a speedwalk. There is also an example map.

# Maven Cooridnates

Not yet available.

# Download

Download here: https://github.com/zach-cloud/deep-dungeon-map-solver/blob/master/artifacts/deep-dungeon-solver-1.0.zip

- Go to link
- Press the download button on the right-hand side of the screen
- Unzip the file with 7Zip or some equivalent
- Double click the "run" file to automatically run the GUI
    - You can also run it as a JAR file from the command line