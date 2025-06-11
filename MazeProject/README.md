# 📜 Maze Escape: Turn-Based Simulation Game  
`CENG 202 - Data Structures Maze Project`  

## 📌 Table of Contents  
1. [Project Description](#-project-description)  
2. [Technical Specifications](#-technical-specifications)  
3. [Installation Guide](#-installation-guide)  
4. [Game Documentation](#-game-documentation)  
5. [Class Structure](#-class-structure)  
6. [Sample Output](#-sample-output)  
7. [License](#-license)  

---

## 🎮 Project Description  
A Java implementation of a turn-based maze simulation featuring:  
- 🔄 **Dynamic corridors** that rotate periodically  
- 🤖 **Autonomous agents** with pathfinding  
- ⚠️ **Interactive tiles** (Traps, Power-ups, Goals)  
- 📈 **Real-time statistics** tracking  

---

## 💻 Technical Specifications  
### Data Structures Used  
| Structure          | Implementation Location       | Purpose                          |  
|--------------------|-------------------------------|----------------------------------|  
| Circular Linked List | `donsatlist.java`           | Corridor rotation logic          |  
| Stack              | `Agent.java`                  | Move history for backtracking    |  
| Queue              | `TurnManager.java`            | Turn order management            |  
| 2D Array           | `MazeManager.java`            | Maze grid storage                |  

### Algorithms  
- **BFS** (For future pathfinding extension)  
- **Fisher-Yates Shuffle** (Maze generation)  

---

## 🔧 Installation Guide  
### Requirements  
- Java JDK 17+  
- (Optional) Maven  

### Steps  
```bash  
# Clone repository  
git clone https://github.com/yourusername/maze-escape.git  

# Compile  
javac src/*.java -d bin/  

# Run (default 8x8 maze with 3 agents)  
java -cp bin/ Main  

# Custom run  
java -cp bin/ Main <width> <height> <agentCount>  

🕹️ Game Documentation
Tile Legend
Symbol	Type	Effect
A	Agent	Player character
W	Wall	Blocks movement
G	Goal	Winning condition
Key Commands
The simulation runs automatically

Press Ctrl+C to terminate early

🏗️ Class Structure
Core Classes
GameController

Orchestrates simulation flow

Manages game lifecycle

MazeManager

Handles maze generation

Validates movements

Agent

Contains movement logic

Tracks statistics