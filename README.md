
🧭 MazeProject - Autonomous Agents in a Dynamic Maze

🧠 Overview
MazeProject simulates autonomous agents navigating through a randomly generated maze using basic Java programming constructs — no AI libraries, no external pathfinding tools.

The agents backtrack from traps, collect power-ups, and aim to reach a goal tile, all while moving randomly and logging their paths. The project showcases the power of Stacks, Queues, Linked Lists, and object-oriented design.

🎯 Project Goals
- Implement simple autonomous behavior without AI.
- Reinforce data structures: Stack, Queue, Circular Linked List.
- Model a dynamic environment (e.g. rotating corridors).
- Demonstrate encapsulation and interaction of Java classes.

🕹️ How to Run

🧱 Prerequisites
- Java JDK 8 or higher
- Terminal / Command Prompt

🚀 Compilation
    javac *.java
    java Main

🎮 Controls
- No user input required
- All agent movement is autonomous and printed to console
- Maze state and agent logs are saved in simulation_log.txt

🧩 Maze Elements

Symbol | Meaning
-------|-------------------------
W      | Wall (not traversable)
E      | Empty Tile
T      | Trap (forces backtrack)
P      | Power-Up (allows free move)
G      | Goal (exit point)
A      | Agent

🧬 Core Components

🔧 GameController
- Orchestrates simulation, initializes components, runs game loop

🗺️ MazeManager
- Builds and manages the maze
- Handles tile validation and rotating rows via RotatingRowManager

🔁 TurnManager
- Manages turn-based logic using a custom Queue
- Assigns agents their moves in a round-robin fashion

🕺 Agent
- Moves autonomously
- Stores move history using a custom Stack
- Handles power-ups, traps, and backtracking

🔄 RotatingRowManager
- Uses a CircularLinkedList to rotate rows dynamically during the simulation

🔢 Supporting Structures
- Stack: Tracks agent movement path
- Queue: Manages agent turn order
- CircularLinkedList: Used for row rotation in the maze

📂 Output
- Agent positions, moves, power-up status, and traps are logged to the terminal
- All movements are written to simulation_log.txt
- Turn-based maze snapshots provide debugging insight

🚧 Known Limitations
- No real AI; movement is purely random
- No GUI, only console-based output

🔮 Future Work
- Add BFS/A* pathfinding for intelligent navigation
- Implement a graphical interface (Swing, JavaFX, or Web-based)
- Allow user-defined mazes and agent parameters

📚 References
- Data Structures and Algorithms in Java - M. Goodrich
- https://www.geeksforgeeks.org/
- https://visualgo.net/en
- Java API Docs
- Lecture Notes & Lab Materials

👥 Authors
- Halil Abacı & Samet Ünsal  

“Simple rules. Complex behavior. Infinite possibilities.”  
— MazeProject Philosophy
