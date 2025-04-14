# Bird Simulation

A simple Java Swing application that simulates flocking behavior of birds, similar to the classic "boids" algorithm.

![Bird Simulation was inspired by this video](https://www.youtube.com/shorts/X8LglXSG53A)

## Features

- **Interactive Bird Creation**: Click anywhere on the screen to add a new bird
- **Automatic Flocking**: Birds automatically move towards the center of mass
- **Collision Avoidance**: Birds try to maintain a safe distance from each other
- **Path Tracking**: Each bird leaves a trail showing its movement history
- **Bird Removal**: Click on a bird to remove it from the simulation

## How to Run

### Requirements
- Java Runtime Environment (JRE) 8 or higher

### Running the Application
1. Download the JAR file from the releases section
2. Run the application using:
   ```
   java -jar bird-simulation.jar
   ```

### Controls
- **Left-click** on the window to add a new bird at that position
- **Left-click** on a bird to remove it

## How It Works

This simulation demonstrates emergent flocking behavior based on three simple rules:
1. **Cohesion**: Birds move toward the average position of the flock
2. **Separation**: Birds avoid crowding nearby flock members
3. **Alignment**: Birds tend to move in the same direction as nearby flock members

Each bird is represented by a yellow triangle with its unique ID displayed in the center. As they move, they leave behind trails showing their flight paths.

## Demo

Try it yourself on Replit: [Demo](https://replit.com/@klhjdfgklj/Birds-Emulator)

## Code Structure

- `Main.java` - Entry point and animation controller
- `MainFrame.java` - Window management and layout
- `Bird.java` - Core bird behavior and rendering
- `TrackPanel.java` - Renders the path history of birds