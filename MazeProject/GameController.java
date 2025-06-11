import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
//abi bu filewriter dosyayı yazmak için kullanılıyomuş
public class GameController {
    public final MazeManager maze;
    public final TurnManager turnManager;
    public final int maxTurns;
    private int currentTurn;
    public final List<Agent> agents;
    //burda gamein başlaması için gereken parametreleri verdik
    public GameController(int width, int height, int agentCount) {
        this.maze = new MazeManager(width, height);
        this.agents = new ArrayList<>();
        this.maxTurns = 100;
        initializeAgents(agentCount);
        this.turnManager = new TurnManager(agents, maze);
    }
    //ajanları rastgele bi yere yerleştirdik yerleştirdiğimiz yerin uygunluğunu kontrol ettik
    private void initializeAgents(int agentCount) {
        Random rand = new Random();
        for (int i = 0; i < agentCount; i++) {
            int x, y;
            do {
                x = rand.nextInt(maze.width);
                y = rand.nextInt(maze.height);
            } while (!maze.getTile(x, y).isTraversable());
            
            Agent agent = new Agent(i, x, y);
            agents.add(agent);
            maze.getTile(x, y).setHasAgent(true);
        }
    }

        //son kısımı yazdırma fonksiyonu
        private void logFinalStatistics(FileWriter logFile) throws IOException {
            logFile.write("\n=== FINAL STATISTICS ===\n");
            //labirentin son hali 
            logFile.write("FINAL MAZE:\n");
            maze.printMazeSnapshot(logFile);
    
            // son ajan zımbırtıları
            logFile.write("\nAGENT RESULTS:\n");
            agents.sort(Comparator.comparingInt(a -> a.getId()));
            for (Agent agent : agents) {
                logFile.write(String.format(
                    "Agent %d: %s | Total Moves: %d | Backtracks: %d | Traps: %d\n",
                    agent.getId(),
                    agent.hasReachedGoal() ? "GOAL" : "FAILED",
                    agent.getTotalMoves(),
                    agent.getBacktracks(),
                    agent.getTrapsTriggered()
                ));
            }
    
            //kazanan kaçıncı tur falan
            logFile.write("\nGLOBAL STATS:\n");
            logFile.write("Total Turns: " + currentTurn + "\n");
            logFile.write("First Winner: Agent " + 
                agents.stream().filter(Agent::hasReachedGoal).findFirst().map(Agent::getId).orElse(-1) + "\n");
        }

    private void logTurnDetails(FileWriter logFile) throws IOException {
        //labirent durumunu yazdırdık ilk önce
        logFile.write("MAZE STATE:\n");
        for (int i = 0; i < maze.height; i++) {
            for (int j = 0; j < maze.width; j++) {
                MazeTile tile = maze.getTile(j, i);
                char c = tile.hasAgent() ? 'A' : tile.getType();
                logFile.write(c + " ");
            }
            logFile.write("\n");
        }

        //sonra direkt ajan hareketleri yazdırdık
        for (Agent agent : agents) {
            logFile.write(String.format(
                "Agent %d: (%d,%d) | Moves: %d | Backtracks: %d | PowerUp: %s\n",
                agent.getId(), agent.getCurrentX(), agent.getCurrentY(),
                agent.getTotalMoves(), agent.getBacktracks(),
                agent.hasPowerUp() ? "YES" : "NO"
            ));

            // son 5 hareketi göstersek yeterlidir bence yoksa çok uzayıp gider gibi
            String[] lastMoves = agent.getLastNMoves(5);
            logFile.write("Last moves: ");
            for (String move : lastMoves) {
                if (move != null) logFile.write(move + " ");
            }
            logFile.write("\n");
        }
    }
        //abi simulation_log.txt adını değiştirebiliriz gerekirse
        public void runSimulation() {
            try (FileWriter logFile = new FileWriter("simulation_log.txt")) {
                while (currentTurn < maxTurns && !turnManager.allAgentsFinished()) {
                    logFile.write("\n=== TURN " + (currentTurn + 1) + " ===\n");
                    logTurnDetails(logFile); // labirent ve ajan durumlarını dosyaya kaydettik
                    turnManager.advanceTurn();
                    currentTurn++;
                }
                logFinalStatistics(logFile);
            } catch (IOException e) {
                e.printStackTrace(System.err);//herhangi bi hata için try except bloğu kullandık
            }
        }

}