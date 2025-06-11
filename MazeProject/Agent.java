import java.util.Arrays;

public class Agent {
    private final int id;
    public int currentX;
    public int currentY;
    public boolean hasReachedGoal;
    public int totalMoves;
    public static int backtracks;
    public static int trapsTriggered; 
    public boolean hasPowerUp;
    public final Stack moveHistory;

    public Agent(int id, int startX, int startY) {
        this.id = id;
        this.currentX = startX;
        this.currentY = startY;
        this.moveHistory = new Stack();
        this.moveHistory.push(startX + "," + startY); // başlangıçta buraya koyduk 
    }
    public String[] convertToStringArray(Object[] array) {
        String[] stringArray = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            stringArray[i] = (String) array[i];
        }
        return stringArray;
    }

    // oto oynatmaya göre ajan yukarı aşağı sağ veya sola doğru hareket ederse hareketi dğeiştir
    public void move(String direction) {
        direction = direction.toUpperCase();
        if (!Arrays.asList("UP", "DOWN", "LEFT", "RIGHT").contains(direction)) return;

        int newX = currentX;
        int newY = currentY;

        switch (direction) {
            case "UP" -> newY--;
            case "DOWN" -> newY++;
            case "LEFT" -> newX--;
            case "RIGHT" -> newX++;
        }

        currentX = newX;
        currentY = newY;
        recordMove(newX, newY);
        totalMoves++;
    }

    // tuzağa yakalanırsa 2 adım geri gitmeli kodu
    public void backtrack() {
        if (moveHistory.getSize() < 2) return;

        // stackten 2 pop et
        moveHistory.pop();
        moveHistory.pop();

        if (moveHistory.isEmpty()) return;
        
        String lastValidMove = moveHistory.peek();
        if (lastValidMove == null || !lastValidMove.contains(",")) return;

        try {
            String[] coords = lastValidMove.split(",");
            currentX = Integer.parseInt(coords[0]);
            currentY = Integer.parseInt(coords[1]);
            backtracks++;
        } catch (NumberFormatException e) {
            System.err.println("Invalid move format in history!");
        }
    }

    // tuzağı triggerleyıp çalışmasını sağla
    public void triggerTrap() {
        trapsTriggered++;
        backtrack(); //bu fonksiyonla da oto 2 adım geri at
    }

    private void recordMove(int x, int y) {
        moveHistory.push(x + "," + y);
    }

    // gücü varsa hamle sayısını arttırmadan hareket etmesini sağla
    public void usePowerUp() {
        if (hasPowerUp) {
            
            totalMoves--; // Hamle sayısını artırmadan hareket ettirir
            hasPowerUp = false;
        }
    }

    // son hareketleri alır gamecontrollerda yazdırmak için gerekli
    public String[] getLastNMoves(int n) {
        return moveHistory.getLastNMoves(n);
    }

    public String getMoveHistoryAsString() {
        StringBuilder sb = new StringBuilder();
        String[] moves = getLastNMoves(moveHistory.getSize());
        for (String move : moves) {
            if (move != null) sb.append(move).append(" -> ");
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 4) : "No moves";
    }
    public void removeAgent(MazeManager mazeManager, int x, int y) {
        mazeManager.updateAgentPosition(this, x, y);
    }

    public void placeAgent(MazeManager mazeManager, int x, int y) {
        mazeManager.updateAgentPosition(this, x, y);
    }

    
    public int getId() { return id; }
    public int getCurrentX() { return currentX; }
    public int getCurrentY() { return currentY; }
    public boolean hasReachedGoal() { return hasReachedGoal; }
    public void setReachedGoal(boolean reached) { this.hasReachedGoal = reached; }
    public int getTotalMoves() { return totalMoves; }
    public int getBacktracks() { return backtracks; }
    public int getTrapsTriggered() { return trapsTriggered; }
    public boolean hasPowerUp() { return hasPowerUp; }
    public void setPowerUp(boolean powerUp) { this.hasPowerUp = powerUp; }
    //override yazmayınca sarı uyarı verdi
    @Override
    public String toString() {
        return String.format(
            "Agent %d: (%d,%d) | Moves: %d | Backtracks: %d | Traps: %d | PowerUp: %s | Goal: %s",
            id, currentX, currentY, totalMoves, backtracks, trapsTriggered,
            hasPowerUp ? "YES" : "NO", hasReachedGoal ? "REACHED" : "PENDING"
        );
    }
}