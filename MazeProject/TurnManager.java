import java.util.List;
import java.util.Random;
//burada genel olarak her turnde yönetimi sağlayıp bastırmaya çalıştık
//finallerin ne anlama geldiğini yeni öğrendim onları elleme
public class TurnManager {
    private final Queue<Agent> agentQueue;
    private final MazeManager mazeManager;
    private int currentRound;
    private final Random random;

    public TurnManager(List<Agent> agents, MazeManager mazeManager) {
        this.agentQueue = new Queue<>();
        this.mazeManager = mazeManager;
        this.currentRound = 0;
        this.random = new Random();
        initializeQueue(agents);
    }
    //queue'yı initialize ettik
    private void initializeQueue(List<Agent> agents) {
        for (Agent agent : agents) {
            if (!agent.hasReachedGoal()) {
                agentQueue.enqueue(agent);
            }
        }
    }

    public void advanceTurn() {
        if (agentQueue.isEmpty()) return;

        Agent currentAgent = agentQueue.dequeue();
        if (!currentAgent.hasReachedGoal()) {
            processAgentTurn(currentAgent);
            rotateRandomCorridor();
            if (!currentAgent.hasReachedGoal()) {
                agentQueue.enqueue(currentAgent);
            }
        }
        currentRound++;
        logTurnDetails(currentAgent);
        mazeManager.printMaze(); // her adımda maze'i bastırmak için kullanıyoz
    }

    private void processAgentTurn(Agent agent) {
        String[] directions = {"UP", "DOWN", "LEFT", "RIGHT"};
        boolean moved = false;

        for (String dir : directions) {
            if (mazeManager.isValidMove(agent,agent.getCurrentX(), agent.getCurrentY(), dir)) {
                executeAgentMove(agent, dir);
                moved = true;
                break;
            }
        }

        if (!moved) {
            System.out.println("Agent " + agent.getId() + " is stuck!");
        }
    }

    private void executeAgentMove(Agent agent, String direction) {
        int oldX = agent.getCurrentX();
        int oldY = agent.getCurrentY();
        
        agent.move(direction);
        mazeManager.updateAgentPosition(agent, oldX, oldY);
        
        MazeTile currentTile = mazeManager.getTile(agent.getCurrentX(), agent.getCurrentY());
        handleTileEffects(agent, currentTile);
    }
    //burda pozisyon durumlarını update etmeye çalışıyoz
    private void handleTileEffects(Agent agent, MazeTile tile) {
        switch (tile.getType()) {
            case 'T' -> {
                System.out.println("Agent " + agent.getId() + " triggered a trap!");
                agent.triggerTrap();
                // Update position after backtrack
                mazeManager.updateAgentPosition(agent, agent.getCurrentX(), agent.getCurrentY());
            }
            case 'P' -> {
                System.out.println("Agent " + agent.getId() + " collected a power-up!");
                agent.setPowerUp(true);
            }
            case 'G' -> {
                System.out.println("Agent " + agent.getId() + " reached the goal!");
                agent.setReachedGoal(true);
            }
        }
    }

    private void rotateRandomCorridor() {
        int[] rotatingRows = mazeManager.getRotatingRowIndexes();
        if (rotatingRows.length > 0) {
            int rowToRotate = rotatingRows[random.nextInt(rotatingRows.length)];
            System.out.println("Rotating row: " + rowToRotate);
            mazeManager.rotateCorridor(rowToRotate);
        }
    }

    public boolean allAgentsFinished() {
        for (Agent agent : agentQueue.toList()) {
            if (!agent.hasReachedGoal()) {
                return false;
            }
        }
        return true;
    }

    private void logTurnDetails(Agent agent) {
        System.out.println("\n=== Turn " + currentRound + " ===");
        System.out.println("Current Agent: " + agent.getId());
        System.out.printf("Position: (%d, %d)%n", agent.getCurrentX(), agent.getCurrentY());
        System.out.println("Total Moves: " + agent.getTotalMoves());
        System.out.println("Backtracks: " + agent.getBacktracks());
        System.out.println("Traps Triggered: " + agent.getTrapsTriggered());
        System.out.println("Power-Up: " + (agent.hasPowerUp() ? "Active" : "None"));
        System.out.println("Goal Status: " + (agent.hasReachedGoal() ? "REACHED" : "Not reached"));
        
        System.out.print("Recent Path: ");
        String[] lastMoves = agent.getLastNMoves(5);
        for (String move : lastMoves) {
            if (move != null) System.out.print(move + " → ");
        }
        System.out.println("Current");
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public Agent getCurrentAgent() {
        return agentQueue.peek();
    }
}